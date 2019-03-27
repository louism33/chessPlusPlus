package com.github.louism33.axolotl.transpositiontable;

import com.fluxchess.jcpi.commands.EngineAnalyzeCommand;
import com.fluxchess.jcpi.commands.EngineStartCalculatingCommand;
import com.fluxchess.jcpi.models.GenericBoard;
import com.fluxchess.jcpi.models.GenericMove;
import com.fluxchess.jcpi.models.IllegalNotationException;
import com.fluxchess.jcpi.protocols.NoProtocolException;
import com.github.louism33.axolotl.main.PVLine;
import com.github.louism33.axolotl.main.UCIEntry;
import com.github.louism33.axolotl.search.EngineBetter;
import com.github.louism33.axolotl.search.MoveOrdererBetter;
import com.github.louism33.chesscore.Chessboard;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.utils.MoveParserFromAN;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.louism33.axolotl.search.EngineBetter.resetBetweenMoves;
import static com.github.louism33.axolotl.search.EngineBetter.resetFull;
import static com.github.louism33.utils.MoveParserFromAN.*;

public class TranspositionTable3Test {


    @Test
    void depthTest() throws IllegalNotationException {
        UCIEntry uciEntry = new UCIEntry();
        EngineBetter.resetFull();
        EngineBetter.uciEntry = uciEntry;

        String mmm = "";
        String[] ms = mmm.split(" ");
        GenericBoard genericBoard = new GenericBoard(518);
        List<GenericMove> moves = new ArrayList<>();
//        for (String m : ms) {
//            moves.add(new GenericMove(m));
//        }

        EngineAnalyzeCommand eac = new EngineAnalyzeCommand(genericBoard, moves);

        EngineStartCalculatingCommand start = new EngineStartCalculatingCommand();
        start.setDepth(3);

        try {
            uciEntry.receive(eac);
            uciEntry.receive(start);
        } catch (NoProtocolException ignored) {
            int aiMove = EngineBetter.rootMoves[0];
            Chessboard board = uciEntry.board;
            List<GenericMove> genericMoves = PVLine.retrievePV(board);

            board.makeMoveAndFlipTurn(buildMoveFromLAN(board, genericMoves.get(0).toString()));
            
            board.makeMoveAndFlipTurn(buildMoveFromLAN(board, genericMoves.get(1).toString()));

            resetBetweenMoves();
            
            Assert.assertTrue(TranspositionTable.retrieveFromTable(board.zobristHash) != 0);
            resetFull();
            Assert.assertEquals(0, TranspositionTable.retrieveFromTable(board.zobristHash));

        } catch (Exception e) {
            throw new AssertionError("crashed on depth searched");
        }

        resetFull();
        System.out.println("persisting ok");
    }
}