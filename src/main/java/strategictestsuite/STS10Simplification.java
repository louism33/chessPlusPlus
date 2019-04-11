package strategictestsuite;

import com.github.louism33.axolotl.search.EngineBetter;
import com.github.louism33.axolotl.search.EngineSpecifications;
import com.github.louism33.chesscore.MoveParser;
import com.github.louism33.utils.ExtendedPositionDescriptionParser;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static challenges.Utils.contains;
import static com.github.louism33.utils.ExtendedPositionDescriptionParser.parseEDPPosition;


@RunWith(Parameterized.class)
public class STS10Simplification {


    private static final int timeLimit = 10_000;
    private static int successes = 0;

    @AfterClass
    public static void finalSuccessTally(){
        System.out.println("Successes: " + successes + " out of " + splitUpPositions.length);
    }
    @Parameterized.Parameters(name = "{index} Test: {1}")
    public static Collection<Object[]> data() {
        List<Object[]> answers = new ArrayList<>();

        EngineSpecifications.DEBUG = true;

        for (int i = 0; i < splitUpPositions.length; i++) {

            String splitUpWAC = splitUpPositions[i];
            Object[] objectAndName = new Object[2];
            ExtendedPositionDescriptionParser.EPDObject EPDObject = parseEDPPosition(splitUpWAC);
            objectAndName[0] = EPDObject;
            objectAndName[1] = EPDObject.getId();
            answers.add(objectAndName);
        }
        return answers;
    }

    private static ExtendedPositionDescriptionParser.EPDObject EPDObject;

    public STS10Simplification(Object edp, Object name) {
        EPDObject = (ExtendedPositionDescriptionParser.EPDObject) edp;
    }

    @Test
    public void test() {
        System.out.println(EPDObject.getFullString());
        System.out.println(EPDObject.getBoard());
        int[] winningMoves = EPDObject.getBestMovesFromComments();
        int[] losingMoves = EPDObject.getAvoidMoves();
        EngineSpecifications.DEBUG = false;
        int move = EngineBetter.searchFixedTime(EPDObject.getBoard(), timeLimit);

        System.out.println("my move: " + MoveParser.toString(move));

        final boolean condition = contains(winningMoves, move) && !contains(losingMoves, move);
        if (condition) {
            successes++;
        }
        Assert.assertTrue(condition);
    }

    private static final String positions = "" +
            "1b1qrr2/1p4pk/1np4p/p3Np1B/Pn1P4/R1N3B1/1Pb2PPP/2Q1R1K1 b - - bm Bxe5; id \"STS(v10.0) Simplification.001\"; c0 \"Bxe5=10, f4=3, Nc4=2\";\n" +
            "1k1r2r1/1b4p1/p4n1p/1pq1pPn1/2p1P3/P1N2N2/1PB1Q1PP/3R1R1K b - - bm Nxf3; id \"STS(v10.0) Simplification.002\"; c0 \"Nxf3=10, Rge8=7, Rgf8=6, Rh8=7\";\n" +
            "1k1r3r/pb1q2p1/B4p2/2p4p/Pp1bPPn1/7P/1P2Q1P1/R1BN1R1K b - - bm Bxa6; id \"STS(v10.0) Simplification.003\"; c0 \"Bxa6=10, Qc6=3, Qe6=3, Rde8=5\";\n" +
            "1k1r4/1br2p2/3p1p2/pp2pPb1/2q1P2p/P1PQNB1P/1P4P1/1K1RR3 b - - bm Qxd3+; id \"STS(v10.0) Simplification.004\"; c0 \"Qxd3+=10, Qa4=6, Qb3=5, Qc5=6\";\n" +
            "1k1r4/1br2p2/3p1p2/pp2pPb1/4P2p/P1PRNB1P/1P4P1/1K2R3 b - - bm Bxe3; id \"STS(v10.0) Simplification.005\"; c0 \"Bxe3=10, b4=7, Ka7=6, Rc5=6\";\n" +
            "1k1r4/5qp1/p1b2n1p/1p2p3/2p1P2P/P1N1P1P1/1PB1Q3/3R2K1 b - - bm Rxd1+; id \"STS(v10.0) Simplification.006\"; c0 \"Rxd1+=10, Qc7=4, Qe7=4, Qf8=4\";\n" +
            "1k1rr3/p1p4p/Bpnb4/3p1qp1/N2P4/P2Q1PPb/1PP3NP/2KR2R1 w - - bm Qxf5; id \"STS(v10.0) Simplification.007\"; c0 \"Qxf5=10, Bb5=3, Nc3=5, Qd2=1\";\n" +
            "1kr5/3n2p1/q2rpn1p/p7/Ppp2P2/5BP1/1PQ2B1P/2RR2K1 w - - bm Rxd6; id \"STS(v10.0) Simplification.008\"; c0 \"Rxd6=10, Kh1=4, Qe2=7, Rd4=4\";\n" +
            "1nr2qk1/pb1r1p1p/1b4pP/1p1Bp1B1/4P1Q1/2NR4/P1R2PP1/6K1 w - - bm Bxb7; id \"STS(v10.0) Simplification.009\"; c0 \"Bxb7=10, Rdd2=1\";\n" +
            "1qr3k1/p3bppp/4pn2/1B2n3/N7/P3P2P/1P2QPP1/2R1B2K w - - bm Rxc8+; id \"STS(v10.0) Simplification.010\"; c0 \"Rxc8+=10, Nc3=6, Rc3=6, Rd1=6\";\n" +
            "1r1q1n2/2p2ppk/p2p3p/P1b1p3/2P1P3/3P1N1P/1R1B1PP1/1Q4K1 b - - bm Rxb2; id \"STS(v10.0) Simplification.011\"; c0 \"Rxb2=10, Nd7=3, Ra8=2, Rc8=2\";\n" +
            "1r1qr1k1/2Q1bp1p/2n3p1/2PN4/4B3/2N3P1/5P1P/5RK1 b - - bm Qxc7; id \"STS(v10.0) Simplification.012\"; c0 \"Qxc7=10, Nb4=2, Rc8=1\";\n" +
            "1r1r2k1/6pp/1b2p2q/3b1p2/1BB1n3/P3PN1P/2Q2PP1/2R2RK1 w - - bm Bxd5; id \"STS(v10.0) Simplification.013\"; c0 \"Bxd5=10, Nd4=3, Qb3=3, Rfe1=2\";\n" +
            "1r1r3k/1qpn1pbp/p1n3pB/P2bp3/BpN3Q1/3P3P/1PPN1PP1/R3R1K1 w - - bm Bxg7+; id \"STS(v10.0) Simplification.014\"; c0 \"Bxg7+=10, Qg5=1, Qh4=4\";\n" +
            "1r2k2r/pp1npp2/2n3p1/7p/3bP3/P3BB1P/1P3PP1/1R1R1NK1 b k - bm Bxe3; id \"STS(v10.0) Simplification.015\"; c0 \"Bxe3=10, Bb6=6, Bc5=6, e5=6\";\n" +
            "1r3r2/3q2kp/p2p1pp1/2pPp3/4Pn2/P2P1N1P/2Q2PP1/1R2R1K1 w - - bm Rxb8; id \"STS(v10.0) Simplification.016\"; c0 \"Rxb8=10, Kh1=5, Kh2=5, Nd2=5\";\n" +
            "1r3rk1/pp3pp1/1np4p/2Nn2bq/3P3N/P2Q2PP/1P1BRP2/4R1K1 w - - bm Bxg5; id \"STS(v10.0) Simplification.017\"; c0 \"Bxg5=10, Qf3=2, Re4=2, Re5=2\";\n" +
            "1r4k1/1q2r2p/1n4pQ/2pp1pN1/7P/P5P1/1P3P2/3RR1K1 w - - bm Rxe7; id \"STS(v10.0) Simplification.018\"; c0 \"Rxe7=10, h5=4, Nf3=4, Nxh7=3\";\n" +
            "1r4k1/5ppp/1p3n2/5n2/NP1r4/1B3P2/1P4PP/3RR2K w - - bm Rxd4; id \"STS(v10.0) Simplification.019\"; c0 \"Rxd4=10, b5=1, Bc2=2, Nc3=2\";\n" +
            "1r4k1/p1rn1ppp/2p1p1b1/N1PpP3/3P1P2/1R4P1/P6P/R4BK1 w - - bm Rxb8+; id \"STS(v10.0) Simplification.020\"; c0 \"Rxb8+=10, Ba6=3, Rc1=3, Re1=3\";\n" +
            "1r4r1/4kp1p/3p1p1b/2q1pP2/Bn2P3/2N5/1PPNQ1PP/1K1R4 b - - bm Bxd2; id \"STS(v10.0) Simplification.021\"; c0 \"Bxd2=10, Rgd8=1\";\n" +
            "1r4rk/2q1b2p/pN6/P1p1Pp2/3n1p2/5N2/5QPP/R4RK1 b - - bm Nxf3+; id \"STS(v10.0) Simplification.022\"; c0 \"Nxf3+=10, Qb7=5, Rbd8=7, Rgd8=5\";\n" +
            "1r6/1R2bk2/P3p1pp/4Pp2/1r1P3P/1N2B1P1/5PK1/8 w - - bm Rxb4; id \"STS(v10.0) Simplification.023\"; c0 \"Rxb4=10, Rxb8=6\";\n" +
            "1r6/3n4/p2b3p/2nN1kpP/1BP1p3/3rP3/3NKP2/1R1R4 w - - bm Bxc5; id \"STS(v10.0) Simplification.024\"; c0 \"Bxc5=10, Bc3=3, Nxe4=2, Rf1=2\";\n" +
            "1r6/8/p2b3p/2nN1kpP/2P1p3/3rP3/3NKP2/1R1R4 w - - bm Rxb8; id \"STS(v10.0) Simplification.025\"; c0 \"Rxb8=10, Ne7+=6\";\n" +
            "1rb2nk1/8/1p1PNbrp/3Pp1pq/p3Pn1P/P2N1PB1/4B3/R2Q1R1K w - - bm Nxf8; id \"STS(v10.0) Simplification.026\"; c0 \"Nxf8=10, Nc7=4, Rc1=1\";\n" +
            "2br1rk1/2q3p1/1pp1p2p/p1n1b3/2BNN1Q1/4P3/PP3PP1/2RR3K w - - bm Nxc5; id \"STS(v10.0) Simplification.027\"; c0 \"Nxc5=10, Kg1=2, Nf3=2, Nxe6=3\";\n" +
            "2br2k1/r5p1/4p3/5p2/8/pP3B2/1q2RPPP/Q3R1K1 b - - bm Qxa1; id \"STS(v10.0) Simplification.028\"; c0 \"Qxa1=10, Qd4=1, Qxb3=1, Rd2=3\";\n" +
            "2brr1k1/2q1bpp1/pNnpp2p/P1p5/1P2PP2/2P1B1P1/4Q1BP/R2R2K1 w - - bm Nxc8; id \"STS(v10.0) Simplification.029\"; c0 \"Nxc8=10, b5=3, bxc5=4, Rab1=3\";\n" +
            "2r1n1k1/5ppp/1prp4/p3p1q1/2b1P3/P1B1QB1P/1PPR1PPK/R7 b - - bm Qxe3; id \"STS(v10.0) Simplification.030\"; c0 \"Qxe3=10, h6=2, Qe7=2, Qh4=2\";\n" +
            "2r1r1k1/1p1q1bbp/pn3pp1/n2p4/3P1B1P/2NB1N2/PPQ1RPP1/4R1K1 w - - bm Rxe8+; id \"STS(v10.0) Simplification.031\"; c0 \"Rxe8+=10, h5=8, Qb1=5\";\n" +
            "2r1r1k1/2qbppbp/pp1p2p1/n2P4/P1P4P/1P3N2/1BQ1BPP1/R2R2K1 w - - bm Bxg7; id \"STS(v10.0) Simplification.032\"; c0 \"Bxg7=10, Bd4=3, h5=3, Ng5=2\";\n" +
            "2r2nk1/pp3p2/5qpb/4p1Np/P3P2P/1P2B1P1/2r2P2/2RRQ1K1 w - - bm Rxc2; id \"STS(v10.0) Simplification.033\"; c0 \"Rxc2=10, Kg2=2\";\n" +
            "2r2r1k/1p1q2b1/pQn1b1pp/4pp2/B3P3/1P3N1P/P1RB1PP1/R5K1 w - - bm Bxc6; id \"STS(v10.0) Simplification.034\"; c0 \"Bxc6=10, Bb4=4, Be3=2, Rac1=1\";\n" +
            "2r3k1/4qpp1/4p2p/1P1bP3/4pPP1/4Q3/4B2P/2R3K1 b - - bm Rxc1+; id \"STS(v10.0) Simplification.035\"; c0 \"Rxc1+=10\";\n" +
            "2r3k1/pp3pp1/4q2p/4p3/bB2Pn2/P4P2/1P1Q2PP/2R2BK1 w - - bm Rxc8+; id \"STS(v10.0) Simplification.036\"; c0 \"Rxc8+=10, Bc5=4, Qe3=2, Rc3=4\";\n" +
            "2r5/4q1kp/1nB2pp1/p3p3/Pb2Q3/4P3/1B1r1PPP/1R1R2K1 b - - bm Rxd1+; id \"STS(v10.0) Simplification.037\"; c0 \"Rxd1+=10, Rcd8=2\";\n" +
            "2rk1br1/4np1p/p2pb3/1p1Npp2/P3P3/2PB2Pq/1PN2P1P/R2Q1R1K w - - bm Nxe7; id \"STS(v10.0) Simplification.038\"; c0 \"Nxe7=10, Nb6=4, Ncb4=6, Ndb4=3\";\n" +
            "2rnq2k/pb3pp1/1p5p/3B4/1P3Q2/P4N1P/2R2PP1/6K1 w - - bm Rxc8; id \"STS(v10.0) Simplification.039\"; c0 \"Rxc8=10, Bb3=3, Rc4=5, Rd2=1\";\n" +
            "2rq1r1k/3n2pp/1p2p3/1P1b2bn/p1BP4/P4NP1/1B1NQP2/2R1R1K1 w - - bm Bxd5; id \"STS(v10.0) Simplification.040\"; c0 \"Bxd5=10, Bd3=1, Nxg5=2, Rc3=2\";\n" +
            "2rq1rk1/1p2bpp1/4p3/pP1pP1np/P2P4/R3BP2/3Q2PP/1NR3K1 w - - bm Rxc8; id \"STS(v10.0) Simplification.041\"; c0 \"Rxc8=10, Ra2=1, Rac3=3, Rb3=1\";\n" +
            "2rr1bk1/3qnppp/pp6/3p4/2PP2Q1/PP5P/1B1N2P1/2R2R1K b - - bm Qxg4; id \"STS(v10.0) Simplification.042\"; c0 \"Qxg4=10, dxc4=2, f6=1, g6=2\";\n" +
            "2rr2k1/p4pp1/1p2pn1p/3b1P2/1qNP4/1P1B4/P2Q2PP/2RR2K1 b - - bm Qxd2; id \"STS(v10.0) Simplification.043\"; c0 \"Qxd2=10, a5=3, Qe7=2, Qf8=2\";\n" +
            "3b4/2q2pkp/p2p1np1/1b1Pp1B1/1pN1P3/1P5P/P1Q2PP1/5NK1 b - - bm Bxc4; id \"STS(v10.0) Simplification.044\"; c0 \"Bxc4=10, a5=1, Be7=1, h6=2\";\n" +
            "3n1r2/2r4p/p2bp2k/1p1p2pP/1P1P4/P1R1B1Pq/4QP2/1B2R1K1 w - - bm Rxc7; id \"STS(v10.0) Simplification.045\"; c0 \"Rxc7=10, Rec1=2\";\n" +
            "3q4/pp2b1k1/2r5/4p2p/P1RpN3/1P3PP1/1Q2P1K1/8 w - - bm Rxc6; id \"STS(v10.0) Simplification.046\"; c0 \"Rxc6=10, Nd2=3, Qb1=2, Qc2=4\";\n" +
            "3r1k2/p4ppp/1r2p3/n1p1P3/5P1P/P1PBK1P1/8/1R3R2 w - - bm Rxb6; id \"STS(v10.0) Simplification.047\"; c0 \"Rxb6=10, a4=5, h5=8, Rfd1=4\";\n" +
            "3r1q1k/Q2R1bpp/3b1r2/2p2p2/2N2P2/1Pp3P1/5PBP/4R1K1 w - - bm Rxd8; id \"STS(v10.0) Simplification.048\"; c0 \"Rxd8=10, h4=5, Nxd6=5, Rc1=6\";\n" +
            "3r1rk1/4b1p1/p3p3/4pbPp/2R4P/1PN5/PB3P2/3R2K1 w - - bm Rxd8; id \"STS(v10.0) Simplification.049\"; c0 \"Rxd8=10, Rc7=2\";\n" +
            "3r2k1/1b1rnp1p/p2qpbp1/1pR5/1P1PB2P/P3BN2/4QPP1/3R2K1 w - - bm Bxb7; id \"STS(v10.0) Simplification.050\"; c0 \"Bxb7=10, Bg5=7\";\n" +
            "3r4/5pk1/4rqp1/p2p4/Pb1Q3p/1Pp1P2P/2R1BPP1/3R2K1 b - - bm Qxd4; id \"STS(v10.0) Simplification.051\"; c0 \"Qxd4=10, g5=6, Re4=5, Red6=5\";\n" +
            "3rkb2/1p4rp/p4p2/2p2B2/2N1Pp2/3R2P1/PPP2P1P/6K1 b - - bm Rxd3; id \"STS(v10.0) Simplification.052\"; c0 \"Rxd3=10, Be7=2, fxg3=2, Rd4=3\";\n" +
            "3rr1k1/6p1/pb1p3p/1p1RnBqP/4PQN1/2P5/PP6/1K3R2 w - - bm Qxg5; id \"STS(v10.0) Simplification.053\"; c0 \"Qxg5=10, a3=3, Kc2=3, Nxe5=4\";\n" +
            "4qbk1/r1p1nbpp/1p3p2/1Q6/P2P4/2BN1BP1/5P1P/2R3K1 w - - bm Qxe8; id \"STS(v10.0) Simplification.054\"; c0 \"Qxe8=10, Bb2=5, h3=5, Nf4=5, Nf4=6\";\n" +
            "4r1k1/1p2qp1p/2p1n1pB/p3p3/P3P1B1/2N2Q1P/2P2PP1/6K1 w - - bm Bxe6; id \"STS(v10.0) Simplification.055\"; c0 \"Bxe6=10, Qd1=2, Qd3=1, Qe3=2\";\n" +
            "4r1k1/1p5p/1r4p1/2pq1p2/P4P2/3P2Q1/1bP3RP/4R1BK b - - bm Rxe1; id \"STS(v10.0) Simplification.056\"; c0 \"Rxe1=10, Ra8=2, Rbe6=3, Rc8=3\";\n" +
            "4r1k1/2r1bp2/1p4p1/3BP2p/nP5P/5K2/1B1R1P2/2R5 b - - bm Rxc1; id \"STS(v10.0) Simplification.057\"; c0 \"Rxc1=10, Ra7=1, Rcc8=3\";\n" +
            "4r1k1/2r2ppp/2nq1n2/1BbppP2/Q7/2P1BN1P/5PP1/RR4K1 w - - bm Bxc5; id \"STS(v10.0) Simplification.058\"; c0 \"Bxc5=10, Nd2=6, Qb3=6, Re1=6\";\n" +
            "4r2k/p1r1b1pp/1p1pqn2/4p3/1PP4B/P4B1P/4QPP1/2RR2K1 w - - bm Bxf6; id \"STS(v10.0) Simplification.059\"; c0 \"Bxf6=10, Qd2=6, Qe3=6, Re1=6\";\n" +
            "4r2k/p2n1pp1/4p2p/1q1r3P/2pR1BP1/P1P1R3/1P2QP2/2K5 b - - bm Rxd4; id \"STS(v10.0) Simplification.060\"; c0 \"Rxd4=10, Nc5=4, Rc8=2\";\n" +
            "4r3/1pn3k1/4p1b1/p1Pp3r/3P2NR/1P3B2/3K2P1/4R3 w - - bm Rxh5; id \"STS(v10.0) Simplification.061\"; c0 \"Rxh5=10, g3=3, Reh1=3\";\n" +
            "4rrk1/2pb3p/p7/1ppPq3/2P1P1pP/2N5/PP2Q2P/4RR1K w - - bm Rxf8+; id \"STS(v10.0) Simplification.062\"; c0 \"Rxf8+=10, a3=3, Kg2=4, Rf2=2\";\n" +
            "5B1k/p6p/1p2q1r1/2p1P1p1/3b1n2/PQ3R2/1P4P1/K2R4 w - - bm Qxe6; id \"STS(v10.0) Simplification.063\"; c0 \"Qxe6=10, g3=3, Qc2=1\";\n" +
            "5k2/4pp2/1N2n1pp/r3P3/P5PP/2rR1K2/P7/3R4 b - - bm Rxd3+; id \"STS(v10.0) Simplification.064\"; c0 \"Rxd3+=10, Rc6=4\";\n" +
            "5qk1/1p1rbrpp/pP3p2/P1pbp3/1n6/BN1P2P1/1Q2PPBP/R1R3K1 w - - bm Bxd5; id \"STS(v10.0) Simplification.065\"; c0 \"Bxd5=10, Bxb4=7, Rab1=6, Rc3=4\";\n" +
            "5rk1/8/3p3p/p1qP2p1/Rrb1P3/3p1P2/1P1Q1RP1/3N2K1 w - - bm Rxb4; id \"STS(v10.0) Simplification.066\"; c0 \"Rxb4=10, b3=3, Ra3=2\";\n" +
            "6k1/1r3pp1/4b2p/p2pP2P/1r1R1Q2/1Pq1N3/2P3P1/3R3K b - - bm Rxd4; id \"STS(v10.0) Simplification.067\"; c0 \"Rxd4=10, R7b5=5, R7b6=4, Rb8=5\";\n" +
            "6k1/3q2b1/p1rrnpp1/P3p3/4P3/1pBR3Q/1P4PP/1B1R3K b - - bm Rxd3; id \"STS(v10.0) Simplification.068\"; c0 \"Rxd3=10, Bf8=6, Nd4=5, Qc7=5\";\n" +
            "6k1/3qbppp/5n2/PQpPp3/4P3/5PP1/1P4KP/2B5 w - - bm Qxd7; id \"STS(v10.0) Simplification.069\"; c0 \"Qxd7=10\";\n" +
            "6k1/5p2/1q1bp1p1/1Pp1B2p/2Pn3P/6P1/1Q2BP2/6K1 w - - bm Bxd6; id \"STS(v10.0) Simplification.070\"; c0 \"Bxd6=10, Bf6=2, Bxd4=1, f4=2\";\n" +
            "6r1/4pp1k/3p3p/2qP1Pb1/r3P1PB/1R5K/4Q3/1R6 b - - bm Bxh4; id \"STS(v10.0) Simplification.071\"; c0 \"Bxh4=10, h5=5, Qd4=5, Rc8=2\";\n" +
            "7r/2r1kp2/3pp2p/p3q1p1/4B3/P5P1/1Q1B1P1P/4R1K1 w - - bm Qxe5; id \"STS(v10.0) Simplification.072\"; c0 \"Qxe5=10, Qb1=5, Qb3=3, Qb6=3\";\n" +
            "8/1p1bkq2/p2pp1n1/P1b1p1P1/4P1QN/1KNB4/1PP5/8 b - - bm Nxh4; id \"STS(v10.0) Simplification.073\"; c0 \"Nxh4=10, Bc6=5, Be8=4, Bf2=5\";\n" +
            "8/3n1nk1/q2b4/3p2p1/2pPp3/2P1P3/1Q4PN/3BB1K1 b - - bm Bxh2+; id \"STS(v10.0) Simplification.074\"; c0 \"Bxh2+=10, Bc7=4, Nf6=4, Nh6=4\";\n" +
            "8/p1r4p/1p1qnkpP/3Ppp2/8/P2Q2P1/BP3P2/2R3K1 w - - bm Rxc7; id \"STS(v10.0) Simplification.075\"; c0 \"Rxc7=10, Rc3=6, Rc4=6, Re1=6\";\n" +
            "8/r4p2/P1ppk3/2p4p/1rPb1BqP/RR1PpQP1/4P1K1/8 b - - bm Qxf3+; id \"STS(v10.0) Simplification.076\"; c0 \"Qxf3+=10, f5=2, Ra8=1, Rxb3=1\";\n" +
            "br2r1k1/5p1p/p7/nq4Pp/p3P3/P1PQ4/1P2R1B1/K1BR4 b - - bm Qxd3; id \"STS(v10.0) Simplification.077\"; c0 \"Qxd3=10, Nb3+=5, Rbc8=4, Rbd8=4\";\n" +
            "br3rk1/p2q1pp1/3p1n1p/2p1p3/2PnP3/P1BP1NP1/1R1Q1PBP/1R4K1 b - - bm Rxb2; id \"STS(v10.0) Simplification.078\"; c0 \"Rxb2=10, Nxf3+=4, Qc7=4, Rb7=5\";\n" +
            "q3b3/4Bpkp/6p1/3Pp3/r3r3/1Q5P/R4PP1/R5K1 w - - bm Rxa4; id \"STS(v10.0) Simplification.079\"; c0 \"Rxa4=10, Bd6=4, Kh1=5, Ra3=5\";\n" +
            "qr3nk1/r4p1p/2p1p1pP/p1PpPbP1/3P1P2/QR2R3/P2NB2K/8 w - - bm Rxb8; id \"STS(v10.0) Simplification.080\"; c0 \"Rxb8=10, Kg3=2, Qb2=4, Rb6=3\";\n" +
            "qr4k1/3b1pp1/4p2p/n2pP3/n1pP4/Q1P2NP1/3N1P1P/1R3BK1 b - - bm Rxb1; id \"STS(v10.0) Simplification.081\"; c0 \"Rxb1=10, Bc6=2, Be8=3, Rb7=2\";\n" +
            "R1b2b2/2kp1p1p/B2npp2/2p5/4P1r1/6P1/3N1P1P/3K3R w - - bm Bxc8; id \"STS(v10.0) Simplification.082\"; c0 \"Bxc8=10, f3=3, h3=3, Kc2=3\";\n" +
            "r1b2rk1/1p2bpp1/5n2/1Q2pq2/2P1N3/p3B1Pp/P2RPP1P/3R2KB w - - bm Nxf6+; id \"STS(v10.0) Simplification.083\"; c0 \"Nxf6+=10, Nc3=4, Ng5=2, Qb1=1\";\n" +
            "r1bq4/3n1pkp/2pRr1p1/8/nPP1PP2/6PP/2Q1N1B1/1R4K1 w - - bm Rxe6; id \"STS(v10.0) Simplification.084\"; c0 \"Rxe6=10, Rdd1=3\";\n" +
            "r1r1bnk1/5pp1/p2pp3/2q1n1P1/3QP2P/1PN4B/2P1NR1K/3R4 b - - bm Qxd4; id \"STS(v10.0) Simplification.085\"; c0 \"Qxd4=10, a5=5, Bb5=4, Rab8=3\";\n" +
            "r2qr1k1/2p2pp1/3p1n2/3P1n1p/1Q3P2/1B5P/PP4PB/2R1R2K w - - bm Rxe8+; id \"STS(v10.0) Simplification.086\"; c0 \"Rxe8+=10, Qc4=5, Ra1=4, Rb1=4\";\n" +
            "r2r2k1/1b1nqpp1/pp5p/2pNP3/P3Q3/5N2/1P3PPP/2RR2K1 b - - bm Bxd5; id \"STS(v10.0) Simplification.087\"; c0 \"Bxd5=10, Kh8=6, Qe6=6, Qe8=6\";\n" +
            "r2r2k1/4qp2/p5p1/2p4p/5P2/3RP1K1/PPQ4P/3R4 b - - bm Rxd3; id \"STS(v10.0) Simplification.088\"; c0 \"Rxd3=10, h4+=2, Rdb8=2, Re8=4\";\n" +
            "r3r1k1/2qn3p/1p2ppp1/3b4/p1PP1P2/4QN2/R5PP/1R3BK1 b - - bm Bxf3; id \"STS(v10.0) Simplification.089\"; c0 \"Bxf3=10, Bb7=4, Bc6=5, Bxc4=3\";\n" +
            "r3r1k1/5pbp/4bpp1/pp2q3/4P1PP/P1NR2Q1/1PP1B3/2KR4 b - - bm Qxg3; id \"STS(v10.0) Simplification.090\"; c0 \"Qxg3=10, Qb8=1, Qc5=2\";\n" +
            "r3r1k1/6p1/p2p1qp1/1p1P3p/4P1bP/P2Q3B/1PP5/1K2R2R b - - bm Bxh3; id \"STS(v10.0) Simplification.091\"; c0 \"Bxh3=10, Bc8=5, Qxh4=5, Rf8=5\";\n" +
            "r3r3/p5bk/2pq1ppp/1p1b4/3P4/P2N1N2/1P3PPP/2RQR1K1 b - - bm Rxe1+; id \"STS(v10.0) Simplification.092\"; c0 \"Rxe1+=10, a5=6, h5=7, Rac8=5\";\n" +
            "r3rbk1/p1qn1p1p/2p1n1p1/Pp2p1B1/1P2P3/2N1Q3/2PN1PPP/1R2R1K1 b - - bm Nxg5; id \"STS(v10.0) Simplification.093\"; c0 \"Nxg5=10, a6=3, Nb8=3, Rac8=4\";\n" +
            "r4r1k/1bq1bppp/p2p4/1p1Nn3/4PR2/PN1B3Q/1PP3PP/R5K1 b - - bm Bxd5; id \"STS(v10.0) Simplification.094\"; c0 \"Bxd5=10, Bc8=5, Qc8=5, Qd8=1\";\n" +
            "r4rk1/1p2bppp/p3bn2/8/Pq1BP3/1BN1Q3/1PP3PP/R3K2R w KQ - bm Bxe6; id \"STS(v10.0) Simplification.095\"; c0 \"Bxe6=10, e5=2, O-O-O=2, Rd1=1\";\n" +
            "r4rk1/pp1qppbp/2n3p1/3b4/3PB2B/2P5/3N1PPP/1R1QR1K1 b - - bm Bxe4; id \"STS(v10.0) Simplification.096\"; c0 \"Bxe4=10, e6=5, Rac8=3, Rfe8=4\";\n" +
            "r4rk1/pp5p/2pp2pb/2n1nq2/2P2P2/2N1B3/PP2B1PP/2RQR1K1 w - - bm Bxc5; id \"STS(v10.0) Simplification.097\"; c0 \"Bxc5=10, g4=6, Kh1=6, Rf1=6\";\n" +
            "r4rk1/pppb3p/8/2pPqp2/2P1P1nP/1QN4B/PP5P/4RRK1 w - - bm Bxg4; id \"STS(v10.0) Simplification.098\"; c0 \"Bxg4=10, Qc2=3, Rf4=5\";\n" +
            "r5k1/5p2/7p/5qP1/1P1QR3/5P2/r4RK1/8 b - - bm Rxf2+; id \"STS(v10.0) Simplification.099\"; c0 \"Rxf2+=10, h5=6, hxg5=6, R2a6=6\";\n" +
            "rr4k1/p2nBpp1/q1p1pn1p/7P/2pPN3/P1P5/1PQ2PP1/2KR3R b - - bm Nxe4; id \"STS(v10.0) Simplification.100\"; c0 \"Nxe4=10, Rb3=4, Rb5=4\";" +
            "";

    private static final String[] splitUpPositions = positions.split("\n");

}

    
