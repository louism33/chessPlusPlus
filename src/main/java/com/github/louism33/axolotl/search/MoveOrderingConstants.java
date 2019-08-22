package com.github.louism33.axolotl.search;

import static com.github.louism33.chesscore.BoardConstants.*;

public final class MoveOrderingConstants {

    public static final int hashScore = 31;
    public static final int queenCapturePromotionScore = 30;
    public static final int queenQuietPromotionScore = 29;
    public static final int mateKillerScore = 28;
    public static final int neutralCapture = 24; // between 19 and 29
    public static final int captureBaseScore = 10; // between 18 and 29
    public static final int killerOneScore = 17;
    public static final int killerTwoScore = 16;
    public static final int pawnPushToSeven = 15;
    public static final int giveCheckMove = 15;
    public static final int castlingMove = 15;
    public static final int pawnPushToSix = 14;
    public static final int oldKillerScoreOne = 15;
    public static final int maxRootQuietScore = 14;
    public static final int oldKillerScoreTwo = 14;
    public static final int maxNodeQuietScore = 14;
    public static final int knightPromotionScore = 1;
    public static final int uninterestingMove = 0;
    public static final int uninterestingPromotion = 0;

    public static final int hashScoreNew = 31_000;
    public static final int queenCapturePromotionScoreNew = 30000;
    public static final int queenQuietPromotionScoreNew = 29000;
    public static final int neutralCaptureNew = 20024; 
    public static final int captureBaseScoreMVVLVA = 21010;
    public static final int evenCaptureScore = captureBaseScoreMVVLVA; 
    public static final int mateKillerScoreNew = 20018;
    public static final int killerOneScoreNew = 20017;
    public static final int killerTwoScoreNew = 20016;
    public static final int captureBaseScoreSEE = 18000;
    public static final int pawnPushToSevenNew = 15;
    public static final int giveCheckMoveNew = 15;
    public static final int castlingMoveNew = 15;
    public static final int pawnPushToSixNew = 14;
    public static final int oldKillerScoreOneNew = 15;
    public static final int maxRootQuietScoreNew = 14;
    public static final int oldKillerScoreTwoNew = 14;
    public static final int maxNodeQuietScoreNew = 14;
    public static final int knightPromotionScoreNew = 1;
    public static final int uninterestingMoveNew = 0;
    public static final int quietMoveBias = 0;
    public static final int uninterestingPromotionNew = 0;
    public static final int alreadySearchedScore = -1;
    public static final int dontSearchMeScore = -2;
    public static final int notALegalMoveScore = -3;

    static int[][] goodQuietDestinations = new int[2][64];

    //todo consider minusing the source from the score
    private static int[] goodQuietDestinationsWhite = {
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 2, 1,
            1, 2, 3, 4, 4, 3, 2, 1,
            1, 2, 4, 5, 5, 4, 2, 1,
            1, 2, 4, 5, 5, 4, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 1, 1, 2, 2, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1,
    };

    static int[] quietsILikeToMove = new int[7];

    static {
        quietsILikeToMove[PAWN] = 3;
        quietsILikeToMove[KNIGHT] = 4;
        quietsILikeToMove[BISHOP] = 4;
        quietsILikeToMove[ROOK] = 2;
        quietsILikeToMove[QUEEN] = 1;
        quietsILikeToMove[KING] = 0;

        goodQuietDestinations[WHITE] = goodQuietDestinationsWhite;

        for (int i = 0; i < 64; i++) {
            int index = (7 - i / 8) * 8 + (i & 7);
            goodQuietDestinations[BLACK][i] = goodQuietDestinationsWhite[index];
        }
    }

}
