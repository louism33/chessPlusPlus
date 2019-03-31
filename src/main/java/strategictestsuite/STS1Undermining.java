package strategictestsuite;

import com.github.louism33.axolotl.search.EngineBetter;
import com.github.louism33.axolotl.search.EngineSpecifications;
import com.github.louism33.utils.ExtendedPositionDescriptionParser;
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
public class STS1Undermining {


    private static final int timeLimit = 10_000;

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

    public STS1Undermining(Object edp, Object name) {
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

        Assert.assertTrue(contains(winningMoves, move) && !contains(losingMoves, move));
    }

    private static final String positions = "" +
            "1kr5/3n4/q3p2p/p2n2p1/PppB1P2/5BP1/1P2Q2P/3R2K1 w - - bm f5; id \"Undermine.001\"; c0 \"f5=10, Be5+=2, Bf2=3, Bg4=2\";\n" +
            "1n5k/3q3p/pp1p2pB/5r2/1PP1Qp2/P6P/6P1/2R3K1 w - - bm c5; id \"Undermine.002\"; c0 \"c5=10, Qd4+=4, b5=4, g4=3\";\n" +
            "1n6/4bk1r/1p2rp2/pP2pN1p/K1P1N2P/8/P5R1/3R4 w - - bm c5; id \"Undermine.003\"; c0 \"c5=10, Rd3=7, Rdd2=7, Rg3=7, Rd5=9\";\n" +
            "1nr5/1k5r/p3pqp1/3p4/1P1P1PP1/R4N2/3Q1PK1/R7 w - - bm b5; id \"Undermine.004\"; c0 \"b5=10, Kg3=4, Ng5=4, Qe3=4\";\n" +
            "1q2r1k1/1b2bpp1/p2ppn1p/2p5/P3PP1B/2PB1RP1/2P1Q2P/2KR4 b - - bm c4; id \"Undermine.005\"; c0 \"c4=10, Bc6=7, Qa8=7, Qc8=7\";\n" +
            "1q4k1/5p1p/p1rprnp1/3R4/N1P1P3/1P6/P5PP/3Q1R1K w - - bm e5; id \"Undermine.006\"; c0 \"e5=10, Nc3=3, Qd3=1, Qf3=2\";\n" +
            "1qr1k2r/1p2bp2/pBn1p3/P2pPbpp/5P2/2P1QBPP/1P1N3R/R4K2 b k - bm h4; id \"Undermine.007\"; c0 \"h4=10, Bd8=1, Bf8=1, Rh7=1\";\n" +
            "1r1b2k1/2r2ppp/p1qp4/3R1NPP/1pn1PQB1/8/PPP3R1/1K6 w - - bm g6; id \"Undermine.008\"; c0 \"g6=10, Ka1=2, Nd4=2, Rd3=2\";\n" +
            "1r1qk1nr/p3ppbp/3p2p1/1pp5/2bPP3/4B1P1/2PQNPBP/R2R2K1 w k - bm e5; id \"Undermine.009\"; c0 \"e5=10, Bf3=5, Nc1=5, Rxa7=4\";\n" +
            "1r1r2k1/p3n2p/b1nqpbp1/2pp4/1p3PP1/2PP1N2/PPN3BP/R1BRQ2K w - - bm d4; id \"Undermine.010\"; c0 \"d4=10, Ng5=6, a4=6, h3=6\";\n" +
            "1r2n1rk/pP2q2p/P2p4/4pQ2/2P2p2/5B1P/3R1P1K/3R4 w - - bm c5; id \"Undermine.011\"; c0 \"c5=10, Bc6=6, Qc2=5, Rg1=5\";\n" +
            "1r3bk1/7p/pp1q2p1/P1pPp3/2P3b1/4B3/1P1Q2BP/R6K w - - bm b4; id \"Undermine.012\"; c0 \"b4=10, Bg1=3, axb6=3, h3=1\";\n" +
            "1r3rk1/3n1pbp/1q1pp1p1/p1p5/2PnPP2/PPB1N1PP/6B1/1R1Q1RK1 b - - bm a4; id \"Undermine.013\"; c0 \"a4=10, Ne2+=2, Rfd8=2, h5=2\";\n" +
            "1r3rk1/p5bp/6p1/q1pPppn1/7P/1B1PQ1P1/PB3P2/R4RK1 b - - bm f4; id \"Undermine.014\"; c0 \"f4=10, Nf7=4, c4=5, e4=5\";\n" +
            "1r4k1/1rq2pp1/3b1nn1/pBpPp3/P1N4p/2PP1Q1P/6PB/2R2RK1 w - - bm d4; id \"Undermine.015\"; c0 \"d4=10, Bc6=4, Qf5=6, Rce1=4\";\n" +
            "1r4k1/p1rqbp1p/b1p1p1p1/NpP1P3/3PB3/3Q2P1/P4P1P/3RR1K1 w - - bm a4; id \"Undermine.016\"; c0 \"a4=10, Kg2=4, Qf3=4, h4=5\";\n" +
            "2r3k1/p2q1pp1/Pbrp3p/6n1/1BP1PpP1/R4P2/2QN2KP/1R6 b - - bm h5; id \"Undermine.017\"; c0 \"h5=10, Be3=4, Ne6=4, Qd8=4\";\n" +
            "1r6/2q2pk1/2n1p1pp/p1Pr4/P1RP4/1p1RQ2P/1N3PP1/7K b - - bm e5; id \"Undermine.018\"; c0 \"e5=10, Kh7=4, Qb7=5, Rbd8=5\";\n" +
            "1r6/R1nk1p2/1p4pp/pP1p1P2/P2P3P/5PN1/5K2/8 w - - bm h5; id \"Undermine.019\"; c0 \"h5=10, Ne2=4, Nf1=7, f4=7\";\n" +
            "1rb3k1/2pn2pp/p2p4/4p3/1pP4q/1P1PBP1P/1PQ2P2/R3R1K1 w - - bm c5; id \"Undermine.020\"; c0 \"c5=10, Kf1=5, Kg2=5, Ra5=5\";\n" +
            "1rbqnrk1/6bp/pp3np1/2pPp3/P1P1N3/2N1B3/1P2Q1BP/R4R1K w - - bm a5; id \"Undermine.021\"; c0 \"a5=10, Bg5=6, Kg1=6, Nxf6+=6\";\n" +
            "1rr3k1/1q3pp1/pnbQp2p/1p2P3/3B1P2/2PB4/P1P2RPP/R5K1 w - - bm f5; id \"Undermine.022\"; c0 \"f5=10, Qa3=3, Rd1=2, h3=2\";\n" +
            "2kr2r1/1bpnqp2/1p1ppn2/p5pp/P1PP4/4PP2/1P1NBBPP/R2Q1RK1 w - - bm b4; id \"Undermine.023\"; c0 \"b4=10, Qb1=3, Qc2=4, Re1=4\";\n" +
            "2b1k2r/5p2/pq1pNp1b/1p6/2r1PPBp/3Q4/PPP3PP/1K1RR3 w k - bm e5; id \"Undermine.024\"; c0 \"e5=10, Qd5=1, Qh3=7, f5=1\";\n" +
            "2b1r1k1/1p6/pQ1p1q1p/P2P3P/2P1pPpN/6P1/4R1K1/8 w - - bm c5; id \"Undermine.025\"; c0 \"c5=10, Kg1=6, Kh2=5, Re3=5\";\n" +
            "2b2rk1/2qn1p2/p2p2pp/2pPP3/8/4NN1P/P1Q2PP1/bB2R1K1 w - - bm e6; id \"Undermine.026\"; c0 \"e6=10, Nc4=1, Ng4=1, exd6=2\";\n" +
            "2bq2k1/1pr3bp/1Qpr2p1/P2pNp2/3P1P1P/6P1/5PB1/1RR3K1 w - - bm a6; id \"Undermine.027\"; c0 \"a6=10, Qc5=2, Rc5=3, h5=2\";\n" +
            "rr6/8/2pbkp2/ppp1p1p1/P3P3/1P1P1PB1/R1P2PK1/R7 b - - bm c4; id \"Undermine.28\"; c0 \"c4=10, Bc7=4, Rb6=1, b4=1\";\n" +
            "2r2rk1/pb2q2p/1pn1p2p/5p1Q/3P4/P1NB4/1P3PPP/R4RK1 w - - bm d5; id \"Undermine.029\"; c0 \"d5=10, Qxh6=5, Rac1=5, Rfd1=5\";\n" +
            "2kr4/ppqnbp1r/2n1p1p1/P2pP3/3P2P1/3BBN2/1P1Q1PP1/R4RK1 w - - bm a6; id \"Undermine.030\"; c0 \"a6=10, Qc2=3, Rfc1=4, g3=4\";\n" +
            "2q5/1pb2r1k/p1b3pB/P1Pp3p/3P4/3B1pPP/1R3P1K/2Q5 b - - bm h4; id \"Undermine.031\"; c0 \"h4=10, Bb5=5, Bd7=1, Qd8=5\";\n" +
            "2r1kb1r/1bqn1pp1/p3p3/1p2P1P1/3Np3/P1N1B3/1PP1Q2P/R4RK1 w k - bm g6; id \"Undermine.032\"; c0 \"g6=10, Bf4=6, Rae1=6, Rf4=6\";\n" +
            "2r1rb2/1bq2p1k/3p1np1/p1p5/1pP1P1P1/PP2BPN1/2Q3P1/R2R1BK1 b - - bm d5; id \"Undermine.033\"; c0 \"d5=10, Bg7=7, Kg8=7, Nd7=7\";\n" +
            "2r2bk1/pq3r1p/6p1/2ppP1P1/P7/BP1Q4/2R3P1/3R3K b - - bm d4; id \"Undermine.034\"; c0 \"d4=10, Qe7=1, Rcc7=1, c4=1\";\n" +
            "2r2rk1/1bb2ppp/p2ppn2/1p4q1/1PnNP3/P1N4P/2P1QPPB/3RRBK1 w - - bm a4; id \"Undermine.035\"; c0 \"a4=10, Nf3=4, Rb1=5, Rd3=5\";\n" +
            "2r2rk1/3q3p/p3pbp1/1p1pp3/4P3/2P5/PPN1QPPP/3R1RK1 b - - bm d4; id \"Undermine.036\"; c0 \"d4=10, Bg7=7, Qb7=6, Qd6=7\";\n" +
            "2r4k/pp3q1b/5PpQ/3p4/3Bp3/1P6/P5RP/6K1 w - - bm h4; id \"Undermine.037\"; c0 \"h4=10, Rg3=2, Rg4=2, Rg5=2\";\n" +
            "2r3k1/1b2b2p/r2p1pp1/pN1Pn3/1pPB2P1/1P5P/P3R1B1/5RK1 w - - bm g5; id \"Undermine.038\"; c0 \"g5=10, Rd1=4, Rff2=4, h4=5\";\n" +
            "2r3k1/5pp1/1pq4p/p7/P1nR4/2P2P2/Q5PP/4B1K1 b - - bm b5; id \"Undermine.039\"; c0 \"b5=10, Nd6=1, Ne3=1, Ne5=1\";\n" +
            "6k1/6pp/4r3/p1qpp3/Pp6/1n1P1B1P/1B2Q1P1/3R1K2 w - - bm d4; id \"Undermine.040\"; c0 \"d4=10, Bxe5=3, Qf2=2, Re1=3\";\n" +
            "r2qkb1r/1b1n1ppp/p3pn2/1pp5/3PP3/2NB1N2/PP3PPP/R1BQ1RK1 w kq - bm d5; id \"Undermine.041\"; c0 \"d5=10, Be3=3, a3=2, e5=3\";\n" +
            "r3r1k1/pn1bnpp1/1p2p2p/1q1pPP2/1BpP3N/2P2BP1/2P3QP/R4RK1 w - - bm f6; id \"Undermine.42\"; c0 \"f6=10, Bxe7=6, Rab1=5, g4=5\";\n" +
            "2r5/p3kpp1/1pn1p2p/8/1PP2P2/PB1R1KP1/7P/8 b - - bm a5; id \"Undermine.043\"; c0 \"a5=10, a6=1, e5=1, f5=1\";\n" +
            "2rq1rk1/1b2bppp/p2p1n2/1p1Pp3/1Pn1P3/5N1P/P1B2PP1/RNBQR1K1 w - - bm a4; id \"Undermine.044\"; c0 \"a4=10, Bb3=1, Nbd2=1, Nc3=1\";\n" +
            "2rqr1k1/1b2bp1p/ppn1p1pB/3n4/3P3P/P1NQ1N2/1PB2PP1/3RR1K1 w - - bm h5; id \"Undermine.045\"; c0 \"h5=10, Bc1=4, Nxd5=1, Rb1=1\";\n" +
            "3Rb3/5ppk/2r1r3/p5Pp/1pN2P1P/1P5q/P4Q2/K2R4 b - - bm a4; id \"Undermine.046\"; c0 \"a4=10, Rc7=7, Re7=6, Rxc4=7\";\n" +
            "3Rbrk1/4Q2p/6q1/pp3p2/4p2P/1P4P1/8/5R1K w - - bm g4; id \"Undermine.047\"; c0 \"g4=10, Kh2=5, Rc8=2, Rf2=3\";\n" +
            "3bn3/3r1p1k/3Pp1p1/1q6/Np2BP1P/3R2PK/8/3Q4 w - - bm h5; id \"Undermine.048\"; c0 \"h5=10, Bf3=6, Rd4=6, g4=6\";\n" +
            "3k1r1r/p2n1p1p/q2p2pQ/1p2P3/2pP4/P4N2/5PPP/2R1R1K1 w - - bm a4; id \"Undermine.049\"; c0 \"a4=10, Ng5=5, Qh4+=8, exd6=6\";\n" +
            "3r1bk1/1p2qp1p/p5p1/P1pPp3/2QnP3/3BB3/1P3PPP/2R3K1 w - - bm f4; id \"Undermine.050\"; c0 \"f4=10, Rb1=4, Rf1=4, h3=4\";\n" +
            "3r1bkr/2q3pp/1p1Npp2/pPn1P3/5B2/1P6/2P2PPP/R2QR1K1 w - - bm b4; id \"Undermine.051\"; c0 \"b4=10, Bd2=5, Qf3=4, exf6=3\";\n" +
            "3r2k1/p2q1pp1/1p2n1p1/2p1P2n/P4P2/2B1Q1P1/7P/1R3BK1 w - - bm a5; id \"Undermine.052\"; c0 \"a5=10, Bb5=3, Qe4=1, Ra1=3\";\n" +
            "3r4/8/pq3kr1/3Bp3/7p/1P3P2/P5PP/3RQ2K b - - bm h3; id \"Undermine.053\"; c0 \"h3=10, Kg7=5, Rd7=4, Rh6=5\";\n" +
            "3r4/pk1p3p/1p2pp2/1N6/2P1KP2/6P1/3R3P/8 w - - bm f5; id \"Undermine.054\"; c0 \"f5=10, Kd4=5, Ke3=5, Nc3=5\";\n" +
            "4k2r/1b2b3/p3pp1p/1p1p4/3BnpP1/P1P4R/1KP4P/5BR1 w k - bm g5; id \"Undermine.055\"; c0 \"g5=10, Be2=5, a4=6, c4=5\";\n" +
            "4k3/r2bbprp/3p1p1N/2qBpP2/ppP1P1P1/1P1R3P/P7/1KR1Q3 w - - bm a3; id \"Undermine.056\"; c0 \"a3=10, Qd2=3, Rc2=3, h4=3\";\n" +
            "4q1k1/pb5p/Nbp1p1r1/3r1p2/PP1Pp1pP/4P1P1/1BR1QP2/2R3K1 w - - bm b5; id \"Undermine.057\"; c0 \"b5=10, Ba1=3, Kg2=3, a5=3\";\n" +
            "4r1k1/1pb3qp/p1b1r1p1/P1Pp4/3P1p2/2BB4/1R1Q1PPP/1R4K1 b - - bm f3; id \"Undermine.058\"; c0 \"f3=10, Qd7=7, Qe7=7, Qf6=7\";\n" +
            "4r1k1/5p1p/p2q2p1/3p4/3Qn3/2P1RN2/Pr3PPP/R5K1 w - - bm c4; id \"Undermine.059\"; c0 \"c4=10, Ree1=5, a3=5, g3=5\";\n" +
            "4rr1k/pp1n2bp/7n/1Pp1pp1q/2Pp3N/1N1P1PP1/P5QP/2B1RR1K b - - bm f4; id \"Undermine.060\"; c0 \"f4=10, Nf7=3, Rf7=4, b6=1\";\n" +
            "4rrk1/p6p/2q2pp1/1p6/2pP1BQP/5N2/P4PP1/2R3K1 w - - bm h5; id \"Undermine.061\"; c0 \"h5=10, Bd2=1, Qg3=1, a4=1\";\n" +
            "5nk1/1bp1rnp1/pp1p4/4p1P1/2PPP3/NBP5/P2B4/4R1K1 w - - bm c5; id \"Undermine.062\"; c0 \"c5=10, Kf1=7, Kf2=7, d5=7\";\n" +
            "5r2/1p1k4/2bp4/r3pp1p/PRP4P/2P2PP1/2B2K2/7R b - - bm f4; id \"Undermine.063\"; c0 \"f4=10, Kc7=4, Raa8=4, Rf7=4\";\n" +
            "5r2/5p1Q/4pkp1/p7/1pb2q1P/5P2/P4RP1/3R2K1 w - - bm h5; id \"Undermine.064\"; c0 \"h5=10, Rb2=1, Rd7=1, Rdd2=1\";\n" +
            "5rk1/1Q3pp1/p2p3p/4p1b1/N3PqP1/1N1K4/PP6/3R4 b - - bm d5; id \"Undermine.065\"; c0 \"d5=10, Qf3+=1, Qxg4=1, h5=1\";\n" +
            "7r/3nkpp1/4p3/p1pbP3/1r3P1p/1P2B2P/P2RBKP1/7R b - - bm a4; id \"Undermine.066\"; c0 \"a4=10, Rc8=6, Rd8=6, Rhb8=6\";\n" +
            "8/1r1rq2k/2p3p1/3b1p1p/4p2P/1N1nP1P1/2Q2PK1/RR3B2 b - - bm f4; id \"Undermine.067\"; c0 \"f4=10, Rb4=3, c5=3, g5=3\";\n" +
            "8/1r2k3/4p2p/R3K2P/1p1P1P2/1P6/8/8 w - - bm f5; id \"Undermine.068\"; c0 \"f5=10, Ke4=4, Rc5=4, d5=3\";\n" +
            "8/3r1pp1/p7/2k2PpP/rp1pB3/2pK1P2/P1R5/1R6 w - - bm f6; id \"Undermine.069\"; c0 \"f6=10, Rg1=1, Rh1=1, f4=2\";\n" +
            "8/6k1/3P1bp1/2B1p3/1P6/1Q3P1q/7r/1K2R3 b - - bm e4; id \"Undermine.070\"; c0 \"e4=10, Qc8=1, Qf5+=1, g5=1\";\n" +
            "b2rrbk1/2q2p1p/pn1p2p1/1p4P1/2nNPB1P/P1N3Q1/1PP3B1/1K1RR3 w - - bm h5; id \"Undermine.071\"; c0 \"h5=10, Bc1=6, Na2=6, Qh3=6\";\n" +
            "b7/2pr1kp1/1p3p2/p2p3p/P1nP1N2/4P1P1/P1R2P1P/2R3K1 w - - bm e4; id \"Undermine.072\"; c0 \"e4=10, Rc3=5, Re2=6, f3=5\";\n" +
            "k1qbr1n1/1p4p1/p1p1p1Np/2P2p1P/3P4/R7/PP2Q1P1/1K1R4 w - - bm d5; id \"Undermine.073\"; c0 \"d5=10, Ra4=3, Rdd3=4, g4=3\";\n" +
            "r1b1rnk1/pp3pq1/2p3p1/6P1/2B2P1R/2P5/PP1Q2P1/2K4R w - - bm f5; id \"Undermine.074\"; c0 \"f5=10, Bd3=3, Rh8+=1, g4=4\";\n" +
            "r1bq1rk1/pp3pbp/3Pp1p1/2p5/4PP2/2P5/P2QB1PP/1RB1K2R b K - bm e5; id \"Undermine.075\"; c0 \"e5=10, Bd7=7, Qh4+=7, b6=6\";\n" +
            "r1bqr2k/pppn2bp/4n3/2P1p1p1/1P2Pp2/5NPB/PBQN1P1P/R4RK1 w - - bm c6; id \"Undermine.076\"; c0 \"c6=10, Nb3=5, Nc4=5, Rac1=5\";\n" +
            "r1br1k2/1pq2pb1/1np1p1pp/2N1N3/p2P1P1P/P3P1R1/1PQ3P1/1BR3K1 w - - bm h5; id \"Undermine.077\"; c0 \"h5=10, Ba2=4, Re1=4, Rf1=5\";\n" +
            "r1n2k1r/5pp1/2R5/pB2pPq1/P2pP3/6Pp/1P2Q2P/5RK1 w - - bm f6; id \"Undermine.078\"; c0 \"f6=10, Qd3=2, Rc5=2, Rg6=2\";\n" +
            "r1r2bk1/pp1n1p1p/2pqb1p1/3p4/1P1P4/1QN1PN2/P3BPPP/2RR2K1 w - - bm b5; id \"Undermine.079\"; c0 \"b5=10, Qc2=5, Rb1=4, a3=4\";\n" +
            "r2q1r2/pp1b2kp/2n1p1p1/3p4/3P1P1P/2PB1N2/6P1/R3QRK1 w - - bm h5; id \"Undermine.080\"; c0 \"h5=10, Qe3=3, Rb1=4, g3=4\";\n" +
            "r2q1rk1/pp2b1pp/1np1b3/4pp2/1P6/P1NP1BP1/2Q1PP1P/1RB2RK1 w - - bm b5; id \"Undermine.081\"; c0 \"b5=10, Be3=1, Bg2=1, Re1=1\";\n" +
            "r2q4/6k1/r1p3p1/np1p1p2/3P4/4P1P1/R2QBPK1/7R w - - bm e4; id \"Undermine.082\"; c0 \"e4=10, Qb2=5, Qc3=5, Rc2=2\";\n" +
            "r2qr1k1/pp3pbp/5np1/2p2b2/8/2PP1Q2/PPB3PP/RNB2RK1 b - - bm c4; id \"Undermine.083\"; c0 \"c4=10, Bg4=3, Ng4=2, Qd7=3\";\n" +
            "r3k2r/1bq1bpp1/p4n2/2p1pP2/2NpP2p/3B4/PPP3PP/R1B1QR1K b k - bm h3; id \"Undermine.084\"; c0 \"h3=10, Bc6=3, Bd8=1, Kf8=1\";\n" +
            "r3k2r/2q2p2/p2bpPpp/1b1p4/1p1B1PPP/8/PPPQ4/1K1R1B1R w kq - bm f5; id \"Undermine.085\"; c0 \"f5=10, Be3=6, Bxb5+=4, h5=4\";\n" +
            "r3k2r/ppq2p1p/2n1p1p1/3pP3/5PP1/2P1Q3/PP2N2P/3R1RK1 b k - bm h5; id \"Undermine.086\"; c0 \"h5=10, O-O=3, Qb6=1, Rc8=1, Rc8=3\";\n" +
            "r3r1k1/1pp1np1p/1b1p1p2/pP2p3/2PP2b1/P3PN2/1B3PPP/R3KB1R w KQ - bm c5; id \"Undermine.087\"; c0 \"c5=10, Be2=3, Rd1=3, dxe5=3\";\n" +
            "r3r1k1/1pq2pbp/p1ppbnp1/4n3/2P1PB2/1NN2P2/PP1Q2PP/R3RBK1 w - - bm c5; id \"Undermine.088\"; c0 \"c5=10, Bxe5=7, Nd1=7, Red1=7\";\n" +
            "r3r1k1/bpp1np1p/3p1p2/pPP1p3/3P2b1/P3PN2/1B3PPP/R3KB1R w KQ - bm b6; id \"Undermine.089\"; c0 \"b6=10, Rc1=3, Rd1=2, h3=5\";\n" +
            "r3r1k1/pp2q3/2b1pp2/6pN/Pn1P4/6R1/1P3PP1/3QRBK1 w - - bm f4; id \"Undermine.090\"; c0 \"f4=10, Qd2=4, b3=5, f3=3\";\n" +
            "r4r2/1p2pbk1/1np1qppp/p7/3PP2P/P1Q2NP1/1P3PB1/2R1R1K1 w - - bm h5; id \"Undermine.091\"; c0 \"h5=10, Qc5=5, Qe3=3, b4=2\";\n" +
            "r4r2/2p2kb1/1p1p2p1/qPnPp2n/2B1PP2/pP6/P1Q1N2R/1KB4R w - - bm f5; id \"Undermine.092\"; c0 \"f5=10, Bd2=5, Rg1=3, Rg2=4\";\n" +
            "r4rk1/2p5/p2p1n2/1p1P3p/2P1p1pP/1P4B1/1P3PP1/3RR1K1 w - - bm c5; id \"Undermine.093\"; c0 \"c5=10, Rc1=5, Rd2=4, Re2=6\";\n" +
            "r4rk1/2qnb1pp/4p3/ppPb1p2/3Pp3/1PB3P1/R1QNPPBP/R5K1 b - - bm a4; id \"Undermine.094\"; c0 \"a4=10, Bf6=6, Qc6=6, e5=6\";\n" +
            "r4rk1/p5pp/1p2b3/2Pn1p2/P2Pp2P/4P1Pq/2Q1BP2/R1BR2K1 w - - bm a5; id \"Undermine.095\"; c0 \"a5=10, Bc4=5, Bf1=5, Rb1=6\";\n" +
            "r4rk1/pbq2p2/2p2np1/1p2b2p/4P3/2N1BPP1/PPQ1B2P/R2R2K1 b - - bm h4; id \"Undermine.096\"; c0 \"h4=10, Rfd8=2, a5=3, a6=2\";\n" +
            "r4rk1/pp1b2b1/n2p1nq1/2pP1p1p/2P1pP2/PP4PP/1BQ1N1B1/R3RNK1 b - - bm h4; id \"Undermine.097\"; c0 \"h4=10, Rab8=1, Rae8=1, Rf7=1\";\n" +
            "rn3rk1/p1p1qp2/1pbppn1p/6p1/P1PP4/2PBP1B1/3N1P1P/R2QK1R1 w Q - bm h4; id \"Undermine.098\"; c0 \"h4=10, Qe2=1, a5=1, f4=1\";\n" +
            "rnbq1rk1/2p1p1bp/p3pnp1/1p6/3P4/1QN1BN2/PP3PPP/R3KB1R w KQ - bm a4; id \"Undermine.099\"; c0 \"a4=10, Rc1=6, g3=6, h3=6\";\n" +
            "rr3n1k/q3bpn1/2p1p1p1/2PpP2p/pP1P1N1P/2BB1NP1/P2Q1P2/6RK w - - bm g4; id \"Undermine.100\"; c0 \"g4=10, Kh2=4, Qc1=2, a3=3\";" +
            "";

    private static final String[] splitUpPositions = positions.split("\n");

}

    
