package javacode.chessengine.search;

import javacode.chessengine.evaluation.Evaluator;
import javacode.chessengine.moveordering.MoveOrderer;
import javacode.chessengine.transpositiontable.TranspositionTable;
import javacode.chessengine.transpositiontable.ZobristHash;
import javacode.chessprogram.check.CheckChecker;
import javacode.chessprogram.chess.Chessboard;
import javacode.chessprogram.chess.Move;
import javacode.chessprogram.moveMaking.MoveParser;
import org.junit.Assert;

import java.util.List;

import static javacode.chessengine.evaluation.Evaluator.*;
import static javacode.chessengine.moveordering.KillerMoves.mateKiller;
import static javacode.chessengine.moveordering.KillerMoves.updateKillerMoves;
import static javacode.chessengine.search.FutilityPruning.futilityMargin;
import static javacode.chessengine.search.FutilityPruning.isFutilityPruningAllowedHere;
import static javacode.chessengine.search.InternalIterativeDeepening.iidDepthReduction;
import static javacode.chessengine.search.InternalIterativeDeepening.isIIDAllowedHere;
import static javacode.chessengine.search.LateMoveReductions.isLateMoveReductionAllowedHere;
import static javacode.chessengine.search.LateMoveReductions.lateMoveDepthReduction;
import static javacode.chessengine.search.NullMovePruning.*;
import static javacode.chessengine.search.Razoring.*;
import static javacode.chessengine.search.SEEPruning.seeScore;
import static javacode.chessengine.transpositiontable.EngineMovesAndHash.*;
import static javacode.chessengine.transpositiontable.TranspositionTable.TableObject.Flag.EXACT;
import static javacode.chessengine.transpositiontable.TranspositionTable.TableObject.Flag.LOWERBOUND;
import static javacode.chessengine.transpositiontable.TranspositionTable.TableObject.Flag.UPPERBOUND;
import static javacode.chessprogram.check.CheckChecker.*;
import static javacode.chessprogram.check.CheckChecker.boardInCheck;
import static javacode.chessprogram.chess.Copier.copyMove;
import static javacode.chessprogram.moveGeneration.MoveGeneratorMaster.generateLegalMoves;

public class PrincipleVariationSearch {

    private Engine engine;
    public TranspositionTable table;
    private MoveOrderer moveOrderer;
    private QuiescenceSearch quiescenceSearch;
    private Move aiMove;
    private Evaluator evaluator;
    private Extensions extensions;

    PrincipleVariationSearch(Engine engine, Evaluator evaluator){
        this.engine = engine;
        this.table = new TranspositionTable();
        this.moveOrderer = new MoveOrderer(this.engine);
        this.evaluator = evaluator;
        this.quiescenceSearch = new QuiescenceSearch(this.engine, this.moveOrderer, this.evaluator);
        this.extensions = new Extensions(this.engine);
    }

    int principleVariationSearch(Chessboard board, ZobristHash zobristHash,
                                 long startTime, long timeLimitMillis,
                                 int originalDepth, int depth, int ply,
                                 int alpha, int beta,
                                 int nullMoveCounter, boolean reducedSearch){

        if (this.engine.HEAVY_DEBUG){
            long testBoardHash = new ZobristHash(board).getBoardHash();
            Assert.assertEquals(testBoardHash, zobristHash.getBoardHash());
        }

        depth += this.extensions.extensions(board, ply);

        Assert.assertTrue(depth >= 0);
        
        /*
        Quiescent Search:
        if at a leaf node, perform specialised search of captures to avoid horizon effect
         */
        if (depth <= 0){
            if(this.engine.getEngineSpecifications().ALLOW_EXTENSIONS && originalDepth != 0) {
                Assert.assertTrue(!boardInCheck(board, board.isWhiteTurn()));
            }
            return this.quiescenceSearch.quiescenceSearch(board, alpha, beta);
        }

        Assert.assertTrue(depth >= 1);

        /*
        Mate Distance Pruning:
        prefer closer wins and further loses 
         */
        if (this.engine.getEngineSpecifications().ALLOW_MATE_DISTANCE_PRUNING){
            alpha = Math.max(alpha, IN_CHECKMATE_SCORE + ply);
            beta = Math.min(beta, -IN_CHECKMATE_SCORE - ply - 1);
            if (alpha >= beta){
                return alpha;
            }
        }
        
        /*
        Transposition Table Lookup:
        if possible, retrieve previously found data from singleton transposition table 
         */
        Move hashMove = null;
        TranspositionTable.TableObject previousTableData = table.get(zobristHash.getBoardHash());
        int score = 0;
        if (previousTableData != null && ply > 0) {
            score = previousTableData.getScore(ply);
            hashMove = previousTableData.getMove();
            if (previousTableData.getDepth() >= depth) {
                TranspositionTable.TableObject.Flag flag = previousTableData.getFlag();
                if (flag == EXACT) {
                    this.engine.statistics.numberOfExacts++;
                    if (ply == 0){
                        aiMove = copyMove(hashMove);
                    }
                    return score;
                } else if (flag == LOWERBOUND) {
                    this.engine.statistics.numberOfLowerBounds++;
                    alpha = Math.max(alpha, score);
                } else if (flag == UPPERBOUND) {
                    this.engine.statistics.numberOfUpperBounds++;
                    beta = Math.min(beta, score);
                }
                if (alpha >= beta) {
                    this.engine.statistics.numberOfHashBetaCutoffs++;
                    if (ply == 0){
                        aiMove = copyMove(hashMove);
                    }
                    return score;
                }
            }
        }
        
        /*
        Principle Variation:
        only perform certain reductions if we are not in the most likely branch of the tree
         */
        int staticBoardEval = this.evaluator.SHORT_MINIMUM;
        boolean thisIsAPrincipleVariationNode = beta - alpha != 1;
        boolean boardInCheck = boardInCheck(board, board.isWhiteTurn());
        boolean debug = this.engine.INFO_LOG;
        if (!thisIsAPrincipleVariationNode && !boardInCheck) {
            List<Move> moves = generateLegalMoves(board, board.isWhiteTurn());
            staticBoardEval = this.evaluator.eval(board, board.isWhiteTurn(), moves);

            if(previousTableData != null){
                if (previousTableData.getFlag() == EXACT
                        || (previousTableData.getFlag() == UPPERBOUND && previousTableData.getScore(ply) < staticBoardEval)
                        || (previousTableData.getFlag() == LOWERBOUND && previousTableData.getScore(ply) > staticBoardEval)){
                    staticBoardEval = previousTableData.getScore(ply);
                }
            }
            
            /*
            Beta Razoring:
            if current node has a very high score, return eval
             */
            if (this.engine.getEngineSpecifications().ALLOW_BETA_RAZORING){
                if (isBetaRazoringMoveOkHere(board, evaluator, depth, staticBoardEval)){
                    int specificBetaRazorMargin = betaRazorMargin[depth];
                    if (staticBoardEval - specificBetaRazorMargin >= beta){
                        if (debug){
                            this.engine.statistics.numberOfSuccessfulBetaRazors++;
                        }
                        return staticBoardEval;
                    }
                }
            }
            
            
            /*
            Alpha Razoring:
            if current node has a very low score, perform Quiescence search to try to find a cutoff
             */
            if (this.engine.getEngineSpecifications().ALLOW_ALPHA_RAZORING){
                if (isAlphaRazoringMoveOkHere(board, evaluator, depth, alpha)){
                    int specificAlphaRazorMargin = alphaRazorMargin[depth];
                    if (staticBoardEval + specificAlphaRazorMargin < alpha){
                        int qScore = this.quiescenceSearch
                                .quiescenceSearch(board, 
                                        alpha - specificAlphaRazorMargin, 
                                        alpha - specificAlphaRazorMargin + 1);

                        if (qScore + specificAlphaRazorMargin <= alpha){
                            if (debug){
                                this.engine.statistics.numberOfSuccessfulAlphaRazors++;
                            }
                            return qScore;
                        }
                        else if (debug){
                            this.engine.statistics.numberOfFailedAlphaRazors++;
                        }
                    }
                }
            }
            
            /*
            Null Move Pruning:
            if not in dangerous position, forfeit a move and make shallower null window search
             */
            if (this.engine.getEngineSpecifications().ALLOW_NULL_MOVE_PRUNING) {
                if (nullMoveCounter < 2 && !reducedSearch && isNullMoveOkHere(board)) {
                    Assert.assertTrue(depth >= 1);
                    Assert.assertTrue(alpha < beta);

                    makeNullMove(board, zobristHash);

                    int reducedDepth = depth - nullMoveDepthReduction(depth) - 1;

                    int nullScore = reducedDepth <= 0 ?

                            -this.quiescenceSearch.quiescenceSearch(board, 
                                    -beta,
                                    -beta + 1)

                            : -principleVariationSearch(board, zobristHash, startTime, timeLimitMillis,
                            originalDepth, reducedDepth, ply + 1,
                            -beta, -beta + 1, nullMoveCounter + 1, true);

                    unMakeNullMove(board, zobristHash);

                    if (nullScore >= beta) {

                        if (nullScore > CHECKMATE_ENEMY_SCORE_MAX_PLY){
                            nullScore = beta;
                        }

                        if (debug) {
                            this.engine.statistics.numberOfNullMoveHits++;
                        }
                        return nullScore;
                    }
                    if (debug) {
                        this.engine.statistics.numberOfNullMoveMisses++;
                    }
                }
            }
        }
        
        /*
        Move Ordering:
        place moves most likely to cause cutoffs at the front of the move list (hashmoves, killers, captures)
         */
        List<Move> orderedMoves;
        if (previousTableData == null) {
            /*
            Internal Iterative Deepening:
            when no hashtable entry, pv node and not endgame, perform shallower search to add a good move to table
             */
            if (this.engine.getEngineSpecifications().ALLOW_INTERNAL_ITERATIVE_DEEPENING){
                if (isIIDAllowedHere(board, depth, reducedSearch, thisIsAPrincipleVariationNode)){
                    if (debug){
                        this.engine.statistics.numberOfIIDs++;
                    }

                    int reducedIIDDepth = depth - iidDepthReduction - 1;
                    
                    principleVariationSearch(board, zobristHash,
                            startTime, timeLimitMillis, originalDepth,
                            reducedIIDDepth, ply,
                            alpha, beta, nullMoveCounter, true);

                    previousTableData = table.get(zobristHash.getBoardHash());
                    if (previousTableData == null){
                        this.engine.statistics.numberOfFailedIIDs++;
                    }
                    else {
                        this.engine.statistics.numberOfSuccessfulIIDs++;
                    }
                }
            }
        }

        if (previousTableData != null) {
            if (debug) {
                this.engine.statistics.numberOfSearchesWithHash++;
            }
            orderedMoves = this.moveOrderer.orderedMoves(board, board.isWhiteTurn(), ply, hashMove, aiMove);

        }
        else{
            if (debug) {
                this.engine.statistics.numberOfSearchesWithoutHash++;
            }

            orderedMoves = this.moveOrderer.orderedMoves(board, board.isWhiteTurn(), ply, null, aiMove);
        }

        int originalAlpha = alpha;
        int bestScore = this.evaluator.SHORT_MINIMUM;
        Move bestMove = null;
        
        /*
        iterate through fully legal moves
         */
        int numberOfMovesSearched = 0;
        for (Move move : orderedMoves){
            // consider getting this from move orderer
            boolean captureMove = this.moveOrderer.moveIsCapture(board, move);
            boolean promotionMove = MoveParser.isPromotionMove(move);
            boolean givesCheckMove = this.moveOrderer.checkingMove(board, move);
            boolean pawnToSix = this.moveOrderer.moveWillBePawnPushSix(board, move);
            boolean pawnToSeven = this.moveOrderer.moveWillBePawnPushSeven(board, move);

            if (!maybeInEndgame(board)
                    && (MoveParser.isPromotionToKnight(move) ||
                    MoveParser.isPromotionToBishop(move) ||
                    MoveParser.isPromotionToRook(move))){
                continue;
            }

            /*
            Can we prune this move?
             */
            if (!thisIsAPrincipleVariationNode && !boardInCheck && numberOfMovesSearched > 0) {
                if (!captureMove) {
                    /*
                    Late Move Pruning:
                    before making move, see if we can prune this move
                     */
                    if (this.engine.getEngineSpecifications().ALLOW_LATE_MOVE_PRUNING) {
                        if (bestScore < CHECKMATE_ENEMY_SCORE_MAX_PLY
                                && !onlyPawnsLeftForPlayer(board, board.isWhiteTurn())) {
                            if (!promotionMove
                                    && !givesCheckMove
                                    && !pawnToSix
                                    && !pawnToSeven
                                    && depth <= 4
                                    && numberOfMovesSearched >= depth * 3 + 4) {

                                if (debug) {
                                    this.engine.statistics.numberOfLateMovePrunings++;
                                }
                                continue;
                            }
                        }
                    }
                
                    /*
                    (Extended) Futility Pruning:
                    if score + margin smaller than alpha, skip this move
                     */
                    if (this.engine.getEngineSpecifications().ALLOW_FUTILITY_PRUNING) {
                        if (isFutilityPruningAllowedHere(board, move, depth,
                                promotionMove, givesCheckMove, pawnToSix, pawnToSeven)) {

                            if (staticBoardEval == this.evaluator.SHORT_MINIMUM) {
                                staticBoardEval = this.evaluator.eval(board, board.isWhiteTurn(),
                                        generateLegalMoves(board, board.isWhiteTurn()));
                            }

                            int futilityScore = staticBoardEval + futilityMargin[depth];

                            if (futilityScore <= alpha) {
                                if (debug) {
                                    this.engine.statistics.numberOfSuccessfulFutilities++;
                                }
                                if (futilityScore > bestScore) {
                                    bestScore = futilityScore;
                                }
                                continue;
                            } else if (debug) {
                                this.engine.statistics.numberOfFailedFutilities++;
                            }
                        }
                    }
                }
                else {
                    /*
                    Static Exchange Evaluation:
                    if an exchange promises a large loss of material skip it, more if further from root
                     */
                    if (this.engine.getEngineSpecifications().ALLOW_SEE_PRUNING) {
                        if (depth <= 5) {
                            int seeScore = seeScore(board, move, evaluator);
                            if (seeScore < -100 * depth) {
                                this.engine.statistics.numberOfSuccessfulSEEs++;
                                continue;
                            }
                        }
                    }
                }
            }

            makeMoveAndHashUpdate(board, move, zobristHash);
            numberOfMovesSearched++;
            if (debug){
                this.engine.statistics.numberOfMovesMade++;
            }

            boolean enemyInCheck = boardInCheck(board, board.isWhiteTurn());

            if (isDrawByRepetition(board, zobristHash) || isDrawByInsufficientMaterial(board)){
                score = IN_STALEMATE_SCORE;
            }
            else { 
                /*
                score now above alpha, therefore if no special cases apply, we do a full search
                 */
                score = alpha + 1;

                /*
                Late Move Reductions:
                search later ordered safer moves to a lower depth
                 */
                if (this.engine.getEngineSpecifications().ALLOW_LATE_MOVE_REDUCTIONS
                        && isLateMoveReductionAllowedHere(board, move, depth,
                        numberOfMovesSearched, reducedSearch, captureMove,
                        givesCheckMove, promotionMove, pawnToSix, pawnToSeven)) {

                    if (debug) {
                        this.engine.statistics.numberOfLateMoveReductions++;
                    }
                
                    /*
                    lower depth search
                     */
                    int lowerDepth = depth - lateMoveDepthReduction(depth) - 1;

                    score = -principleVariationSearch
                            (board, zobristHash, startTime, timeLimitMillis,
                                    originalDepth, lowerDepth, ply + 1,
                                    -alpha - 1, -alpha, 0, true);

                    /*
                    if a lower move seems good, full depth research
                     */
                    if (score > alpha) {
                        score = -principleVariationSearch(board, zobristHash,
                                startTime, timeLimitMillis,
                                originalDepth, depth - 1, ply + 1,
                                -alpha - 1, -alpha, 0, false);

                        if (debug) {
                            this.engine.statistics.numberOfLateMoveReductionsMisses++;
                        }
                    } else if (debug) {
                        this.engine.statistics.numberOfLateMoveReductionsHits++;
                    }
                }
            
                /*
                Principle Variation Search:
                moves that are not favourite (PV) are searched with a null window
                 */
                else if (this.engine.getEngineSpecifications().ALLOW_PRINCIPLE_VARIATION_SEARCH 
                        && numberOfMovesSearched > 1) {

                    score = -principleVariationSearch(board, zobristHash,
                            startTime, timeLimitMillis,
                            originalDepth, depth - 1, ply + 1,
                            -alpha - 1, -alpha, 0, reducedSearch);
                
                    /*
                    if this line of play would improve our score, do full re-search (implemented slightly lower down)
                     */
                    if (score > alpha && debug) {
                        this.engine.statistics.numberOfPVSMisses++;
                    } else {
                        this.engine.statistics.numberOfPVSHits++;
                    }
                }

                /*
                always search PV node fully + full re-search of moves that showed promise
                 */
                if (score > alpha) {
                    score = -principleVariationSearch(board, zobristHash,
                            startTime, timeLimitMillis,
                            originalDepth, depth - 1, ply + 1,
                            -beta, -alpha, 0, false);
                }
            }

            UnMakeMoveAndHashUpdate(board, zobristHash);
            
            /*
            record score and move if better than previous ones
             */
            if (score > bestScore){
                bestScore = score;
                bestMove = copyMove(move);
                alpha = Math.max(alpha, score);
                if (ply == 0) {
                    aiMove = copyMove(move);
                }
            }

            /*
            Alpha Beta Pruning:
            represents a situation which is too good, or too bad, and will not occur in normal play, so stop searching further
             */
            if (alpha >= beta){
                if (debug) {
                    this.engine.statistics.statisticsFailHigh(ply, numberOfMovesSearched, move);
                }

                if (!this.moveOrderer.moveIsCapture(board, move)){
                    /*
                    Killer Moves:
                    record this cutoff move, because we will try out in sister nodes
                     */
                    if (this.engine.getEngineSpecifications().ALLOW_KILLERS) {
                        updateKillerMoves(move, ply);
                    }
                    if (this.engine.getEngineSpecifications().ALLOW_HISTORY_MOVES) {
                        this.moveOrderer.updateHistoryMoves(move, ply);
                    }

                    if (this.engine.getEngineSpecifications().ALLOW_MATE_KILLERS){
                        if (alpha > CHECKMATE_ENEMY_SCORE_MAX_PLY){
                            mateKiller[ply] = copyMove(move);
                        }
                    }
                }
                break;
            }
        }

        if (numberOfMovesSearched == 0) {
            if (boardInCheck) {
                if (debug) {
                    this.engine.statistics.numberOfCheckmates++;
                }
                return IN_CHECKMATE_SCORE + ply;
            }
            else {
                if (debug) {
                    this.engine.statistics.numberOfStalemates++;
                }
                return IN_STALEMATE_SCORE;
            }
        }

        /*
        Transposition Tables:
        add information to table with flag based on the node type
         */
        TranspositionTable.TableObject.Flag flag;
        if (bestScore <= originalAlpha){
            flag = UPPERBOUND;
        } else if (bestScore >= beta) {
            flag = LOWERBOUND;
        } else {
            flag = EXACT;
        }

        table.put(zobristHash.getBoardHash(),
                new TranspositionTable.TableObject(bestMove, bestScore, depth,
                        flag));

        return bestScore;
    }

    public Move getAiMove() {
        return aiMove;
    }

}
