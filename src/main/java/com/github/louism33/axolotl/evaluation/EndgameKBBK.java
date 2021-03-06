package com.github.louism33.axolotl.evaluation;

import com.github.louism33.chesscore.Chessboard;
import org.junit.Assert;

import static com.github.louism33.axolotl.evaluation.EvaluationConstants.K;
import static com.github.louism33.axolotl.evaluation.EvaluationConstants.Q;
import static com.github.louism33.axolotl.evaluation.EvaluationConstants.*;
import static com.github.louism33.chesscore.BitOperations.*;
import static com.github.louism33.chesscore.BoardConstants.*;
import static com.github.louism33.chesscore.MaterialHashUtil.KBBK;
import static java.lang.Long.numberOfLeadingZeros;
import static java.lang.Long.numberOfTrailingZeros;

public final class EndgameKBBK {

    public static final int[] weakKingLocationKBBK = {
            21, 8, 18, 9, 9, 18, 8, 21,
            8, -3, 17, 2, 2, 17, -3, 8,
            18, 17, 2, 6, 6, 2, 17, 18,
            9, 2, 6, 0, 0, 6, 2, 9,
            9, 2, 6, 0, 0, 6, 2, 9,
            18, 17, 2, 6, 6, 2, 17, 18,
            8, -3, 17, 2, 2, 17, -3, 8,
            21, 8, 18, 9, 9, 18, 8, 21,
    };

    public static final int[] bishopLocations = {
            7, 0, 1, 1, 1, 1, 0, 7,
            0, -7, 5, 0, 0, 5, -7, 0,
            1, 5, 0, 0, 0, 0, 5, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1,
            1, 5, 0, 0, 0, 0, 5, 1,
            0, -7, 5, 0, 0, 5, -7, 0,
            7, 0, 1, 1, 1, 1, 0, 7,
    };


    public static int manFacBishopBishop = 0, chebFacBishopBishop = 1, centreFacBishopBishop = 2, bishopBishopNearEnemyKingMan = 3, bishopBishopNearEnemyKingCheb = 4, bishopNearBishop = 5;

    public static int[] bishopBishopNumbers = {
            -3, -11, 20, -5, -10, 1
    };

    public static int evaluateKBBK(Chessboard board) {
        int score = 0, winningPlayer = -1;

        Assert.assertEquals(KBBK, board.typeOfGameIAmIn);

        for (int turn = WHITE; turn <= BLACK; turn++) {
            long myBishops = board.pieces[turn][BISHOP];
            if (myBishops != 0) {
                score += 7_000;

                // included in order to stop losing pieces for no reason
                int materialScore = 0;
                materialScore += populationCount(board.pieces[turn][PAWN]) * material[P];
                materialScore += populationCount(board.pieces[turn][KNIGHT]) * material[K];
                materialScore += populationCount(board.pieces[turn][BISHOP]) * material[B];
                materialScore += populationCount(board.pieces[turn][ROOK]) * material[R];
                materialScore += populationCount(board.pieces[turn][QUEEN]) * material[Q];
                score += Score.getScore(materialScore, 0);

                winningPlayer = turn;
                long myKing = board.pieces[turn][KING];
                long enemyKing = board.pieces[1 - turn][KING];
                final int myKingIndex = numberOfTrailingZeros(myKing);
                final int enemyKingIndex = numberOfTrailingZeros(enemyKing);
                final int bi1 = numberOfTrailingZeros(myBishops);
                final int bi2 = numberOfLeadingZeros(myBishops);

                score += (bishopBishopNumbers[bishopNearBishop] * manhattanDistance(bi1, bi2) + bishopBishopNumbers[bishopNearBishop] * chebyshevDistance(bi1, bi2));

                score += (bishopBishopNumbers[manFacBishopBishop] * manhattanDistance(myKingIndex, enemyKingIndex) + bishopBishopNumbers[chebFacBishopBishop] * chebyshevDistance(myKingIndex, enemyKingIndex));

                score += bishopBishopNumbers[centreFacBishopBishop] * weakKingLocationKBBK[enemyKingIndex];

                while (myBishops != 0) {

                    int b = numberOfTrailingZeros(myBishops);

                    score += bishopLocations[b];

                    score += (bishopBishopNumbers[bishopBishopNearEnemyKingMan] * manhattanDistance(b, enemyKingIndex) + bishopBishopNumbers[bishopBishopNearEnemyKingCheb] * chebyshevDistance(b, enemyKingIndex));

                    myBishops &= myBishops - 1;
                }
            }
        }

        Assert.assertTrue(winningPlayer != -1);

        Assert.assertTrue(Math.abs(score) < CHECKMATE_ENEMY_SCORE_MAX_PLY);
return board.turn == winningPlayer ? score : -score;
    }


}
