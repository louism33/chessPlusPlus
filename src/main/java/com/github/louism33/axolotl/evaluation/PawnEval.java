package com.github.louism33.axolotl.evaluation;

import com.github.louism33.chesscore.Chessboard;

import java.util.Arrays;

import static com.github.louism33.axolotl.evaluation.EvaluationConstants.*;
import static com.github.louism33.axolotl.evaluation.Evaluator.scoresForEPO;
import static com.github.louism33.axolotl.evaluation.PawnTranspositionTable.*;
import static com.github.louism33.axolotl.search.EngineSpecifications.PRINT_EVAL;
import static com.github.louism33.chesscore.BitOperations.*;
import static com.github.louism33.chesscore.BoardConstants.fileForward;
import static com.github.louism33.chesscore.BoardConstants.*;
import static com.github.louism33.chesscore.PieceMove.singlePawnCaptures;
import static java.lang.Long.numberOfTrailingZeros;

@SuppressWarnings("ALL")
public final class PawnEval {

//    static final int[] pawnScoresForDisplay = new int[2];
    static int pawnScore;
    static final long[] pawnMoveData = new long[16];

    static long[] calculatePawnData(Chessboard board, int percentOfStart) {
        Arrays.fill(pawnMoveData, 0);
        pawnScore = 0;

        final long allPieces = board.allPieces();

        for (int turn = WHITE; turn <= BLACK; turn++) {
            int myPawnScore = 0;
            final long ps = board.pieces[turn][PAWN];
            final long eps = board.pieces[1 - turn][PAWN];
            long myPassedPawns = 0;
            long allPawnCaptures = 0, allPawnSpans = 0, filesWithPawns = 0, pawnDoubleCaptures = 0;
            long allAdjacentSquares = 0;
            long pawns = ps;
            while (pawns != 0) {
                final long pawn = getFirstPiece(pawns);
                final int pawnIndex = numberOfTrailingZeros(pawn);

                allAdjacentSquares |= horizontalAdjacentSquares[pawnIndex];

                filesWithPawns |= FILES[pawnIndex % 8];

                long captureTable = singlePawnCaptures(pawn, turn, UNIVERSE);
                pawnDoubleCaptures |= (captureTable & allPawnCaptures);
                allPawnCaptures |= captureTable;

                final long myAttackSpan = pawnAttackSpans[turn][pawnIndex];
                allPawnSpans |= myAttackSpan;


                final int stopSquareIndex = pawnIndex + (8 - (16 * turn));
                final long stopSquare = newPieceOnSquare(stopSquareIndex);
                final long myBackupSquares = pawnAttackSpans[1 - turn][stopSquareIndex] | PAWN_CAPTURE_TABLE[1 - turn][stopSquareIndex];

                if ((adjacentFiles[pawnIndex] & ps) == 0) {
                    myPawnScore += pawnFeatures[PAWN_ISOLATED];
                }

                // either isolated or backwards, not both 
                // enemy threatens stop square
                else if ((PAWN_CAPTURE_TABLE[turn][stopSquareIndex] & eps) != 0) {
                    // no friendly pawns adjacent or on adj files behind
                    if ((myBackupSquares & ps) == 0) {
                        myPawnScore += pawnFeatures[PAWN_BACKWARDS];
                    }
                }

                // doesn't include being attacked by a pawn atm?
                final long myPromotionSpan = fileForward[turn][pawnIndex] | myAttackSpan;
                final long myPromotionEnemies = eps & myPromotionSpan; 
                if (myPromotionEnemies == 0) {
                    myPassedPawns |= pawn;
                }
                else if (populationCount(myBackupSquares & ps) > populationCount(myPromotionEnemies)) {
                    myPawnScore += pawnFeatures[PAWN_CANDIDATE];
                    
                }

                pawns &= pawns - 1;
            }

            // general move and capture data
            pawnMoveData[PUSHES + turn] = bulkPawnPseudoPushes(ps, turn, UNIVERSE, allPieces);
            pawnMoveData[SPANS + turn] = allPawnSpans;
            pawnMoveData[CAPTURES + turn] = allPawnCaptures;
            pawnMoveData[DOUBLE_CAPTURES + turn] = pawnDoubleCaptures;
            pawnMoveData[FILE_WITHOUT_MY_PAWNS + turn] = ~filesWithPawns;
            pawnMoveData[PASSED_PAWNS + turn] = myPassedPawns;

            boolean opposed, backwards;
            long stoppers, lever, leverPush, doubled, neighbours, phalanx, support;

            for (int f = 0; f < 8; f++) {
                final int fileAndPawns = populationCount(FILES[f] & ps);
                if (fileAndPawns > 1) {
                    myPawnScore += pawnFeatures[PAWN_DOUBLED];
                }
            }

            long protectedPawns = allPawnCaptures & ps;
            while (protectedPawns != 0) {
                final int i = turn == BLACK
                        ? (7 - (numberOfTrailingZeros(protectedPawns) / 8))
                        : numberOfTrailingZeros(protectedPawns) / 8;
                myPawnScore += i * pawnFeatures[PAWN_PROTECTED_BY_PAWNS];
                protectedPawns &= protectedPawns - 1;
            }

            long neighbourPawns = allAdjacentSquares & ps;
            
            while (neighbourPawns != 0) {
                final int i = turn == BLACK 
                        ? (7 - (numberOfTrailingZeros(neighbourPawns) / 8)) 
                        : numberOfTrailingZeros(neighbourPawns) / 8;
                myPawnScore += i * pawnFeatures[PAWN_NEIGHBOURS];
                neighbourPawns &= neighbourPawns - 1;
            }


            if (PRINT_EVAL) {
                scoresForEPO[turn][EvalPrintObject.pawnScore] += Score.getScore(myPawnScore, percentOfStart);
            }
            
            if (turn == WHITE) {
                pawnScore += myPawnScore;
            } else {
                pawnScore -= myPawnScore;
            } 
        }

        return pawnMoveData;
    }


    private static long bulkPawnPseudoPushes(long pawns, int turn, long legalPushes, long allPieces) {
        final long possiblePawnSinglePushes = turn == WHITE ? pawns << 8 : pawns >>> 8;
        final long intermediateRank = INTERMEDIATE_RANKS[turn];
        final long possibleDoubles = (((possiblePawnSinglePushes & intermediateRank & ~allPieces)));
        return (possiblePawnSinglePushes | (turn == WHITE ? possibleDoubles << 8 : possibleDoubles >>> 8));
    }
}
