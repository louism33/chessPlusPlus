package main.chess;

import main.miscAdmin.FenParser;
import main.moveGeneration.MoveGeneratorMaster;
import main.moveMaking.MoveOrganiser;
import main.moveMaking.MoveUnmaker;

import java.util.List;

public class Perft {
    // too many checks, captures

/*
20
400
8902
197 281
4 865 609
119 060 324
 */


    public static void main(String[] args) {
        new Perft();
    }

    Perft(){

//        Chessboard normalBoard = new Chessboard();
//        runPerftTestWithBoard(6, normalBoard);
        

        /*
      "depth":1,
      "nodes":8,
      "fen":"r6r/1b2k1bq/8/8/7B/8/8/R3K2R b QK - 3 2"
         */

//        Chessboard board1 = FenParser.makeBoardBasedOnFEN("r6r/1b2k1bq/8/8/7B/8/8/R3K2R b QK - 3 2");
//        runPerftTestWithBoard(1, board1);
//        plop(board1);
        
        
        
        
        /*
      "depth":1,
      "nodes":8,
      "fen":"8/8/8/2k5/2pP4/8/B7/4K3 b - d3 5 3"
         */
//        Chessboard board2 = FenParser.makeBoardBasedOnFEN("8/8/8/2k5/2pP4/8/B7/4K3 b - d3 5 3");
//        runPerftTestWithBoard(1, board2);
//        plop(board2);


        
        /*
       "depth":1,
      "nodes":19,
      "fen":"r1bqkbnr/pppppppp/n7/8/8/P7/1PPPPPPP/RNBQKBNR w QqKk - 2 2"
         */
//        runPerftTestWithBoard(1, FenParser.makeBoardBasedOnFEN("r1bqkbnr/pppppppp/n7/8/8/P7/1PPPPPPP/RNBQKBNR w QqKk - 2 2"));




        /*
        "depth":1,
      "nodes":5,
      "fen":"r3k2r/p1pp1pb1/bn2Qnp1/2qPN3/1p2P3/2N5/PPPBBPPP/R3K2R b QqKk - 3 2"
         */

//        Chessboard board4 = FenParser.makeBoardBasedOnFEN("r3k2r/p1pp1pb1/bn2Qnp1/2qPN3/1p2P3/2N5/PPPBBPPP/R3K2R b QqKk - 3 2");
//        runPerftTestWithBoard(1, board4);
//        plop(board4);
        
        
        
        
        /*
        "depth":1,
      "nodes":44,
      "fen":"2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b QK - 3 2"
         */

//        Chessboard board5 = FenParser.makeBoardBasedOnFEN("2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b QK - 3 2");
//        runPerftTestWithBoard(1, board5);
//        plop(board5);
        
        
        
        
        /*
        "depth":1,
      "nodes":39,
      "fen":"rnb2k1r/pp1Pbppp/2p5/q7/2B5/8/PPPQNnPP/RNB1K2R w QK - 3 9"
         */


//        Chessboard board6 = FenParser.makeBoardBasedOnFEN("rnb2k1r/pp1Pbppp/2p5/q7/2B5/8/PPPQNnPP/RNB1K2R w QK - 3 9");
//        runPerftTestWithBoard(1, board6);
//        plop(board6);

        
        /*
              "depth":1,
      "nodes":9,
      "fen":"2r5/3pk3/8/2P5/8/2K5/8/8 w - - 5 4"
         */


//        Chessboard board7 = FenParser.makeBoardBasedOnFEN("2r5/3pk3/8/2P5/8/2K5/8/8 w - - 5 4");
//        runPerftTestWithBoard(1, board7);
//        plop(board7);
        
        
        
        /*
              "depth":3,
      "nodes":62379,
      "fen":"rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8"
         */


        // TODO:
//        Chessboard board8 = FenParser.makeBoardBasedOnFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
//        runPerftTestWithBoard(3, board8);
//        plop(board8);




/*
"depth":3,
      "nodes":89890,
      "fen":"r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10"
 */

        // TODO
//        Chessboard board9 = FenParser.makeBoardBasedOnFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
//        runPerftTestWithBoard(3, board9);
//        plop(board9);
        
        
        
        
        
        
        /*
              "depth":6,
      "nodes":1134888,
      "fen":"3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1"
         */

        Chessboard board10 = FenParser.makeBoardBasedOnFEN("3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1");
        runPerftTestWithBoard(6, board10);
        plop(board10);

    }

    void plop(Chessboard board){

        List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
        System.out.println(moves);
        for (Move move : moves) {
            MoveOrganiser.makeMoveMaster(board, move);
            MoveOrganiser.flipTurn(board);
//            System.out.println(Art.boardArt(board));
            MoveUnmaker.unMakeMoveMaster(board);
        }


    }


    void runPerftTestWithBoard(int d, Chessboard board){

        String s = Art.boardArt(board);
        System.out.println(s);
        System.out.println("-----------------------------------");

        int maxD = d > 0 ? d : 5;


//        for (int depth = 1; depth <= maxD; depth++) {
//            countFinalNodesAtDepth(board, depth);
//            System.out.println();
////            countTotalNodesAtDepth(board, depth);
//            System.out.println("-----");
//            reset();
//        }


        reset();

        countFinalNodesAtDepth(board, maxD);


    }



    void reset(){
        MoveGeneratorMaster.numberOfChecks = 0;
        MoveGeneratorMaster.numberOfCheckMates = 0;
        MoveGeneratorMaster.numberOfStaleMates = 0;
        MoveOrganiser.epNum = 0;
        MoveOrganiser.captures = 0;
        MoveOrganiser.castlings = 0;
        MoveOrganiser.promotions = 0;
    }



//    public static void countTotalNodesAtDepth(Chessboard board, int depth){
//        long i = Perft.countMovesAtDepthHelper(board, depth) - 1;
//        System.out.println("Total Nodes at Depth " + depth + ": " + i);
//    }

    public static void countFinalNodesAtDepth(Chessboard board, int depth) {

        long t1 = System.currentTimeMillis();

        long ii = Perft.countFinalNodesAtDepthHelper(board, depth);
        System.out.println("Final Nodes at Depth " + depth + ": " + ii);
        System.out.println("--previous checks: " + MoveGeneratorMaster.numberOfChecks);
        System.out.println("--checkmates: " + MoveGeneratorMaster.numberOfCheckMates);
        System.out.println("--stalemates: " + MoveGeneratorMaster.numberOfStaleMates);
        System.out.println("--eps: " + MoveOrganiser.epNum);
        System.out.println("--previous captures: " + MoveOrganiser.captures);
        System.out.println("--previous castlings: " + MoveOrganiser.castlings);
        System.out.println("--previous promotions: " + MoveOrganiser.promotions);



        long t2 = System.currentTimeMillis();

        long t = t2 - t1;
        long seconds = t / 1000;

        System.out.println("Depth " + depth + " took " + seconds + " seconds (" + t+" milliseconds).");
        if (t > 0) {
            System.out.println("Veeeery roughly " + (ii / t) * 1000 + " (final) nodes per second.");
        }

    }



    private static long countFinalNodesAtDepthHelper(Chessboard board, int depth){
        long temp = 0;
        if (depth == 0){
            return 1;
        }
        List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
        if (depth == 1){
            return moves.size();
        }
        for (Move move : moves) {
            MoveOrganiser.makeMoveMaster(board, move);
            MoveOrganiser.flipTurn(board);

            System.out.println(Art.boardArt(board));
            
            
            long movesAtDepth = countFinalNodesAtDepthHelper(board, depth - 1);
            temp += movesAtDepth;
            MoveUnmaker.unMakeMoveMaster(board);
        }
        return temp;
    }

    private static long countFinalNodesAtDepthHelperOld(Chessboard board, int depth){
        long temp = 0;
        if (depth == 0){
            return 1;
        }
        List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
        if (depth == 1){
            return moves.size();
        }
        for (Move move : moves) {
            Chessboard babyBoard = Copier.copyBoard(board, board.isWhiteTurn(), false);
            MoveOrganiser.makeMoveMaster(babyBoard, move);
            MoveOrganiser.flipTurn(babyBoard);
            long movesAtDepth = countFinalNodesAtDepthHelper(babyBoard, depth - 1);
            temp += movesAtDepth;
        }
        return temp;
    }


//    private static long countMovesAtDepthHelper(Chessboard board, int depth){
//        long temp = 1;
//
//        if (depth == 0){
//            return 1;
//        }
//
//        List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
//
//        for (Move move : moves) {
//            Chessboard babyBoard = Copier.copyBoard(board, board.isWhiteTurn(), false);
//            MoveOrganiser.makeMoveMaster(babyBoard, move);
//
//            babyBoard.setWhiteTurn(!board.isWhiteTurn());
//
//            long movesAtDepth = countMovesAtDepthHelper(babyBoard, depth - 1);
//
//            temp += movesAtDepth;
//        }
//
//        return temp ;
//    }

    
    
    
    /*
    [
   {
      "depth":1,
      "nodes":8,
      "fen":"r6r/1b2k1bq/8/8/7B/8/8/R3K2R b QK - 3 2"
   },
   {
      "depth":1,
      "nodes":8,
      "fen":"8/8/8/2k5/2pP4/8/B7/4K3 b - d3 5 3"
   },
   {
      "depth":1,
      "nodes":19,
      "fen":"r1bqkbnr/pppppppp/n7/8/8/P7/1PPPPPPP/RNBQKBNR w QqKk - 2 2"
   },
   {
      "depth":1,
      "nodes":5,
      "fen":"r3k2r/p1pp1pb1/bn2Qnp1/2qPN3/1p2P3/2N5/PPPBBPPP/R3K2R b QqKk - 3 2"
   },
   {
      "depth":1,
      "nodes":44,
      "fen":"2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b QK - 3 2"
   },
   {
      "depth":1,
      "nodes":39,
      "fen":"rnb2k1r/pp1Pbppp/2p5/q7/2B5/8/PPPQNnPP/RNB1K2R w QK - 3 9"
   },
   {
      "depth":1,
      "nodes":9,
      "fen":"2r5/3pk3/8/2P5/8/2K5/8/8 w - - 5 4"
   },
   {
      "depth":3,
      "nodes":62379,
      "fen":"rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8"
   },
   {
      "depth":3,
      "nodes":89890,
      "fen":"r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10"
   },
   {
      "depth":6,
      "nodes":1134888,
      "fen":"3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1"
   },
   {
      "depth":6,
      "nodes":1015133,
      "fen":"8/8/4k3/8/2p5/8/B2P2K1/8 w - - 0 1"
   },
   {
      "depth":6,
      "nodes":1440467,
      "fen":"8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1"
   },
   {
      "depth":6,
      "nodes":661072,
      "fen":"5k2/8/8/8/8/8/8/4K2R w K - 0 1"
   },
   {
      "depth":6,
      "nodes":803711,
      "fen":"3k4/8/8/8/8/8/8/R3K3 w Q - 0 1"
   },
   {
      "depth":4,
      "nodes":1274206,
      "fen":"r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1"
   },
   {
      "depth":4,
      "nodes":1720476,
      "fen":"r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1"
   },
   {
      "depth":6,
      "nodes":3821001,
      "fen":"2K2r2/4P3/8/8/8/8/8/3k4 w - - 0 1"
   },
   {
      "depth":5,
      "nodes":1004658,
      "fen":"8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1"
   },
   {
      "depth":6,
      "nodes":217342,
      "fen":"4k3/1P6/8/8/8/8/K7/8 w - - 0 1"
   },
   {
      "depth":6,
      "nodes":92683,
      "fen":"8/P1k5/K7/8/8/8/8/8 w - - 0 1"
   },
   {
      "depth":6,
      "nodes":2217,
      "fen":"K1k5/8/P7/8/8/8/8/8 w - - 0 1"
   },
   {
      "depth":7,
      "nodes":567584,
      "fen":"8/k1P5/8/1K6/8/8/8/8 w - - 0 1"
   },
   {
      "depth":4,
      "nodes":23527,
      "fen":"8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1"
   }
]
     */
}
