package com.github.louism33.axolotl.evaluation;

import com.github.louism33.chesscore.Chessboard;
import org.junit.Assert;

import java.util.Arrays;

import static com.github.louism33.axolotl.evaluation.EvalPrintObject.*;
import static com.github.louism33.axolotl.evaluation.EvaluationConstants.*;
import static com.github.louism33.axolotl.evaluation.EvaluatorPositionConstant.POSITION_SCORES;
import static com.github.louism33.axolotl.evaluation.EvaluatorPositionConstant.mobilityScores;
import static com.github.louism33.axolotl.evaluation.Init.*;
import static com.github.louism33.chesscore.BitOperations.fileForward;
import static com.github.louism33.chesscore.BitOperations.*;
import static com.github.louism33.chesscore.BoardConstants.*;
import static com.github.louism33.chesscore.PieceMove.*;
import static java.lang.Long.numberOfTrailingZeros;

@SuppressWarnings("ALL")
public final class Evaluator {

    static boolean isEndgame = false;

    static int[] whiteThreatsToSquare = new int[64];
    static int[] blackThreatsToSquare = new int[64];

    static void resetThreats(){
        Arrays.fill(whiteThreatsToSquare, 0);
        Arrays.fill(blackThreatsToSquare, 0);
    }

    public static void printEval(Chessboard board, int turn){
        printEval(board, turn, board.generateLegalMoves());
    }
    public static void printEval(Chessboard board, int turn, int[] moves){
        eval(board, moves);
        EvalPrintObject epo = new EvalPrintObject(scoresForEPO);
        epo.turn = board.turn;
        System.out.println(epo);
    }

    public static int percentOfEndgame;
    public static int percentOfStartgame;

    /**
     todo:
     trapped pieces
     pinned pieces, and to queen
     */
    static final long[] turnThreatensSquares = new long[2];
    
    public static int eval(final Chessboard board, final int[] moves) {
        Assert.assertTrue(moves != null);

        Arrays.fill(scoresForEPO[WHITE], 0);
        Arrays.fill(scoresForEPO[BLACK], 0);

        init(board, WHITE);
        init(board, BLACK);

        percentOfEndgame = getPercentageOfEndgameness(board);
        percentOfStartgame = 100 - percentOfEndgame;

        EvalPrintObject.percentOfEndgame = percentOfEndgame;

        final int turn = board.turn;

        int score = evalTurn(board, turn) - evalTurn(board, 1 - turn);

        int myPassedPawnScore = PassedPawns.evalPassedPawnsByTurn(board, turn);
        int enemyPassedPawnScore = PassedPawns.evalPassedPawnsByTurn(board, 1 - turn);

        scoresForEPO[turn][passedPawnsScore] = myPassedPawnScore;
        scoresForEPO[1 - turn][passedPawnsScore] = enemyPassedPawnScore;

        score += myPassedPawnScore;
        score -= enemyPassedPawnScore;
        
        int mks = attackingEnemyKingLookup[1 - turn] >= 0
                ? attackingEnemyKingLookup[1 - turn]
                : 0;

        if (mks >= KING_SAFETY_ARRAY.length) {
            mks = KING_SAFETY_ARRAY.length - 1;
        }
        int myKingSafety = -KING_SAFETY_ARRAY[mks];

        int yks = attackingEnemyKingLookup[turn] >= 0
                ? attackingEnemyKingLookup[turn]
                : 0;

        if (yks >= KING_SAFETY_ARRAY.length) {
            yks = KING_SAFETY_ARRAY.length - 1;
        }
        int yourKingSafety = -KING_SAFETY_ARRAY[yks];

        myKingSafety = (percentOfStartgame * myKingSafety) / 100;
        yourKingSafety = (percentOfStartgame * yourKingSafety) / 100;

        scoresForEPO[turn][kingSafetyScore] += myKingSafety;
        scoresForEPO[1 - turn][kingSafetyScore] += yourKingSafety;

        score += myKingSafety;
        score -= yourKingSafety;

        final int turnBonus = (percentOfStartgame * MY_TURN_BONUS) / 100;
        scoresForEPO[WHITE][turnScore] = turnBonus;
        score += turnBonus;

        scoresForEPO[turn][totalScore] = score;
        scoresForEPO[1 - turn][totalScore] = score;
        return score;
    }

    public static int[][] scoresForEPO = new int[2][32];

    private final static int evalTurn(Chessboard board, int turn){
        //please generate moves before calling this
        final long[][] pieces = board.pieces;
        
        long squaresIThreatenWithPieces = 0;

        long myPawns, myKnights, myBishops, myRooks, myQueens, myKing;
        long enemyPawns, enemyKnights, enemyBishops, enemyRooks, enemyQueens, enemyKing;
        final long friends, enemies;

        myPawns = pieces[turn][PAWN];
        myKnights = pieces[turn][KNIGHT];
        myBishops = pieces[turn][BISHOP];
        myRooks = pieces[turn][ROOK];
        myQueens = pieces[turn][QUEEN];
        myKing = pieces[turn][KING];

        enemyPawns = pieces[1 - turn][PAWN];
        enemyKnights = pieces[1 - turn][KNIGHT];
        enemyBishops = pieces[1 - turn][BISHOP];
        enemyRooks = pieces[1 - turn][ROOK];
        enemyQueens = pieces[1 - turn][QUEEN];
        enemyKing = pieces[1 - turn][KING];

        friends = pieces[turn][ALL_COLOUR_PIECES];
        enemies = pieces[1 - turn][ALL_COLOUR_PIECES];

        Assert.assertTrue(friends != 0);
        Assert.assertTrue(enemies != 0);

        final long allPieces = friends | enemies;
        final long pinnedPieces = board.pinnedPieces;
        final boolean inCheck = board.inCheckRecorder;

        int finalScore = 0, materialScore = 0;

        materialScore += populationCount(myPawns) * PAWN_SCORE;
        materialScore += populationCount(myKnights) * KNIGHT_SCORE;
        materialScore += populationCount(myBishops) * BISHOP_SCORE;
        materialScore += populationCount(myRooks) * ROOK_SCORE;
        materialScore += populationCount(myQueens) * QUEEN_SCORE;

        scoresForEPO[turn][EvalPrintObject.materialScore] = materialScore;

        finalScore += materialScore;

        final long ignoreThesePieces = 0; // maybe pins

        final long enemyBigPieces = enemies & ~enemyPawns;

        int positionScore = 0;
        int mobilityScore = 0;

        int threatsScore = 0;

        final long squaresMyPawnsThreaten = pawnTables[turn][CAPTURES];
        final long squaresEnemyPawnsThreaten = pawnTables[1 - turn][CAPTURES];

        final long myPawnAttackSpan = pawnTables[turn][SPANS];
        final long enemyPawnAttackSpan = pawnTables[1 - turn][SPANS];

        final boolean whiteToPlay = turn == WHITE;
        final long outpostRanks = whiteToPlay ? (RANK_FOUR | RANK_FIVE | RANK_SIX) : (RANK_FIVE | RANK_FOUR | RANK_THREE);
        final long unthreatenableOutpostSpots = outpostRanks & ~(enemyPawnAttackSpan | squaresEnemyPawnsThreaten);

        final long fileWithoutMyPawns = filesWithNoPlayerPawns[turn];
        final long fileWithoutEnemyPawns = filesWithNoPlayerPawns[1 - turn];
        final long openFiles = fileWithoutEnemyPawns & fileWithoutMyPawns;
        final long closedFiles = ~openFiles;

        final long enemyPawnShifts = whiteToPlay ? enemyPawns >>> 8 : enemyPawns << 8;
        final long myBlockedPawns = enemyPawnShifts & myPawns;
        final long myBackwardsPawns = myPawns & (PENULTIMATE_RANKS[1 - turn] | INTERMEDIATE_RANKS[turn]);

        final long safeMobSquares = ~(myKing | myQueens | myBackwardsPawns | myBlockedPawns | squaresEnemyPawnsThreaten);

        if (percentOfStartgame != 0) {
            //space
            long mySafeSquares = (~myPawns
                    & (whiteToPlay ? (RANK_TWO | RANK_THREE | RANK_FOUR) : (RANK_FIVE | RANK_SIX | RANK_SEVEN))
                    & (FILE_C | FILE_D | FILE_E | FILE_F)
                    & ~squaresEnemyPawnsThreaten);

            int spaceScore = populationCount(mySafeSquares) * SPACE;

            long myDevelopedPawns = myPawns & ~(RANK_SEVEN | RANK_TWO);
            while (myDevelopedPawns != 0) {
                final int pawnIndex = numberOfTrailingZeros(myDevelopedPawns);
                final long fileBack = fileForward(pawnIndex, turn == BLACK) & mySafeSquares;

                spaceScore += populationCount(fileBack) * (1 + SPACE);
                myDevelopedPawns &= myDevelopedPawns - 1;
            }

            finalScore += spaceScore;

            scoresForEPO[turn][EvalPrintObject.spaceScore] = spaceScore;
        }

        final long enemyKingSmallArea = squareCentredOnIndex(numberOfTrailingZeros(enemyKing));
        final long enemyKingSafetyArea = kingSafetyArea[1 - turn];

        final long myKingSmallArea = squareCentredOnIndex(numberOfTrailingZeros(myKing));
        final long myKingSafetyArea = kingSafetyArea[turn];

        int defendingMyKingLookup = 0;

        final long allPawns = myPawns | enemyPawns;

        final int numberOfPawns = populationCount(allPawns);

        final long wps = WHITE_COLOURED_SQUARES & allPawns;
        final long bps = BLACK_COLOURED_SQUARES & allPawns;

        final long wpsc = wps & (centreNineSquares ^ centreFourSquares);
        final long bpsc = bps & (centreNineSquares ^ centreFourSquares);

        final long wpscc = wps & centreFourSquares;
        final long bpscc = bps & centreFourSquares;

        final long developpedPawns = myPawns & ~PENULTIMATE_RANKS[1 - turn];
        final long behindPawnSpots = whiteToPlay ? developpedPawns >>> 8 : developpedPawns << 8;

        int knightsScore = 0;

        while (myKnights != 0){
            final long knight = getFirstPiece(myKnights);
            if ((knight & ignoreThesePieces) == 0) {
                final int knightIndex = numberOfTrailingZeros(knight);
                positionScore += POSITION_SCORES[turn][KNIGHT][63 - knightIndex];

                final long pseudoMoves = KNIGHT_MOVE_TABLE[knightIndex];
                final long table = pseudoMoves & safeMobSquares;

                squaresIThreatenWithPieces |= pseudoMoves;
                
                mobilityScore += mobilityScores[KNIGHT - 2][populationCount(table)];

                knightsScore += numberOfPawns * KNIGHT_PAWN_NUMBER_BONUS;

                if ((knight & squaresMyPawnsThreaten) != 0) {
                    knightsScore += KNIGHT_PROTECTED_PAWN;
                }

                //outpost, double score if defended by friendly pawn
                if ((knight & unthreatenableOutpostSpots) != 0) {
                    knightsScore += KNIGHT_ON_OUTPOST_BONUS * (1 + (populationCount(squaresMyPawnsThreaten & knight)));
                } else {
                    long myThreatsToEmptyOutposts = table & (unthreatenableOutpostSpots & ~friends);
                    if (myThreatsToEmptyOutposts != 0) {
                        knightsScore += KNIGHT_REACH_OUTPOST_BONUS * (1 + (populationCount(squaresMyPawnsThreaten & myThreatsToEmptyOutposts)));
                    }
                }

                if ((knight & behindPawnSpots) != 0) {
                    knightsScore += PIECE_BEHIND_PAWN;
                }

                if ((knight & enemyKingSafetyArea) != 0) {
                    attackingEnemyKingLookup[turn] += KNIGHT_ATTACK_KING_UNITS;
                }

                attackingEnemyKingLookup[turn] += populationCount(table & enemyKingSafetyArea) * KNIGHT_ATTACK_KING_UNITS;
            }
            myKnights &= (myKnights - 1);
        }

        int bishopsScore = 0;

        if (populationCount(myBishops) >= 2) {
            bishopsScore += BISHOP_DOUBLE;
        }

        while (myBishops != 0){
            final long bishop = getFirstPiece(myBishops);
            if ((bishop & ignoreThesePieces) == 0) {
                final int bishopIndex = numberOfTrailingZeros(bishop);
                positionScore += POSITION_SCORES[turn][BISHOP][63 - bishopIndex];

                final long pseudoMoves = singleBishopTable(allPieces, bishopIndex, UNIVERSE);
                final long table = pseudoMoves & safeMobSquares;

                squaresIThreatenWithPieces |= pseudoMoves;
                
                mobilityScore += mobilityScores[BISHOP - 2][populationCount(table)];

                if ((bishop & squaresMyPawnsThreaten) != 0) {
                    bishopsScore += BISHOP_PROTECTED_PAWN;
                }

                //outpost, double score if defended by friendly pawn
                if ((bishop & unthreatenableOutpostSpots) != 0) {
                    bishopsScore += BISHOP_ON_OUTPOST_BONUS * (1 + (populationCount(squaresMyPawnsThreaten & bishop)));
                } else {
                    long myThreatsToEmptyOutposts = table & (unthreatenableOutpostSpots & ~friends);
                    if (myThreatsToEmptyOutposts != 0) {
                        bishopsScore += BISHOP_REACH_OUTPOST_BONUS * (1 + (populationCount(squaresMyPawnsThreaten & myThreatsToEmptyOutposts)));
                    }
                }

                if ((bishop & behindPawnSpots) != 0) {
                    bishopsScore += PIECE_BEHIND_PAWN;
                }

                if (populationCount(pseudoMoves & centreFourSquares) > 1) {
                    bishopsScore += BISHOP_PRIME_DIAGONAL;
                }

                if ((bishop & WHITE_COLOURED_SQUARES) != 0) {
                    bishopsScore -= (BISHOP_COLOUR_PAWNS * populationCount(wps) *
                            (1 + populationCount(wpscc) + populationCount(wpsc) / 2));
                } else {
                    bishopsScore -= (BISHOP_COLOUR_PAWNS * populationCount(bps) *
                            (1 + populationCount(bpscc) + populationCount(bpsc) / 2));
                }


                if ((bishop & enemyKingSafetyArea) != 0) {
                    attackingEnemyKingLookup[turn] += BISHOP_ATTACK_KING_UNITS;
                }

                attackingEnemyKingLookup[turn] += populationCount(table & enemyKingSafetyArea) * BISHOP_ATTACK_KING_UNITS;
            }
            myBishops &= (myBishops - 1);
        }

        int rooksScore = 0;

        while (myRooks != 0){
            final long rook = getFirstPiece(myRooks);
            if ((rook & ignoreThesePieces) == 0) {
                final int rookIndex = numberOfTrailingZeros(rook);
                positionScore += POSITION_SCORES[turn][ROOK][63 - rookIndex];

                final long pseudoMoves = singleRookTable(allPieces, rookIndex, UNIVERSE);
                final long table = pseudoMoves & safeMobSquares;

                squaresIThreatenWithPieces |= pseudoMoves;
                
                mobilityScore += mobilityScores[ROOK - 2][populationCount(table)];

                //table does not include pawns defended by pawns
                rooksScore += (populationCount(table & enemyPawns)) * ROOKS_ATTACK_UNDEFENDED_PAWNS;

                //trapped by king
                if (percentOfStartgame > 50) {
                    final long startingSpotsForKing = whiteToPlay
                            ? (INITIAL_WHITE_KING | INITIAL_WHITE_QUEEN)
                            : (INITIAL_BLACK_KING | INITIAL_BLACK_QUEEN);
                    if (((myKing & startingSpotsForKing) != 0)
                            && populationCount(pseudoMoves) <= 5
                            && ((rook & FINAL_RANKS[1 - turn]) != 0)) {
                        rooksScore -= TRAPPED_ROOK;
                        if ((board.castlingRights & (whiteToPlay ? 0b11 : 0b1100)) == 0) {
                            rooksScore -= TRAPPED_ROOK;
                        }
                    }
                }

                if ((rook & PENULTIMATE_RANKS[turn]) != 0) {
                    rooksScore += (percentOfStartgame * ROOK_ON_SEVENTH_BONUS) / 100;
                }

                if ((myRooks & (FILES[rookIndex % 8] ^ rook)) != 0) {
                    rooksScore += ROOK_BATTERY_SCORE;
                }

                if ((rook & openFiles) != 0) {
                    rooksScore += ROOK_OPEN_FILE_BONUS;
                } else if ((rook & fileWithoutMyPawns) != 0) {
                    rooksScore += ROOK_ON_SEMI_OPEN_FILE_BONUS;
                }

                if ((rook & enemyKingSafetyArea) != 0) {
                    attackingEnemyKingLookup[turn] += ROOK_ATTACK_KING_UNITS;
                }

                attackingEnemyKingLookup[turn] += populationCount(table & enemyKingSafetyArea) * ROOK_ATTACK_KING_UNITS;
            }
            myRooks &= (myRooks - 1);
        }
      
        int queensScore = 0;

        long defendedByMyQueen = 0;

        while (myQueens != 0){
            final long queen = getFirstPiece(myQueens);
            if ((queen & ignoreThesePieces) == 0) {
                final int queenIndex = numberOfTrailingZeros(queen);
                positionScore += POSITION_SCORES[turn][QUEEN][63 - queenIndex];

                long pseudoMoves = singleQueenTable(allPieces, queenIndex, UNIVERSE);
                final long table = pseudoMoves & safeMobSquares;

                squaresIThreatenWithPieces |= pseudoMoves;
                
                defendedByMyQueen |= pseudoMoves;

                mobilityScore += mobilityScores[QUEEN - 2][populationCount(table)];

                if ((queen & enemyKingSafetyArea) != 0) {
                    attackingEnemyKingLookup[turn] += QUEEN_ATTACK_KING_UNITS;
                }

                attackingEnemyKingLookup[turn] += populationCount(table & enemyKingSafetyArea) * QUEEN_ATTACK_KING_UNITS;
            }
            myQueens &= (myQueens - 1);
        }

        attackingEnemyKingLookup[turn] += populationCount(squaresMyPawnsThreaten & enemyKingSafetyArea);

        myPawns = pieces[turn][PAWN] & ~ignoreThesePieces;
        attackingEnemyKingLookup[1 - turn] -= populationCount(myPawns & myKingSafetyArea);
        attackingEnemyKingLookup[1 - turn] -= populationCount(myPawns & myKingSmallArea);
        attackingEnemyKingLookup[1 - turn] -= populationCount(squaresMyPawnsThreaten & myKingSafetyArea);

        /*
        regular pawns
         */
        int pawnsScore = 0;
        pawnsScore += populationCount(squaresMyPawnsThreaten & myPawns) * PAWN_PROTECTED_BY_PAWNS;
        while (myPawns != 0){
            final long pawn = getFirstPiece(myPawns);
            final int pawnIndex = numberOfTrailingZeros(pawn);
            positionScore += POSITION_SCORES[turn][PAWN][63 - pawnIndex];

            if ((pawn & enemyKingSafetyArea) != 0) {
                attackingEnemyKingLookup[turn] += 1;
            }

            myPawns &= myPawns - 1;
        }
        

        threatsScore += populationCount(squaresMyPawnsThreaten & enemyBigPieces) * PAWN_THREATENS_BIG_THINGS;

        attackingEnemyKingLookup[turn] += populationCount(squaresMyPawnsThreaten & enemyKingSafetyArea);

        Assert.assertTrue(percentOfEndgame >= 0 && percentOfEndgame <= 100);
        Assert.assertTrue(percentOfStartgame >= 0 && percentOfStartgame <= 100);

       
        /*
        king
         */
        int kingIndex = numberOfTrailingZeros(myKing);
        long kingPseudoMoves = KING_MOVE_TABLE[kingIndex];
        
        squaresIThreatenWithPieces |= kingPseudoMoves;
        
        positionScore += (percentOfStartgame == 0 ? 0
                : (percentOfStartgame * POSITION_SCORES[turn][KING][63 - kingIndex]) / 100);

        positionScore += percentOfEndgame == 0 ? 0
                : (percentOfEndgame * POSITION_SCORES[turn][KING-KING][63 - kingIndex]) / 100;

        if (board.pieces[turn][QUEEN] == 0) {
            attackingEnemyKingLookup[turn] -= 2;
        }

        if ((myKingSafetyArea & fileWithoutMyPawns) != 0) {
            attackingEnemyKingLookup[1 - turn] += KING_NEAR_SEMI_OPEN_FILE_LOOKUP;
        }


        turnThreatensSquares[turn] = squaresIThreatenWithPieces;


        finalScore += positionScore;
        finalScore += mobilityScore;
        finalScore += threatsScore;

        finalScore += pawnsScore;
        finalScore += knightsScore;
        finalScore += bishopsScore;
        finalScore += rooksScore;
        finalScore += queensScore;

        scoresForEPO[turn][EvalPrintObject.pawnScore] = pawnsScore;
        scoresForEPO[turn][EvalPrintObject.knightScore] = knightsScore;
        scoresForEPO[turn][EvalPrintObject.bishopScore] = bishopsScore;
        scoresForEPO[turn][EvalPrintObject.rookScore] = rooksScore;
        scoresForEPO[turn][EvalPrintObject.queenScore] = queensScore;

        scoresForEPO[turn][EvalPrintObject.mobilityScore] = mobilityScore;
        scoresForEPO[turn][EvalPrintObject.positionScore] = positionScore;
        scoresForEPO[turn][EvalPrintObject.threatsScore] = threatsScore;

        return finalScore;
    }


    static int getPercentageOfEndgameness(Chessboard board) {
        int answer = 150;

        int pawnCountW = populationCount(board.pieces[WHITE][PAWN]);
        int pawnCountB = populationCount(board.pieces[BLACK][PAWN]);

        if (pawnCountB + pawnCountW < 2) {
            return 100;
        }

        answer -= pawnCountW * 2;
        answer -= pawnCountB * 2;

        if (pawnCountB < 3) {
            answer += 15;
        }

        if (pawnCountW < 3) {
            answer += 15;
        }

        long wk = board.pieces[WHITE][KNIGHT];
        long wb = board.pieces[WHITE][BISHOP];
        long wr = board.pieces[WHITE][ROOK];
        long wq = board.pieces[WHITE][QUEEN];

        long bk = board.pieces[BLACK][KNIGHT];
        long bb = board.pieces[BLACK][BISHOP];
        long br = board.pieces[BLACK][ROOK];
        long bq = board.pieces[BLACK][QUEEN];

        answer -= populationCount(wk) * 5;
        answer -= populationCount(bk) * 5;

        answer -= populationCount(wb) * 7;
        answer -= populationCount(bb) * 7;

        answer -= populationCount(wr) * 10;
        answer -= populationCount(br) * 10;

        answer -= populationCount(wq) * 22;
        answer -= populationCount(bq) * 22;

        answer += board.fullMoveCounter / 5;

        if (answer < 0) {
            return 0;
        }

        if (answer > 100) {
            return 100;
        }

        return answer;
    }
}
