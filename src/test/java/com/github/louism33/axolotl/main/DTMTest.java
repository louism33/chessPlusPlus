package com.github.louism33.axolotl.main;

import com.github.louism33.axolotl.search.Engine;
import com.github.louism33.axolotl.search.EngineSpecifications;
import com.github.louism33.axolotl.search.SearchSpecs;
import com.github.louism33.axolotl.util.ResettingUtils;
import com.github.louism33.chesscore.Chessboard;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.utils.MoveParserFromAN;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DTMTest {

    Engine engine = new Engine();

    @BeforeEach
    void setup() {
        ResettingUtils.reset();
    }


    @AfterEach
    void tearDown() {
        ResettingUtils.reset();
    }

    @Test
    void mateInOne() {
        Chessboard board = new Chessboard("3rk2r/1pR2p2/b2BpPp1/p2p4/8/1P6/P4PPP/4R1K1 w - - 1 0");
        board.makeMoveAndFlipTurn(MoveParserFromAN.buildMoveFromLAN(board, "e1e6"));
        board.makeMoveAndFlipTurn(MoveParserFromAN.buildMoveFromLAN(board, "f7e6"));
//        EngineSpecifications.PRINT_PV = true;
//        System.out.println(board);
        Engine.setThreads(1);
//        System.out.println(board);
//        MoveParser.printMove(board.generateLegalMoves());
        SearchSpecs.basicTimeSearch(10_000);
        final int move = engine.simpleSearch(board);
//        MoveParser.printMove(move);
        Assert.assertEquals("f6f7", MoveParser.toString(move));
    }
}
