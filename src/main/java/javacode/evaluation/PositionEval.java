package javacode.evaluation;

import javacode.chessprogram.chess.BitIndexing;
import javacode.chessprogram.chess.Chessboard;

import java.util.List;

class PositionEval {

    static int evalPositionByTurn(Chessboard board, boolean white){
        long pawns, knights, bishops, rooks, queens, kings;
        if (white){
            pawns = board.WHITE_PAWNS;
            knights = board.WHITE_KNIGHTS;
            bishops = board.WHITE_BISHOPS;
            rooks = board.WHITE_ROOKS;
            queens = board.WHITE_QUEEN;
            kings = board.WHITE_KING;
        }
        else {
            pawns = board.BLACK_PAWNS;
            knights = board.BLACK_KNIGHTS;
            bishops = board.BLACK_BISHOPS;
            rooks = board.BLACK_ROOKS;
            queens = board.BLACK_QUEEN;
            kings = board.BLACK_KING;
        }

        return piecePositionScores(pawns, white, pawnpos)
                + piecePositionScores(knights, white, knightpos)
                + piecePositionScores(bishops, white, bishoppos)
                + piecePositionScores(rooks, white, rookpos)
                + piecePositionScores(queens, white, queenpos)
//                + piecePositionScores(kings, white, kingposstart)
                ;
    }

    private static int piecePositionScores(long pieces, boolean white, int[] whichArray){
        List<Integer> indexOfAllPieces = BitIndexing.getIndexOfAllPieces(pieces);
        int answer = 0;
        for (Integer piece : indexOfAllPieces){
            if (white){
                piece = 63 - piece;
            }
            answer += whichArray[piece];
        }
        return answer;
    }

    private static final int[] pawnpos = {
              0,    0,  0,  0,  0,  0,  0,  0,
            164,  156,174,218,218,174,156,164,
             12,   26, 66, 58, 58, 66, 26, 12,
            -16,   -4, -2, 14, 14, -2, -4,-16,
            -32,  -28,-12,  2,  2,-12,-28,-32,
            -30,  -20,-16,-16,-16,-16,-20,-30,
            -24,    4,-16,-10,-10,-16,  4,-24,
              0,    0,  0,  0,  0,  0,  0,  0,
    };

    private static final int[] knightpos = {
            -214,-112,-130,-34,-34,-130,-112,-214,
             -80, -62,   2,-34,-34,   2, -62, -80,
             -24,  44,  18, 36, 36,  18,  44, -24,
              18,  42,  38, 50, 50,  38,  42,  18,
               8,  36,  36, 36, 36,  36,  36,   8,
               8,  38,  34, 40, 40,  34,  38,   8,
               0,   0,  24, 36, 36,  24,   0,   0,
             -30,   6,  -4, 20, 20,  -4,   6, -30,
    };

    private static final int[] bishoppos = {
            -18, 12,-92,-84,-84,-92, 12,-18,
            -44, -8,  0,-18,-18,  0, -8,-44,
             40, 48, 42, 34, 34, 42, 48, 40,
             28, 34, 40, 58, 58, 40, 34, 28,
             28, 34, 36, 62, 62, 36, 34, 28,
             36, 54, 50, 40, 40, 50, 54, 36,
             36, 60, 48, 42, 42, 48, 60, 36,
             8,  32, 30, 48, 48, 30, 32,  8,
    };

    private static final int[] rookpos =   {
            -36,-26,-72, -4, -4,-72,-26,-36,
            -36,-20, 14, 22, 22, 14,-20,-36,
            -28,  2, -2, -8, -8, -2,  2,-28,
            -40,-22, 10,  6,  6, 10,-22,-40,
            -44,-14,-22,  2,  2,-22,-14,-44,
            -38,-12, -2, -4, -4, -2,-12,-38,
            -48, -2, -6, 10, 10, -6, -2,-48,
            -10,-12,  0, 14, 14,  0,-12,-10,
    };

    private static final int[] queenpos =   {
            -72,-44,-78,-66,-66,-78,-44,-72,
            -38,-88,-70,-82,-82,-70,-88,-38,
             -8,-38,-44,-64,-64,-44,-38, -8,
            -38,-44,-46,-60,-60,-46,-44,-38,
            -24,-34,-22,-30,-30,-22,-34,-24,
             -8, 10,-14,-10,-10,-14, 10, -8,
             -4, 12, 26, 20, 20, 26, 12, -4,
             14,  6, 12, 24, 24, 12,  6, 14,
    };

    private static final int[] kingposstart =   {
            -50,180,-26,  2,  2,-26,180,-50,
             48,-10,-64,-24,-24,-64,-10, 48,
             42, 52, 44,-44,-44, 44, 52, 42,
            -40,-22,-46,-92,-92,-46,-22,-40,
            -40,-20,-20,-76,-76,-20,-20,-40,
             14, 16,  2,-10,-10,  2, 16, 14,
             30, 10,-40,-56,-56,-40, 10, 30,
             34, 44,  8, 18, 18,  8, 44, 34,
    };

    private static int kingposend[] =   {
            -90,-90, -2,-56,-56, -2,-90,-90,
            -34, 14, 40, 24, 24, 40, 14,-34,
             -8, 32, 36, 38, 38, 36, 32, -8,
              0, 38, 44, 50, 50, 44, 38,  0,
            -12, 14, 28, 44, 44, 28, 14,-12,
            -18,  8, 18, 24, 24, 18,  8,-18,
            -40, -4, 16, 24, 24, 16, -4,-40,
            -74,-44,-24,-34,-34,-24,-44,-74,
    };
}