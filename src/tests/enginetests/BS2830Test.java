package tests.enginetests;

import javacode.chessengine.Engine;
import javacode.chessprogram.chess.Move;
import javacode.chessprogram.miscAdmin.ExtendedPositionDescriptionParser;
import javacode.graphicsandui.Art;
import org.junit.jupiter.api.Test;

public class BS2830Test {

    @Test
    void bS2830TestTests() {

        int successes = 0;
        int failures = 0;

        String bS2830TestTests = "" +
                "4r1k1/p1pb1ppp/Qbp1r3/8/1P6/2Pq1B2/R2P1PPP/2B2RK1 b - - bm Qxf3; id \"BS2830-01\";\n" +
                "7r/2qpkp2/p3p3/6P1/1p2b2r/7P/PPP2QP1/R2N1RK1 b - - bm f5; id \"BS2830-02\";\n" +
                "r1bq1rk1/pp4bp/2np4/2p1p1p1/P1N1P3/1P1P1NP1/1BP1QPKP/1R3R2 b - - bm Bh3+; id \"BS2830-03\";\n" +
                "8/2kPR3/5q2/5N2/8/1p1P4/1p6/1K6 w - - bm Nd4; id \"BS2830-04\";\n" +
                "2r1r3/p3bk1p/1pnqpppB/3n4/3P2Q1/PB3N2/1P3PPP/3RR1K1 w - - bm Rxe6; id \"BS2830-05\";\n" +
                "8/2p5/7p/pP2k1pP/5pP1/8/1P2PPK1/8 w - - bm f3; id \"BS2830-06\";\n" +
                "8/5p1p/1p2pPk1/p1p1P3/P1P1K2b/4B3/1P5P/8 w - - bm b4; id \"BS2830-07\";\n" +
                "rn2r1k1/pp3ppp/8/1qNp4/3BnQb1/5N2/PPP2PPP/2KR3R b - - bm Bh5; id \"BS2830-08\";\n" +
                "r3kb1r/1p1b1p2/p1nppp2/7p/4PP2/qNN5/P1PQB1PP/R4R1K w kq - bm Nb1; id \"BS2830-09\";\n" +
                "r3r1k1/pp1bp2p/1n2q1P1/6b1/1B2B3/5Q2/5PPP/1R3RK1 w - - bm Bd2; id \"BS2830-10\";\n" +
                "r3k2r/pb3pp1/2p1qnnp/1pp1P3/Q1N4B/2PB1P2/P5PP/R4RK1 w kq - bm exf6; id \"BS2830-11\";\n" +
                "r1b1r1k1/ppp2ppp/2nb1q2/8/2B5/1P1Q1N2/P1PP1PPP/R1B2RK1 w - - bm Bb2; id \"BS2830-12\";\n" +
                "rnb1kb1r/1p3ppp/p5q1/4p3/3N4/4BB2/PPPQ1P1P/R3K2R w KQkq - bm O-O-O; id \"BS2830-13\";\n" +
                "r1bqr1k1/pp1n1ppp/5b2/4N1B1/3p3P/8/PPPQ1PP1/2K1RB1R w - - bm Nxf7; id \"BS2830-14\";\n" +
                "2r2rk1/1bpR1p2/1pq1pQp1/p3P2p/P1PR3P/5N2/2P2PPK/8 w - - bm Kg3; id \"BS2830-15\";\n" +
                "8/pR4pk/1b6/2p5/N1p5/8/PP1r2PP/6K1 b - - bm Rxb2; id \"BS2830-16\";\n" +
                "r1b1qrk1/ppBnppb1/2n4p/1NN1P1p1/3p4/8/PPP1BPPP/R2Q1R1K w - - bm Ne6; id \"BS2830-17\";\n" +
                "8/8/4b1p1/2Bp3p/5P1P/1pK1Pk2/8/8 b - - bm g5; id \"BS2830-18\";\n" +
                "r3k2r/pp1n1ppp/1qpnp3/3bN1PP/3P2Q1/2B1R3/PPP2P2/2KR1B2 w kq - bm Be1; id \"BS2830-19\";\n" +
                "r1bqk2r/pppp1Npp/8/2bnP3/8/6K1/PB4PP/RN1Q3R b kq - bm O-O; id \"BS2830-20\";\n" +
                "r4r1k/pbnq1ppp/np3b2/3p1N2/5B2/2N3PB/PP3P1P/R2QR1K1 w - - bm Ne4; id \"BS2830-21\";\n" +
                "r2qr2k/pbp3pp/1p2Bb2/2p5/2P2P2/3R2P1/PP2Q1NP/5RK1 b - - bm Qxd3; id \"BS2830-22\";\n" +
                "5r2/1p4r1/3kp1b1/1Pp1p2p/2PpP3/q2B1PP1/3Q2K1/1R5R b - - bm Rxf3; id \"BS2830-23\";\n" +
                "8/7p/8/7P/1p6/1p5P/1P2Q1pk/1K6 w - - bm Ka1; id \"BS2830-24\";\n" +
                "r5k1/p4n1p/6p1/2qPp3/2p1P1Q1/8/1rB3PP/R4R1K b - - bm Rf8; id \"BS2830-25\";\n" +
                "1r4k1/1q2pN1p/3pPnp1/8/2pQ4/P5PP/5P2/3R2K1 b - - bm Qd5; id \"BS2830-26\";\n" +
                "2rq1rk1/pb3ppp/1p2pn2/4N3/1b1PPB2/4R1P1/P4PBP/R2Q2K1 w - - bm d5; id \"BS2830-27\";" +
                "";

        String[] splitUpKaufmans = bS2830TestTests.split("\\\n");
        int totalKaufs = splitUpKaufmans.length;

        for (int bs = 0; bs < totalKaufs; bs++){

            System.out.println("Now checking BS test number "+ (bs + 1));

            ExtendedPositionDescriptionParser.EDPObject edpObject = ExtendedPositionDescriptionParser.parseEDPPosition(splitUpKaufmans[bs]);

            System.out.println("Its name is: "+ edpObject.getId());
            System.out.println("Board: ");
            System.out.println(Art.boardArt(edpObject.getBoard()));

            System.out.print("Our move to play... " );
            Move move = Engine.search(edpObject.getBoard(), 1000);
            System.out.println(move);

            int destination = move.destination;
//            Assert.assertEquals(destination, edpObject.getBestMoveDestinationIndex());

            if (destination == edpObject.getBestMoveDestinationIndex()){
                System.out.println("SUCCEEDED BS test number "+ (bs + 1));
                successes++;
            }
            else {
                System.out.println("FAILED BS test number "+ (bs + 1));
                failures++;
            }

        }

        System.out.println();
        System.out.println("-----");
        System.out.println("Total tests: " + splitUpKaufmans.length);
        System.out.println("Successes: "+ successes);
        System.out.println("Failures: " + failures);
    }
    
    
    /*
    4r1k1/p1pb1ppp/Qbp1r3/8/1P6/2Pq1B2/R2P1PPP/2B2RK1 b - - bm Qxf3; id "BS2830-01";
7r/2qpkp2/p3p3/6P1/1p2b2r/7P/PPP2QP1/R2N1RK1 b - - bm f5; id "BS2830-02";
r1bq1rk1/pp4bp/2np4/2p1p1p1/P1N1P3/1P1P1NP1/1BP1QPKP/1R3R2 b - - bm Bh3+; id "BS2830-03";
8/2kPR3/5q2/5N2/8/1p1P4/1p6/1K6 w - - bm Nd4; id "BS2830-04";
2r1r3/p3bk1p/1pnqpppB/3n4/3P2Q1/PB3N2/1P3PPP/3RR1K1 w - - bm Rxe6; id "BS2830-05";
8/2p5/7p/pP2k1pP/5pP1/8/1P2PPK1/8 w - - bm f3; id "BS2830-06";
8/5p1p/1p2pPk1/p1p1P3/P1P1K2b/4B3/1P5P/8 w - - bm b4; id "BS2830-07";
rn2r1k1/pp3ppp/8/1qNp4/3BnQb1/5N2/PPP2PPP/2KR3R b - - bm Bh5; id "BS2830-08";
r3kb1r/1p1b1p2/p1nppp2/7p/4PP2/qNN5/P1PQB1PP/R4R1K w kq - bm Nb1; id "BS2830-09";
r3r1k1/pp1bp2p/1n2q1P1/6b1/1B2B3/5Q2/5PPP/1R3RK1 w - - bm Bd2; id "BS2830-10";
r3k2r/pb3pp1/2p1qnnp/1pp1P3/Q1N4B/2PB1P2/P5PP/R4RK1 w kq - bm exf6; id "BS2830-11";
r1b1r1k1/ppp2ppp/2nb1q2/8/2B5/1P1Q1N2/P1PP1PPP/R1B2RK1 w - - bm Bb2; id "BS2830-12";
rnb1kb1r/1p3ppp/p5q1/4p3/3N4/4BB2/PPPQ1P1P/R3K2R w KQkq - bm O-O-O; id "BS2830-13";
r1bqr1k1/pp1n1ppp/5b2/4N1B1/3p3P/8/PPPQ1PP1/2K1RB1R w - - bm Nxf7; id "BS2830-14";
2r2rk1/1bpR1p2/1pq1pQp1/p3P2p/P1PR3P/5N2/2P2PPK/8 w - - bm Kg3; id "BS2830-15";
8/pR4pk/1b6/2p5/N1p5/8/PP1r2PP/6K1 b - - bm Rxb2; id "BS2830-16";
r1b1qrk1/ppBnppb1/2n4p/1NN1P1p1/3p4/8/PPP1BPPP/R2Q1R1K w - - bm Ne6; id "BS2830-17";
8/8/4b1p1/2Bp3p/5P1P/1pK1Pk2/8/8 b - - bm g5; id "BS2830-18";
r3k2r/pp1n1ppp/1qpnp3/3bN1PP/3P2Q1/2B1R3/PPP2P2/2KR1B2 w kq - bm Be1; id "BS2830-19";
r1bqk2r/pppp1Npp/8/2bnP3/8/6K1/PB4PP/RN1Q3R b kq - bm O-O; id "BS2830-20";
r4r1k/pbnq1ppp/np3b2/3p1N2/5B2/2N3PB/PP3P1P/R2QR1K1 w - - bm Ne4; id "BS2830-21";
r2qr2k/pbp3pp/1p2Bb2/2p5/2P2P2/3R2P1/PP2Q1NP/5RK1 b - - bm Qxd3; id "BS2830-22";
5r2/1p4r1/3kp1b1/1Pp1p2p/2PpP3/q2B1PP1/3Q2K1/1R5R b - - bm Rxf3; id "BS2830-23";
8/7p/8/7P/1p6/1p5P/1P2Q1pk/1K6 w - - bm Ka1; id "BS2830-24";
r5k1/p4n1p/6p1/2qPp3/2p1P1Q1/8/1rB3PP/R4R1K b - - bm Rf8; id "BS2830-25";
1r4k1/1q2pN1p/3pPnp1/8/2pQ4/P5PP/5P2/3R2K1 b - - bm Qd5; id "BS2830-26";
2rq1rk1/pb3ppp/1p2pn2/4N3/1b1PPB2/4R1P1/P4PBP/R2Q2K1 w - - bm d5; id "BS2830-27";
     */
}
