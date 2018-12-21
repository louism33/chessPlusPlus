package com.github.louism33.axolotl.evaluation;

import com.github.louism33.chesscore.BitOperations;
import com.github.louism33.chesscore.Chessboard;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.chesscore.Piece;

import static com.github.louism33.axolotl.evaluation.EvaluationConstants.*;
import static com.github.louism33.chesscore.BitboardResources.*;

class Misc {

    static int evalMiscByTurn(Chessboard board, boolean white, int[] moves, long pinnedPieces, long myPieces,
                              boolean inCheck) {
        
        int score = 0;

        if (board.isWhiteTurn() == white){
            score += MY_TURN_BONUS;
        }

        score += BitOperations.populationCount(centreFourSquares & myPieces) * CENTRE_PIECE;

        while (pinnedPieces != 0){
            long pinnedPiece = BitOperations.getFirstPiece(pinnedPieces);
            int ordinal = Piece.pieceOnSquare(board, pinnedPiece).ordinal();
            int pinnedPenalty = ordinal > 6 ? PINNED_PIECES[ordinal - 6] : PINNED_PIECES[ordinal];

            score += pinnedPenalty;
            pinnedPieces &= pinnedPieces -1;
        }

        if (inCheck){
            score += IN_CHECK_PENALTY;
        }

        return score;
    }


}