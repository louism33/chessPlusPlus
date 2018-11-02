package javacode.chessengine;

import javacode.chessprogram.chess.Chessboard;
import javacode.chessprogram.chess.Move;
import javacode.evaluation.Evaluator;

import java.util.List;

import static javacode.chessengine.AspirationSearch.aspirationSearch;
import static javacode.chessengine.Engine.*;
import static javacode.chessprogram.moveGeneration.MoveGeneratorMaster.generateLegalMoves;
import static javacode.evaluation.Evaluator.*;
import static javacode.evaluation.Evaluator.IN_CHECKMATE_SCORE;
import static javacode.evaluation.Evaluator.IN_CHECKMATE_SCORE_MAX_PLY;

class IterativeDeepeningDFS {

    private static TranspositionTable table = TranspositionTable.getInstance();

    static Move iterativeDeepeningWithAspirationWindows(Chessboard board, ZobristHash zobristHash, long startTime, long timeLimitMillis){
        int maxDepth = ALLOW_TIME_LIMIT ? 10000 : MAX_DEPTH;
        int aspirationScore = 0;
        
        List<Move> rootMoves = generateLegalMoves(board, board.isWhiteTurn());

        int depth = 0;
        boolean outOfTime = false;
        
        /*
        Iterative Deepening Depth First Search:
        call search function at increasing depths, the data we get from lower depths is easily worth it
         */
        while (!outOfTime && depth < maxDepth){
            System.out.println("---- depth: " + depth + " ---- current best move: " + PrincipleVariationSearch.getAiMove() + " ----");
            System.out.println();
            
            int score = aspirationSearch(board, startTime, timeLimitMillis, zobristHash, depth, aspirationScore);

            /*
            stop search when a checkmate has been found, however far away
            */
            if (score >= CHECKMATE_ENEMY_SCORE_MAX_PLY){
                if (ALLOW_MATE_DISTANCE_PRUNING) {
                    int distanceToCheckmate = CHECKMATE_ENEMY_SCORE - score;
                    System.out.println("Checkmate found in " + distanceToCheckmate + " plies.");
                }
                else{
                    System.out.println("Checkmate found.");
                }
                break;
            }

            if (ALLOW_TIME_LIMIT) {
                long currentTime = System.currentTimeMillis();
                long timeLeft = startTime + timeLimitMillis - currentTime;
                if (timeLeft < 0) {
                    outOfTime = true;
                }
            }
            
            aspirationScore = score;
            depth++;
        }

        return PrincipleVariationSearch.getAiMove();
    }


}
