package com.github.louism33.axolotl.search;

import com.github.louism33.axolotl.util.ResettingUtils;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.utils.ExtendedPositionDescriptionParser;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.louism33.axolotl.evaluation.EvaluationConstants.CHECKMATE_ENEMY_SCORE_MAX_PLY;
import static com.github.louism33.axolotl.search.Engine.aiMoveScore;
import static com.github.louism33.axolotl.search.EngineSpecifications.PRINT_PV;

public class FamousPositionsTest {

    Engine engine = new Engine();


    @BeforeAll
    public static void setup() {
        ResettingUtils.reset();
        PRINT_PV = false;
    }

    @AfterAll
    public static void after() {
        ResettingUtils.reset();
    }


    // todo, very tough!
    @Test
    void behtingTest() {
        String pos = "8/8/7p/3KNN1k/2p4p/8/3P2p1/8 w - - ; bm Kc6";
        ExtendedPositionDescriptionParser.EPDObject EPDObject =
                ExtendedPositionDescriptionParser.parseEDPPosition(pos);
//        System.out.println(EPDObject.getBoard());
//        Engine.setThreads(4);
        SearchSpecs.basicTimeSearch(1_000);
        final int move = engine.simpleSearch(EPDObject.getBoard());
//        MoveParser.printMove(move);
//        Assert.assertEquals(MoveParser.toString(move), "d5c6");
    }

    @Test
    void saavedraTest() {
        // amazing position requiring underpromotion to win
        String pos = "8/8/1KP5/3r4/8/8/8/k7 w - - ; bm c7";
        ExtendedPositionDescriptionParser.EPDObject EPDObject =
                ExtendedPositionDescriptionParser.parseEDPPosition(pos);
//        System.out.println(EPDObject.getBoard());
//        PRINT_PV = true;
        SearchSpecs.basicTimeSearch(5_000);
        final int move = engine.simpleSearch(EPDObject.getBoard());
        Assert.assertEquals(MoveParser.toString(move), "c6c7");
//        Assert.assertTrue(aiMoveScore >= CHECKMATE_ENEMY_SCORE_MAX_PLY);
    }

    @Test
    void retiTest() {
        System.out.println("testing reti position to see if engine finds the draw");
        String pos = "7K/8/k1P5/7p/8/8/8/8 w - -";
        ExtendedPositionDescriptionParser.EPDObject EPDObject =
                ExtendedPositionDescriptionParser.parseEDPPosition(pos);
        SearchSpecs.basicTimeSearch(1_000);
        final int move = engine.simpleSearch(EPDObject.getBoard());
        Assert.assertEquals(MoveParser.toString(move), "h8g7");
        Assert.assertEquals(aiMoveScore, 0);
    }
}
