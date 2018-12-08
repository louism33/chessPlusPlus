package com.github.louism33.axolotl.moveordering;

import com.github.louism33.chesscore.Chessboard;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.chesscore.Perft;
import org.junit.Assert;
import org.junit.Test;

import static com.github.louism33.axolotl.moveordering.MoveOrderer.getMoveScore;

public class MoveOrdererTest {

    @Test
    public void moveScore() {
        Chessboard board = new Chessboard();
        int[] ints = board.generateCleanLegalMoves();
        int move = ints[0];

        int max = 64;
        for (int s = 1; s < max; s++) {
            int moveScore = MoveOrderer.buildMoveScore(move, s);
            int scoreFromMove = getMoveScore(moveScore);

            Assert.assertEquals(MoveParser.toString(move), MoveParser.toString(moveScore));
            Assert.assertEquals(scoreFromMove, s);
        }
    }


    @Test
    public void positiveMoves() {
        Chessboard board = new Chessboard("r2r2k1/pp2bppp/2p1p3/4qb1P/8/1BP1BQ2/PP3PP1/2KR3R b - - 0 1");

        int[] moves = board.generateCleanLegalMoves();
        for (int m = 0; m < moves.length; m++){
            int move = moves[m];
            if (move == 0){
                break;
            }
            int max = 64;
            for (int s = 0; s < max; s++) {
                int moveScore = MoveOrderer.buildMoveScore(move, s);
                int scoreFromMove = getMoveScore(moveScore);

                Assert.assertTrue(moveScore > 0);
                Assert.assertEquals(MoveParser.toString(move), MoveParser.toString(moveScore));
                Assert.assertEquals(scoreFromMove, s);
            }
        }
    }
}