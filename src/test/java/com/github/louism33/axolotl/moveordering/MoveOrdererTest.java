package com.github.louism33.axolotl.moveordering;

import com.github.louism33.axolotl.search.MoveOrdererBetter;
import com.github.louism33.axolotl.search.MoveOrderingConstants;
import com.github.louism33.chesscore.Chessboard;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.utils.MoveParserFromAN;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static com.github.louism33.axolotl.search.MoveOrdererBetter.buildMoveScore;
import static com.github.louism33.axolotl.search.MoveOrdererBetter.getMoveScore;
import static com.github.louism33.axolotl.transpositiontable.TranspositionTable.addToTableReplaceByDepth;
import static com.github.louism33.chesscore.BoardConstants.BLACK;
import static com.github.louism33.chesscore.MoveConstants.MOVE_MASK_WITHOUT_CHECK;

public class MoveOrdererTest {

    @Test
    public void moveOrdererRootTest() {
        Chessboard board = new Chessboard();

        

        int move = MoveParserFromAN.buildMoveFromLAN(board, "e7e3");

//        addToTableReplaceByDepth(board.zobristHash,
//                e2e4 & MOVE_MASK_WITHOUT_CHECK,
//                123, 1, 1, 1, 1);

        int[] moves = board.generateLegalMoves();

        MoveParser.printMove(moves);

        int l = moves[moves.length - 1];
        MoveOrdererBetter.scoreMovesAtRoot(moves, l, board);
        Ints.sortDescending(moves, 0, l);

        MoveParser.printMove(moves);
        
        for (int m = 0; m < l; m++) {
            int mm = moves[m];
            int moveScore = getMoveScore(mm);
            String s = MoveParser.toString(mm);
            System.out.println(s + " with score " + moveScore);
        }


        MoveParser.printMove(move);
        int i = MoveOrdererBetter.quietHeuristicMoveScore(move, BLACK, 20);

        
        System.out.println(i);

//        Assert.assertEquals(moves[0] & MOVE_MASK_WITHOUT_CHECK, e2e4);
    }

    
    
    @Test
    public void moveOrderer1RootTest() {
        Chessboard board = new Chessboard();


        int e2e4 = MoveParserFromAN.buildMoveFromLAN(board, "e2e4");
        
        addToTableReplaceByDepth(board.zobristHash,
                e2e4 & MOVE_MASK_WITHOUT_CHECK,
                123, 1, 1, 1, 1);

        int[] moves = board.generateLegalMoves();

        int l = moves[moves.length - 1];
        MoveOrdererBetter.scoreMovesAtRoot(moves, l, board);
        Ints.sortDescending(moves, 0, l);
        Assert.assertEquals(moves[0] & MOVE_MASK_WITHOUT_CHECK, e2e4);
    }

    @Test
    public void moveOrdererRoot2Test() {
        Chessboard board = new Chessboard();

        int m = MoveParserFromAN.buildMoveFromLAN(board, "a2a3");

        addToTableReplaceByDepth(board.zobristHash,
                m & MOVE_MASK_WITHOUT_CHECK,
                123, 1, 1, 1, 1);

        int[] moves = board.generateLegalMoves();

        int l = moves[moves.length - 1];
        MoveOrdererBetter.scoreMovesAtRoot(moves, l, board);
        Ints.sortDescending(moves, 0, l);
        Assert.assertEquals(moves[0] & MOVE_MASK_WITHOUT_CHECK, m);
    }

    @Test
    public void moveOrdererRoot3Test() {
        Chessboard board = new Chessboard();

        int m = MoveParserFromAN.buildMoveFromLAN(board, "g1f3");

        addToTableReplaceByDepth(board.zobristHash,
                m & MOVE_MASK_WITHOUT_CHECK,
                123, 1, 1, 1, 1);

        int[] moves = board.generateLegalMoves();

        int l = moves[moves.length - 1];
        MoveOrdererBetter.scoreMovesAtRoot(moves, l, board);
        Ints.sortDescending(moves, 0, l);
        Assert.assertEquals(moves[0] & MOVE_MASK_WITHOUT_CHECK, m);
    }


    @Test
    public void moveOrdererRoot4Test() {
        String fen = "8/P7/8/8/8/8/8/k6K";
        Chessboard board = new Chessboard(fen);

        int m = MoveParserFromAN.buildMoveFromLAN(board, "h1g1");

        addToTableReplaceByDepth(board.zobristHash,
                m & MOVE_MASK_WITHOUT_CHECK,
                123, 1, 1, 1, 1);

        int[] moves = board.generateLegalMoves();

        int l = moves[moves.length - 1];
        MoveOrdererBetter.scoreMovesAtRoot(moves, l, board);
        Ints.sortDescending(moves, 0, l);
        Assert.assertEquals(moves[0] & MOVE_MASK_WITHOUT_CHECK, m);

        int promQueenMove = MoveParserFromAN.buildMoveFromLAN(board, "a7a8Q");
        Assert.assertEquals(moves[1] & MOVE_MASK_WITHOUT_CHECK, promQueenMove);
    }
    
    @Test
    public void moveScoreTest() {
        Chessboard board = new Chessboard();
        int[] ints = board.generateLegalMoves();
        int move = ints[0];

        int max = MoveOrderingConstants.hashScore;
        for (int s = 1; s < max; s++) {
            int moveScore = buildMoveScore(move, s);
            int scoreFromMove = getMoveScore(moveScore);

            Assert.assertEquals(MoveParser.toString(move), MoveParser.toString(moveScore));
            Assert.assertEquals(scoreFromMove, s);
        }
    }


    @Test
    public void positiveMovesTest() {
        Chessboard board = new Chessboard("r2r2k1/pp2bppp/2p1p3/4qb1P/8/1BP1BQ2/PP3PP1/2KR3R b - - 0 1");

        int[] moves = board.generateLegalMoves();
        for (int m = 0; m < moves.length; m++){
            int move = moves[m];
            if (move == 0){
                break;
            }
            int max = MoveOrderingConstants.hashScore;
            for (int s = 1; s < max; s++) {
                int moveScore = buildMoveScore(move, s);
                int scoreFromMove = getMoveScore(moveScore);

                Assert.assertTrue(moveScore > 0);
                Assert.assertEquals(MoveParser.toString(move), MoveParser.toString(moveScore));
                Assert.assertEquals(scoreFromMove, s);
            }
        }
    }

}