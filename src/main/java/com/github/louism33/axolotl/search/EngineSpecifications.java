package com.github.louism33.axolotl.search;

import com.github.louism33.chesscore.Chessboard;

public final class EngineSpecifications {

    public static boolean MASTER_DEBUG = false;

    public static boolean DEBUG = false;
    public static boolean SPSA = false;
    public static boolean sendBestMove = true;
    public static boolean PRINT_PV = true;
    public static boolean PRINT_EVAL = false;
    
    public static int NUMBER_OF_THREADS = 1;
    public static final int DEFAULT_THREAD_NUMBER = 1;
    public static final int MAX_THREADS = 8;
    public final static int ABSOLUTE_MAX_DEPTH = Chessboard.MAX_DEPTH_AND_ARRAY_LENGTH - 2;
    public final static int MAX_DEPTH_HARD = Chessboard.MAX_DEPTH_AND_ARRAY_LENGTH - 2;

    /*
    one mb is 1024 KB
    1024 * 1024 Bytes / 64
    
    
    
    128 mb = 128,000,000 bytes
    one long = 8 bytes
    128 mb = 128,000,000 / 8 longs = 16,000,000 longs
     */
    public static int DEFAULT_TABLE_SIZE_MB = 128;
    public static final int MIN_TABLE_SIZE_MB = 1;
    public static final int MAX_TABLE_SIZE_MB = 1024;

    public static final int TABLE_SIZE_PER_MB = 1_000_000 / 8; // bytes in mb div by bytes in long

    public static int TABLE_SIZE_MB = DEFAULT_TABLE_SIZE_MB;
    public static int TABLE_SIZE = DEFAULT_TABLE_SIZE_MB * TABLE_SIZE_PER_MB;
    public static final int MIN_TABLE_SIZE = MIN_TABLE_SIZE_MB * TABLE_SIZE_PER_MB;
    public static final int MAX_TABLE_SIZE = MAX_TABLE_SIZE_MB * TABLE_SIZE_PER_MB;
}
