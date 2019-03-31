package strategictestsuite;

import com.github.louism33.axolotl.search.EngineBetter;
import com.github.louism33.axolotl.search.EngineSpecifications;
import com.github.louism33.chesscore.MoveParser;
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
public class STS8fghPAWNS {


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

    public STS8fghPAWNS(Object edp, Object name) {
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
        
        Assert.assertTrue(contains(winningMoves, move) && !contains(losingMoves, move));
    }

    private static final String positions = "" +
            "1qr2k1r/pb3pp1/1b2p2p/3nP3/1p6/3B2QN/PP3PPP/R1BR2K1 b - - bm g5; id \"STS(v8.0) AKPC.001\"; c0 \"g5=10, Bd4=4, Kg8=4, Rd8=3\";\n" +
            "1r1qr1k1/p5b1/2p2ppp/3p4/1Pp5/P1Nn1Q2/3BN1PP/R4RK1 b - - bm f5; id \"STS(v8.0) AKPC.002\"; c0 \"f5=10, a5=6, d4=7, Rb7=5\";\n" +
            "1r1rq1k1/1p4p1/pNb1pp1p/1pP5/3P4/1PQ5/5PPP/R3R1K1 w - - bm f3; id \"STS(v8.0) AKPC.003\"; c0 \"f3=10, Qd3=4, Rad1=4, Red1=4\";\n" +
            "1r3r2/1p3q1k/1Q1p4/2pNbpp1/2P5/7P/PP2R1P1/3R3K b - - bm g4; id \"STS(v8.0) AKPC.004\"; c0 \"g4=10, f4=7, Ra8=3, Rbe8=6\";\n" +
            "1r4k1/2p2p1p/4n1p1/2qpP3/2nN4/1BPQ4/Pr3PPP/3RR1K1 w - - bm h4; id \"STS(v8.0) AKPC.005\"; c0 \"h4=10, g3=7, h3=8, Qh3=9\";\n" +
            "1r4k1/3q2pp/2np4/2p1pp2/2P1P3/R1BP1Q1P/5PP1/6K1 b - - bm f4; id \"STS(v8.0) AKPC.006\"; c0 \"f4=10, fxe4=8, Ne7=6, Rb1+=5\";\n" +
            "1r4k1/ppq2ppp/r4nn1/P2p4/2pP4/B1P1P1PP/1Q1N1PK1/RR6 b - - bm h5; id \"STS(v8.0) AKPC.007\"; c0 \"h5=10, h6=1, Ne7=1, Nh5=1\";\n" +
            "1rb1r1k1/4qpb1/p1np1npp/1pp5/2P1PN2/1P3PP1/PB4BP/1QRR1N1K b - - bm g5; id \"STS(v8.0) AKPC.008\"; c0 \"g5=10, Bb7=5, bxc4=5, Ne5=6\";\n" +
            "1rq1n3/2pr3k/2n1bppp/p1PNp3/Pp2P3/1P2Q1PP/3R1PBK/3RN3 w - - bm f4; id \"STS(v8.0) AKPC.009\"; c0 \"f4=10, Bf1=6, Nc2=5, Nd3=6\";\n" +
            "2b2kn1/5ppQ/1r5p/p2pN3/1qpP1PP1/5P2/P6P/1B4RK w - - bm g5; id \"STS(v8.0) AKPC.010\"; c0 \"g5=10, Bg6=8, Qc2=8, Rd1=7\";\n" +
            "2qn4/p5k1/1p1Qbpp1/1B1p3p/3P3P/2P5/P4PP1/4N1K1 w - - bm g3; id \"STS(v8.0) AKPC.011\"; c0 \"g3=10, Kf1=3, Nc2=3, Nd3=2\";\n" +
            "2r1r1k1/p2b2bp/1pn1p1pq/4Pp2/2BP4/P4N2/1P2NQPP/2R2RK1 w - - bm h4; id \"STS(v8.0) AKPC.012\"; c0 \"h4=10, Ba2=8, g3=7, h3=6\";\n" +
            "2r2bk1/p1q2p2/4p1p1/3nP1B1/1p2Q2P/1P3N2/Pr3PP1/R4RK1 w - - bm h5; id \"STS(v8.0) AKPC.013\"; c0 \"h5=10, Bc1=4, Qd4=2, Qg4=6\";\n" +
            "2r2k1r/1q1nbpp1/p2p4/1p1Pp3/8/P1B2BP1/1PP1QP2/3RR1K1 b - - bm f5; id \"STS(v8.0) AKPC.014\"; c0 \"f5=10, g6=8, Kg8=5\";\n" +
            "2r2rk1/1b1qb1pp/p2p4/1p1PpP2/4Q3/1P2BN1P/P4PP1/R3R1K1 w - - bm g4; id \"STS(v8.0) AKPC.015\"; c0 \"g4=10, a4=2\";\n" +
            "2r2rk1/2qbnppp/1p2p3/p2pPn2/P5QP/2PB1N2/2PB1PP1/1R2R1K1 w - - bm h5; id \"STS(v8.0) AKPC.016\"; c0 \"h5=10, g3=5, Kf1=5, Rb3=6\";\n" +
            "2r2rk1/p3ppbp/1pnp1np1/4q3/2P1P3/PPN1B2P/2Q1BPP1/R2R2K1 w - - bm f4; id \"STS(v8.0) AKPC.017\"; c0 \"f4=10, b4=6, Qd3=7, Rab1=6, Rac1=8\";\n" +
            "2r2rk1/p4qb1/1p1ppp2/2n1n1pp/2PN4/1PN2PPP/P1QBP1K1/2RR4 w - - bm f4; id \"STS(v8.0) AKPC.018\"; c0 \"f4=10, Be3=9, Kg1=5, Ndb5=6\";\n" +
            "2r2rk1/pq2ppbp/1pnp1np1/8/2P1P3/2N1BP2/PPQ1BPKP/3R1R2 w - - bm f4; id \"STS(v8.0) AKPC.019\"; c0 \"f4=10, b3=4, Rfe1=4, Rg1=6\";\n" +
            "2r3k1/p3np1p/1p1pp1p1/4b3/1PP5/P3B2P/2R2PP1/3R3K w - - bm g4; id \"STS(v8.0) AKPC.020\"; c0 \"g4=10, Bd4=5, g3=5, Kg1=5\";\n" +
            "2r3k1/pp2p1b1/q2pP1p1/3pnrPp/P7/1P5P/2PB1PB1/2RQR1K1 w - - bm f4; id \"STS(v8.0) AKPC.021\"; c0 \"f4=10, a5=5, Be3=2\";\n" +
            "2r5/1bq3kp/4pNp1/3pP1Q1/2pP4/7P/5PP1/R5K1 w - - bm h4; id \"STS(v8.0) AKPC.022\"; c0 \"h4=10, Nh5+=6, Ra3=5, Rc1=5\";\n" +
            "2r5/5k2/p1n3p1/P1Pqpp1p/3n4/4BPQN/6PP/3R3K w - - bm f4; id \"STS(v8.0) AKPC.023\"; c0 \"f4=10, Ng5+=6\";\n" +
            "2rr1bk1/p4pp1/1p2q2p/1PpN4/2Q1P3/P4PP1/3R3P/3R2K1 w - - bm f4; id \"STS(v8.0) AKPC.024\"; c0 \"f4=10, Kg2=8, Qc3=6, Rf1=5\";\n" +
            "2rr2k1/5ppp/p3p3/1p2P3/1Pn5/2B1PqP1/P1Q2P1P/3RR1K1 b - - bm h5; id \"STS(v8.0) AKPC.025\"; c0 \"h5=10, g5=6, h6=7, Rxd1=7\";\n" +
            "3bn1k1/1bq1npp1/3p3p/1p1Pp3/1Pp1P3/2P1B1NP/2BN1PPK/Q7 b - - bm f5; id \"STS(v8.0) AKPC.026\"; c0 \"f5=10, Bc8=3, Qc8=2\";\n" +
            "3q1r2/2r2ppk/5b1p/2pBpR2/P3P2P/1P4PK/5Q2/5R2 w - - bm g4; id \"STS(v8.0) AKPC.027\"; c0 \"g4=10, a5=6, Bc4=5, Qg1=5\";\n" +
            "3q2k1/rb1r3p/6p1/pQRp1p2/3N3P/1P2PBP1/P4P2/6K1 w - - bm h5; id \"STS(v8.0) AKPC.028\"; c0 \"h5=10, a3=7, Bg2=7, Qa4=8\";\n" +
            "3q3k/3r1p1p/4p3/p4p2/PbP2Q1P/1p3N2/1P2KPP1/1R6 b - - bm f6; id \"STS(v8.0) AKPC.029\"; c0 \"f6=10\";\n" +
            "3q3k/5p1p/3rp3/p4p1P/PbP2Q2/1p3N2/1P2KPP1/1R6 b - - bm f6; id \"STS(v8.0) AKPC.030\"; c0 \"f6=10, Kg8=9, Qd7=2, Qf6=2\";\n" +
            "3qr1k1/5r1p/p1p1n1p1/Ppp1Q3/4Pp2/1P1P1N1P/2P2RPK/R7 b - - bm g5; id \"STS(v8.0) AKPC.031\"; c0 \"g5=10, h6=1, Rff8=1, Rg7=1\";\n" +
            "3qrr1k/pp1n1ppp/2n3b1/2p5/3Pp1P1/P1P1P2P/BB2QPN1/2RR2K1 b - - bm f5; id \"STS(v8.0) AKPC.032\"; c0 \"f5=10, f6=5, h6=6, Qe7=6\";\n" +
            "3r1r1k/1p2q2b/p1p1p2p/2P1P3/PPQP1pPP/5B2/7K/4BR2 b - - bm h5; id \"STS(v8.0) AKPC.033\"; c0 \"h5=10, Kg7=4, Rd7=3, Rde8=5, Rf7=6\";\n" +
            "3r1rk1/pb1pb2p/1p2p1q1/1N2npp1/B1P5/4PPN1/PP2Q1PP/3R1RK1 b - - bm h5; id \"STS(v8.0) AKPC.034\"; c0 \"h5=10, Bc5=3, Bc6=3, Qf6=1\";\n" +
            "3r1rk1/pb2bppp/1pp1p3/2n1P2Q/2P1P3/1Pp2NP1/P4PBP/2R3K1 b - - bm g6; id \"STS(v8.0) AKPC.035\"; c0 \"g6=10, h6=2, Nxe4=3, Rd7=4\";\n" +
            "3r2k1/2q1rp2/4p1p1/2p1P2p/1p3Q2/1P1bP1PP/P4RBK/2R5 w - - bm g4; id \"STS(v8.0) AKPC.036\"; c0 \"g4=10, Qf6=7\";\n" +
            "3r2k1/3n1p1p/p1r3p1/q1P3Q1/P5B1/2R1B2P/5PP1/6K1 b - - bm h6; id \"STS(v8.0) AKPC.037\"; c0 \"h6=10, h5=6, Nf6=7, Nf8=4\";\n" +
            "3r2k1/p1r3pp/Q1p1pb2/2Pn1p1q/PpBPp3/1P2P1PP/1B2RPK1/4R3 b - - bm g5; id \"STS(v8.0) AKPC.038\"; c0 \"g5=10, Qe8=2, Qf7=2, Rb8=2\";\n" +
            "3r2r1/2qb2pk/1p2p2p/p1n1Pp2/P1PNpP2/1P2Q3/3RB1PP/3R2K1 b - - bm g5; id \"STS(v8.0) AKPC.039\"; c0 \"g5=10, Be8=8, g6=8, Rb8=8, Rc8=8\";\n" +
            "3r4/1pq3kp/2pp1pp1/p1n1r3/P1PNP3/1P2BPPB/2Q1N1K1/8 w - - bm f4; id \"STS(v8.0) AKPC.040\"; c0 \"f4=10, Bc1=2, Bd2=2, Nf4=1\";\n" +
            "3rb1k1/2q1b1p1/pp2p3/5p1p/1PP1p3/P3Q1PP/4P1RK/3N1R2 b - - bm h4; id \"STS(v8.0) AKPC.041\"; c0 \"h4=10, a5=6, Bf6=5, Kh7=4\";\n" +
            "3rb1k1/2q1b1pp/pp2p3/5p2/1PP1p3/P3Q1PP/4PR1K/3N1R2 b - - bm h5; id \"STS(v8.0) AKPC.042\"; c0 \"h5=10, a5=8, g6=6, Rc8=8\";\n" +
            "3rrk2/p2n1p1B/1pq4p/2ppB1p1/2bP4/PnN1PP2/1PQ3PP/3RR1K1 w - - bm f4; id \"STS(v8.0) AKPC.043\"; c0 \"f4=10, Bd3=6, Bg3=1, Bh8=5\";\n" +
            "4b1k1/pp1qbpp1/4p3/4Q2p/8/2P1N1P1/PP3PBP/6K1 b - - bm h4; id \"STS(v8.0) AKPC.044\"; c0 \"h4=10, b6=1, g6=3, Qd2=1\";\n" +
            "4qr1k/p4n1p/1p1p2p1/2pQ1n2/2P1NB2/P1P2P2/5KPP/3R4 w - - bm g4; id \"STS(v8.0) AKPC.045\"; c0 \"g4=10, g3=6, h3=7, Rd3=6, Re1=6\";\n" +
            "4r1k1/p4bpp/1p6/1P6/2PQ4/4rPq1/PR2PRB1/6K1 b - - bm h5; id \"STS(v8.0) AKPC.046\"; c0 \"h5=10, Qc7=1, R3e5=4\";\n" +
            "4r2k/6p1/2b2p2/pq5p/r2P2P1/2P1N1PP/B2Q1K2/4R3 b - - bm h4; id \"STS(v8.0) AKPC.047\"; c0 \"h4=10, Be4=3, Qb8=4, Rb8=2\";\n" +
            "4r3/2pq1pk1/1r4p1/p2pP1Np/1pnP4/2PQ3P/P3RPP1/4R1K1 w - - bm f4; id \"STS(v8.0) AKPC.048\"; c0 \"f4=10, cxb4=8, e6=8, Kh2=5\";\n" +
            "4r3/p4rk1/1p4p1/3Rp1q1/4P2p/2Q2P2/P5PP/3R1K2 w - - bm h3; id \"STS(v8.0) AKPC.049\"; c0 \"h3=10, Qd2=3, R1d3=5, Rd7=8\";\n" +
            "4rr1k/1pq3bp/p1b2np1/P1Ppp3/1P1N2P1/1B3PB1/Q6P/3RR1K1 b - - bm h5; id \"STS(v8.0) AKPC.050\"; c0 \"h5=10, h6=7, Qb8=7, Re7=6\";\n" +
            "4rr1k/2q5/1pnn1b1p/p1p2b2/N1PppPp1/PP1P4/2QBPPBP/3R1RNK b - - bm h5; id \"STS(v8.0) AKPC.051\"; c0 \"h5=10, Re6=4, Rf7=4, Rg8=8\";\n" +
            "5k1r/1b2rp2/pR4pp/1p3n2/2p5/2N5/PP3PPP/3Q2K1 w - - bm f3; id \"STS(v8.0) AKPC.052\"; c0 \"f3=10, g4=7, h4=3, Rxb7=5\";\n" +
            "5k2/q7/2p2pp1/4p1pn/1pP1PnN1/1P2RPPP/3r1B1K/5Q2 b - - bm f5; id \"STS(v8.0) AKPC.053\"; c0 \"f5=10, Ne6=1, Nxg3=4, Qa2=1\";\n" +
            "5r1k/2qb1ppp/6nb/3B4/pp1BQ3/5NP1/P1P2P1P/4R1K1 w - - bm h4; id \"STS(v8.0) AKPC.054\"; c0 \"h4=10, Ba1=5, Ne5=7, Rb1=7\";\n" +
            "5r1k/2qb1ppp/6nb/3B4/ppPBQ3/5NP1/P4P1P/4R1K1 w - - bm h4; id \"STS(v8.0) AKPC.055\"; c0 \"h4=10, Be5=5, c5=7, Ne5=5\";\n" +
            "5r2/3br1kp/3q2p1/1pNp4/1P1Pnp2/5B2/2P1R1PP/2Q2RK1 b - - bm g5; id \"STS(v8.0) AKPC.056\"; c0 \"g5=10, Bc8=6, Bf5=1, Rfe8=1\";\n" +
            "5rk1/2r1bpp1/p2p1n1p/1p2pP2/1P3q2/1BP4P/PB3PP1/R2QR1K1 w - - bm g4; id \"STS(v8.0) AKPC.057\"; c0 \"g4=10, a4=7, Qc2=6, Qe2=7\";\n" +
            "5rk1/5p2/pnB1p1pp/q3P3/2nP1P2/1R1N3P/6PK/2Q5 w - - bm f5; id \"STS(v8.0) AKPC.058\"; c0 \"f5=10, Bb7=3, Nc5=3, Rc3=3\";\n" +
            "5rk1/p1r3pp/1p2q3/1P1p4/1QnB2P1/2R1PP1P/4R1K1/8 b - - bm h5; id \"STS(v8.0) AKPC.059\"; c0 \"h5=10, Qf7=8, Re7=9, Rf4=7\";\n" +
            "6k1/2qn3p/pp1pprpB/5r2/1PP5/P4PbP/3QB1P1/2R2RK1 w - - bm f4; id \"STS(v8.0) AKPC.060\"; c0 \"f4=10, Be3=3, Kh1=3, Rc3=4\";\n" +
            "6k1/5p1p/1p1n2pP/1n6/q1rP4/2pQP3/r1N2PP1/BRR3K1 b - - bm f5; id \"STS(v8.0) AKPC.061\"; c0 \"f5=10, f6=3\";\n" +
            "6k1/p2nqpp1/Qp2p2p/8/3PNb2/P4N2/1Pr2PPP/4R1K1 w - - bm g3; id \"STS(v8.0) AKPC.062\"; c0 \"g3=10, b4=1, Qxa7=1, Rb1=3\";\n" +
            "8/3qpk2/1p3r1p/p1pPRp2/2P2P2/P4KPP/4Q3/8 w - - bm g4; id \"STS(v8.0) AKPC.063\"; c0 \"g4=10, Qc2=6, Qd1=6, Qd3=6\";\n" +
            "8/3r1pk1/p1pqp1p1/8/P1Pb4/5RPP/2Q5/4R2K b - - bm f5; id \"STS(v8.0) AKPC.064\"; c0 \"f5=10, a5=7, c5=1, e5=6, Re7=1\";\n" +
            "8/6pk/p5rp/2NpPq2/2bPp2b/P3P1P1/6Q1/1R2B1K1 b - - bm h5; id \"STS(v8.0) AKPC.065\"; c0 \"h5=10, a5=4, Be2=7, Qg4=4\";\n" +
            "b4rk1/2pq1pp1/3p3p/B1p5/2PpP1n1/3P2P1/PR1Q1PBP/6K1 b - - bm f5; id \"STS(v8.0) AKPC.066\"; c0 \"f5=10, Ne5=8\";\n" +
            "b4rk1/p2q1pp1/3p3p/B1p5/2PpP1n1/P2P2P1/1R1Q1PBP/6K1 b - - bm f5; id \"STS(v8.0) AKPC.067\"; c0 \"f5=10, Re8=1\";\n" +
            "b5k1/6bp/3qp1p1/3n2P1/5P1K/2rBB3/6NP/3R1Q2 b - - bm h6; id \"STS(v8.0) AKPC.068\"; c0 \"h6=10, Bb7=2, Ne7=5, Rb3=2\";\n" +
            "n1rr2k1/3qppbp/p1nP2p1/6P1/5P1Q/1P2B3/P4PBP/2RR2K1 w - - bm f5; id \"STS(v8.0) AKPC.069\"; c0 \"f5=10, Bc5=4, Qh3=6, Rc4=2\";\n" +
            "nr4k1/1p1qrpb1/p6p/P2p2p1/1P1N4/4B1P1/2RQ1PKP/2R5 w - - bm h4; id \"STS(v8.0) AKPC.070\"; c0 \"h4=10, h3=4, Qd3=7, Rc5=3\";\n" +
            "q5k1/4bpp1/r3p2p/2p5/1pP5/1P3NPP/1RQ1PP2/6K1 b - - bm f5; id \"STS(v8.0) AKPC.071\"; c0 \"f5=10, Bf6=5, g6=5, Ra3=5\";\n" +
            "r1b1r1k1/1p1nqpbp/2ppn1p1/1P6/2P1P3/2N1BPP1/2Q1N1BP/1R1R2K1 w - - bm f4; id \"STS(v8.0) AKPC.072\"; c0 \"f4=10, bxc6=5, h3=5, Kh1=5\";\n" +
            "r1b2rk1/1pp1q1b1/2nppn1p/pN3pp1/2PP4/1Q4PN/PP2PPBP/R1BR2K1 w - - bm f4; id \"STS(v8.0) AKPC.073\"; c0 \"f4=10, d5=3\";\n" +
            "r1b2rk1/p4pp1/1q2p2p/1p1n4/3b2N1/3B2Q1/PPP2PPP/R1B2RK1 b - - bm f5; id \"STS(v8.0) AKPC.074\"; c0 \"f5=10, h5=5, Kh8=6, Rd8=7\";\n" +
            "r1b2rk1/pp2qppp/n2p4/2pPp3/P1N1P3/3P4/1P2BPPP/R1Q2RK1 w - - bm f4; id \"STS(v8.0) AKPC.075\"; c0 \"f4=10, b3=1\";\n" +
            "r1b2rk1/pp3pp1/1q2p2p/3n4/3b2N1/3B2Q1/PPP2PPP/R1B2RK1 b - - bm h5; id \"STS(v8.0) AKPC.076\"; c0 \"h5=10, f5=5, Kh8=6, Rd8=5\";\n" +
            "r1b4r/pp3k1p/3R1p2/B1p5/2P4Q/5q2/PP2NP1P/2K5 b - - bm h5; id \"STS(v8.0) AKPC.077\"; c0 \"h5=10, Be6=6, Rg8=3\";\n" +
            "r1q1k2r/3n3p/p5p1/1pbpP3/2p2P2/8/PPB1QP1P/R1B2RK1 w kq - bm f5; id \"STS(v8.0) AKPC.078\"; c0 \"f5=10, a4=5, Bb3=4, Rd1=3\";\n" +
            "r1q2rk1/p3npb1/1pnBb1pp/2p1P3/8/2N2NPP/PP2QPB1/R4RK1 w - - bm g4; id \"STS(v8.0) AKPC.079\"; c0 \"g4=10, h4=1, Qe4=2, Rfd1=4\";\n" +
            "r1r3k1/1bqn1pbp/pp1pp1p1/4n3/2P2N1P/1PN1PP2/P2BB1P1/2R1QRK1 w - - bm h5; id \"STS(v8.0) AKPC.080\"; c0 \"h5=10, Qf2=6, Qg3=5, Rd1=6\";\n" +
            "r2q1rk1/1p2n1bp/p1npp1p1/2p1p3/4P3/N1PPB1PP/1P3PB1/R2QR1K1 w - - bm h4; id \"STS(v8.0) AKPC.081\"; c0 \"h4=10, Nc2=2, Qb3=4, Qg4=5, Rf1=4\";\n" +
            "r2q1rk1/1p2p3/2p2ppp/p2n1b2/P2P4/1B3N1P/1PP2PP1/R2QR1K1 b - - bm g5; id \"STS(v8.0) AKPC.082\"; c0 \"g5=10, Bc8=6, Bd7=6, Kh7=6\";\n" +
            "r2r2k1/1p3p1p/1qbppQp1/p7/4P3/P1NR4/1P3PPP/1R4K1 w - - bm h4; id \"STS(v8.0) AKPC.083\"; c0 \"h4=10, b4=5, Rbd1=4, Rf3=6\";\n" +
            "r2r2k1/2pq1pp1/p1p2b1p/5p2/P2P4/2N3P1/1PQ1PP1P/R2R2K1 b - - bm f4; id \"STS(v8.0) AKPC.084\"; c0 \"f4=10, g5=9, Qe6=8, Rab8=7\";\n" +
            "r2r2k1/pp1qppbp/2p1bnp1/4B3/3P2P1/4QN1P/PP2PPB1/R1R3K1 b - - bm h5; id \"STS(v8.0) AKPC.085\"; c0 \"h5=10, a5=5, Bd5=2, h6=2\";\n" +
            "r3k3/1p1n1p2/2p1r1p1/3bP3/3P3Q/8/p1B2PPP/R5K1 w q - bm f4; id \"STS(v8.0) AKPC.086\"; c0 \"f4=10, Be4=8, f3=7, Qh6=5\";\n" +
            "r3qrk1/1p4bp/1n1p1p1n/pPpPp1p1/P3P3/1P1N3P/3B1PP1/2QBRRK1 w - - bm h4; id \"STS(v8.0) AKPC.087\"; c0 \"h4=10, Bc2=6, Bf3=7, Kh2=6, Nb2=7\";\n" +
            "r3qrk1/2p3pp/np6/3pN3/p2P4/P5P1/1PQ1PP2/3R1RK1 w - - bm f4; id \"STS(v8.0) AKPC.088\"; c0 \"f4=10, e3=6, e4=5, Rd2=6\";\n" +
            "r3r1k1/1pq4p/p2b1pp1/3pnb2/3N3Q/2P5/PP1B1PPP/R3RBK1 b - - bm g5; id \"STS(v8.0) AKPC.089\"; c0 \"g5=10, Bd7=3, Be4=3, Qd7=6\";\n" +
            "r3r1k1/4qpp1/2p5/p1ppP2p/6P1/1PQ2P1P/P1P5/3RR1K1 w - - bm f4; id \"STS(v8.0) AKPC.090\"; c0 \"f4=10, a4=1, Kg2=2, Re2=1\";\n" +
            "r3r1k1/pn1bnpp1/1p2p2p/1q1pP3/2pP1P1N/B1P2BP1/2P3QP/R4RK1 w - - bm f5; id \"STS(v8.0) AKPC.091\"; c0 \"f5=10, Bxe7=6, g4=5, g4=6, Rfb1=4\";\n" +
            "r3rb2/1p3k2/p1p2p2/2q2bpp/2PR4/1PQ4P/P4PPB/3R1BK1 b - - bm h4; id \"STS(v8.0) AKPC.092\"; c0 \"h4=10, Be7=6, g4=5, Kg7=6\";\n" +
            "r4k2/1b1qbp2/2pnp1r1/pp2B2p/2pPP1pP/P1N2P2/1P2B1PQ/2RR2K1 b - - bm f5; id \"STS(v8.0) AKPC.093\"; c0 \"f5=10, f6=2, gxf3=4, Kg8=2\";\n" +
            "r4rk1/1b3pbp/1qp1p1p1/p2n2B1/3P3P/P2B1N2/1PQ2PP1/R3R1K1 w - - bm h5; id \"STS(v8.0) AKPC.094\"; c0 \"h5=10, Bd2=1, Rac1=7, Re2=1\";\n" +
            "r4rk1/1p1qn1bp/p1npp1p1/2p1p3/4P2P/N1PPB1P1/1P3PB1/R2QR1K1 w - - bm h5; id \"STS(v8.0) AKPC.095\"; c0 \"h5=10, Bh3=8, Nc4=8, Rf1=4\";\n" +
            "r4rk1/1p2qppp/1np5/p2pNb2/P2Pn3/2NBP3/1PQ2PPP/2R2RK1 b - - bm f6; id \"STS(v8.0) AKPC.096\"; c0 \"f6=10, g6=6, h6=7, Nxc3=7\";\n" +
            "r4rk1/1pq1p3/2p2ppp/p2n1b2/P2P4/1B3N1P/1PP2PP1/R2QR1K1 b - - bm g5; id \"STS(v8.0) AKPC.097\"; c0 \"g5=10, Bc8=7, Kh7=7, Rf7=7\";\n" +
            "r4rk1/3n1pbp/3Pq1p1/4p3/p7/2Q1B1N1/P2R1PPP/2R3K1 b - - bm f5; id \"STS(v8.0) AKPC.098\"; c0 \"f5=10, h6=4, Nf6=5, Rfd8=5\";\n" +
            "r4rk1/ppp1qp1p/2p3p1/4n3/4P2P/2N1QP2/PPP2P2/2KR3R w - - bm h5; id \"STS(v8.0) AKPC.099\"; c0 \"h5=10, b3=1, Nb1=5, Ne2=4\";\n" +
            "r5k1/p2b1r2/1p1p1q2/2pPbp1p/P1P1p3/1P4P1/2RQNPBP/4R1K1 b - - bm h4; id \"STS(v8.0) AKPC.100\"; c0 \"h4=10, Kh7=8, Qg7=8, Rg7=8\";" +
            "";

    private static final String[] splitUpPositions = positions.split("\n");

}

    