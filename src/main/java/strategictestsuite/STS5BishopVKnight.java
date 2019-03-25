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
public class STS5BishopVKnight {


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

    public STS5BishopVKnight(Object edp, Object name) {
        EPDObject = (ExtendedPositionDescriptionParser.EPDObject) edp;
    }

    @Test
    public void test() {
        System.out.println(EPDObject.getBoardFen());
        System.out.println(EPDObject.getBoard());
        int[] winningMoves = EPDObject.getBestMoves();
        int[] losingMoves = EPDObject.getAvoidMoves();
        EngineSpecifications.DEBUG = false;
        int move = EngineBetter.searchFixedTime(EPDObject.getBoard(), timeLimit);

        Assert.assertTrue(contains(winningMoves, move) && !contains(losingMoves, move));
    }

    private static final String positions = "" +
            "1b3rk1/5ppp/2p2rq1/1p1n4/3P2P1/1BPbBP2/1P1N2QP/R3R1K1 w - - bm Bxd5; c0 \"Bxd5=10, Ne4=6\"; id \"STS(v5.0) Bishop vs Knight.001\";\n" +
            "1k1r2r1/1p2bp2/4q2p/p1ppP3/6b1/2PQ2B1/PP2NPP1/R1R3K1 b - - bm Bxe2; c0 \"Bxe2=10, Bf5=3, Bg5=3, c4=4\"; id \"STS(v5.0) Bishop vs Knight.002\";\n" +
            "1q2n1k1/r4pb1/6p1/1bpPN1Pp/p1N1PP2/3B2KP/R3Q3/8 b - - bm Bxe5; c0 \"Bxe5=10\"; id \"STS(v5.0) Bishop vs Knight.003\";\n" +
            "1r1qr1k1/1b1nbpp1/ppnp3p/8/2P1PBB1/2N5/PPNQ2PP/2R2RK1 w - - bm Bxd7; c0 \"Bxd7=10, Be2=2, Ne3=3, Rcd1=3\"; id \"STS(v5.0) Bishop vs Knight.004\";\n" +
            "1r1r2k1/2qp1ppp/2pbpn2/8/3BP3/3B2P1/P1P2P1P/R2Q1RK1 w - - bm Bxf6; c0 \"Bxf6=10, Qd2=7, Qe1=6, a4=5\"; id \"STS(v5.0) Bishop vs Knight.005\";\n" +
            "1r1r2n1/1pb3pk/p1p3bp/4Np2/3P2P1/2N1RB1P/PP3PK1/4R3 w - - bm Nxg6; c0 \"Nxg6=10, Ne2=3, Rd1=2, d5=3\"; id \"STS(v5.0) Bishop vs Knight.006\";\n" +
            "1r2r1k1/2q3p1/3bp2p/p2nNp2/1ppP1P2/2P3Q1/PP1RN1PP/R5K1 b - - bm Bxe5; c0 \"Bxe5=10, Rb5=7, Rb7=6, a4=6\"; id \"STS(v5.0) Bishop vs Knight.007\";\n" +
            "1r2rbk1/1bqn1pp1/pp1p2np/3N1p2/2P1P3/PP2B1PB/4NQKP/2RR4 b - - bm Bxd5; c0 \"Bxd5=10, Qc6=6, Qc8=6, Qd8=3\"; id \"STS(v5.0) Bishop vs Knight.008\";\n" +
            "1r3bk1/1q2r1p1/pp1p3p/2nP2p1/3BP3/PP3QP1/4N1KP/2RR4 w - - bm Bxc5; c0 \"Bxc5=10, Bg1=5, Rc2=5, Re1=5\"; id \"STS(v5.0) Bishop vs Knight.009\";\n" +
            "1r4k1/2qnbpp1/r3p2p/3pP2b/1p1N4/3P1NP1/nBPQ1PBP/R3R1K1 b - - bm Bxf3; c0 \"Bxf3=10, Raa8=5, Rba8=6, Rbb6=5\"; id \"STS(v5.0) Bishop vs Knight.010\";\n" +
            "1r4k1/6pp/2b3n1/Nnq1p3/4Pp2/3B2PP/4QP1K/2B1R3 w - - bm Nxc6; c0 \"Nxc6=10, Bb2=2, Bc4+=3, Qa2+=6\"; id \"STS(v5.0) Bishop vs Knight.011\";\n" +
            "1rr4k/5p1p/4b1p1/pp1Nb3/4P3/PP1N1P2/K5RP/3R4 b - - bm Bxd5; c0 \"Bxd5=10, Bc3=4, Bd4=4, Bg7=6\"; id \"STS(v5.0) Bishop vs Knight.012\";\n" +
            "2b1rr2/4q1pk/p2ppb1p/1p2n3/4PNB1/P1N4Q/1PP3PP/R4R1K b - - bm Nxg4; c0 \"Nxg4=10, Bd7=4, Bg5=4, Qd7=3\"; id \"STS(v5.0) Bishop vs Knight.013\";\n" +
            "2b1rr2/4q1pk/p2ppb1p/1p5P/4PNQ1/P1N5/1PP3P1/R4R1K b - - bm Bxc3; c0 \"Bxc3=10, Bd4=4, Qc7=1, Rf7=1\"; id \"STS(v5.0) Bishop vs Knight.014\";\n" +
            "2b3k1/p3qpbp/2p3p1/1pn1p3/4P3/PBP1NN1P/1PQ2PP1/6K1 b - - bm Nxb3; c0 \"Nxb3=10, Qd6=6, a5=7, h6=6\"; id \"STS(v5.0) Bishop vs Knight.015\";\n" +
            "2k5/1p1nbqpp/pPprp1b1/P1p1p3/2P1P2N/4BP1P/1Q1N2P1/3R2K1 w - - bm Nxg6; c0 \"Nxg6=10, Bf2=7, Rb1=7, g3=7\"; id \"STS(v5.0) Bishop vs Knight.016\";\n" +
            "2k5/p7/1pp3r1/6b1/3P1p1p/P4NrP/1PP2R2/1K1R4 w - - bm Nxg5; c0 \"Nxg5=10, Rdf1=4, Re1=6, Rh1=6\"; id \"STS(v5.0) Bishop vs Knight.017\";\n" +
            "2kr1b1r/p1qn3p/Ppp3p1/4np2/8/2NBBP2/1PP3PP/RQ2R1K1 b - - bm Nxd3; c0 \"Nxd3=10, Bc5=4, Bd6=2, h5=2\"; id \"STS(v5.0) Bishop vs Knight.018\";\n" +
            "2kr3b/1pr2p1p/p1n1p1p1/2NpPn2/1P1P4/3RBP1B/1P2KP1P/6R1 w - - bm Bxf5; c0 \"Bxf5=10, Ra1=6, Rgd1=6, f4=5\"; id \"STS(v5.0) Bishop vs Knight.019\";\n" +
            "2krr3/pp1qbp2/4b3/1P2P2p/P1pPB1pN/4B1Q1/5PPP/3R2K1 b - - bm Bxh4; c0 \"Bxh4=10, c3=4\"; id \"STS(v5.0) Bishop vs Knight.020\";\n" +
            "2n2rk1/p2b2bp/3p2p1/q1pPp2n/P1P1P3/1QN5/3BBNPP/1R4K1 w - - bm Bxh5; c0 \"Bxh5=10, Qa2=5, Qb8=7, Qd1=5\"; id \"STS(v5.0) Bishop vs Knight.021\";\n" +
            "2q3k1/1b4r1/p2np2p/QnBpN3/3P1pPp/1P1BP3/8/R5K1 w - - bm Bxb5; c0 \"Bxb5=10, Qb6=1, Rf1=3, exf4=2\"; id \"STS(v5.0) Bishop vs Knight.022\";\n" +
            "2r1r1k1/1q1bp1bp/3p2p1/1pnP4/3NB3/1P2BP2/3Q2PP/1RR3K1 b - - bm Nxe4; c0 \"Nxe4=10, Rc7=1\"; id \"STS(v5.0) Bishop vs Knight.023\";\n" +
            "2r1r1k1/pp1q1pbp/2npbnp1/4p1N1/1P2P3/2P2N1P/P4PP1/R1BQRBK1 w - - bm Nxe6; c0 \"Nxe6=10, Bb2=4, Bd2=3, a3=4\"; id \"STS(v5.0) Bishop vs Knight.024\";\n" +
            "2r2nk1/1b3ppp/p2q1n2/1p1p4/3B1P1Q/1P1NP1P1/P5BP/2R3K1 w - - bm Bxf6; c0 \"Bxf6=10, Bc5=2, Rxc8=7, f5=4\"; id \"STS(v5.0) Bishop vs Knight.025\";\n" +
            "2r2rk1/4bp2/p6p/1p1R4/3nn1p1/4P1B1/PP1NBPPP/4R1K1 b - - bm Nxe2+; c0 \"Nxe2+=10, Nc2=4, Rc1=6\"; id \"STS(v5.0) Bishop vs Knight.026\";\n" +
            "2r2rk1/p2qbppp/5n2/3p1N2/1p2n3/1NP1BQ2/PP3PPP/R4RK1 w - - bm Nxe7+; c0 \"Nxe7+=10, Bd4=2, Rfe1=2, cxb4=4\"; id \"STS(v5.0) Bishop vs Knight.027\";\n" +
            "2r5/2q2pk1/3n1b1p/3Pp1p1/b1B1P1N1/P4N2/5PPP/2Q2RK1 w - - bm Nxf6; c0 \"Nxf6=10\"; id \"STS(v5.0) Bishop vs Knight.028\";\n" +
            "2r5/3n2k1/5p2/Pp2nB1r/1B1pP2P/P1pP2P1/6K1/3R2R1 w - - bm Bxd7; c0 \"Bxd7=10, Be6=5, Bh3=5, Rdf1=3\"; id \"STS(v5.0) Bishop vs Knight.029\";\n" +
            "2rq1rk1/1b2bppp/p3p3/n7/3P4/1BBQ1N1P/P4PP1/R3R1K1 b - - bm Nxb3; c0 \"Nxb3=10, Bf6=3, Bxf3=2, Qc7=2\"; id \"STS(v5.0) Bishop vs Knight.030\";\n" +
            "2rq1rk1/1p1npp1p/p2pb1p1/3N4/3QP3/1BP2P2/PP4PP/R4R1K b - - bm Bxd5; c0 \"Bxd5=10, Nc5=6, a5=5, b5=5\"; id \"STS(v5.0) Bishop vs Knight.031\";\n" +
            "2rq1rk1/1p1nppbp/p2pn1p1/6B1/2P1P3/1PN1Q1PP/P4PB1/2R2RK1 b - - bm Nxg5; c0 \"Nxg5=10, Bd4=1, Bxc3=1, Re8=4\"; id \"STS(v5.0) Bishop vs Knight.032\";\n" +
            "2rq1rk1/3bppbp/p4np1/n3Q3/Np6/1B3N1P/PPP2PP1/R1BR2K1 b - - bm Nxb3; c0 \"Nxb3=10, Nc4=7, Qc7=7, e6=7\"; id \"STS(v5.0) Bishop vs Knight.033\";\n" +
            "2rq1rk1/4bppp/p1p1pn2/1p1b4/3P4/2NB1NQ1/PPP2PPP/1K1RR3 b - - bm Bxf3; c0 \"Bxf3=10, Bd6=3, Nh5=3, a5=2\"; id \"STS(v5.0) Bishop vs Knight.034\";\n" +
            "2rq1rk1/pp1bppbp/3p1np1/8/2nNP3/1BN1BP2/PPP2QPP/R4RK1 w - - bm Bxc4; c0 \"Bxc4=10, Bc1=5, Nd1=7, Nde2=7\"; id \"STS(v5.0) Bishop vs Knight.035\";\n" +
            "2rqr1k1/1b3p1p/pn1p1bpB/2p5/3P4/1P3N1P/P2QBPP1/2RR2K1 b - - bm Bxf3; c0 \"Bxf3=10, Nd7=5, Qc7=6, cxd4=6\"; id \"STS(v5.0) Bishop vs Knight.036\";\n" +
            "2rqr1k1/1b3ppp/3b2n1/p2pN3/1p1P1B2/3BPP1P/P2Q2P1/1R1R2K1 b - - bm Nxf4; c0 \"Nxf4=10, Bxe5=4, Nxe5=2, Rc3=2\"; id \"STS(v5.0) Bishop vs Knight.037\";\n" +
            "2rr2k1/ppq1pp1p/2npbnpB/8/2PNP3/1QP2P2/P3B1PP/R4RK1 w - - bm Nxe6; c0 \"Nxe6=10, Qa4=4, Rab1=3, Rfb1=4\"; id \"STS(v5.0) Bishop vs Knight.038\";\n" +
            "2rr4/p3q1pk/bn2p2p/2b1Pp2/1pN4P/1P4Q1/P2BBPP1/2RR2K1 b - - bm Bxc4; c0 \"Bxc4=10, Bd4=4, Rc7=3, Rd4=6\"; id \"STS(v5.0) Bishop vs Knight.039\";\n" +
            "3b1k2/1b3p1p/pP4p1/3p4/1p1PnBP1/1K3B2/PP2N2P/8 w - - bm Bxe4; c0 \"Bxe4=10, Be3=2, h3=2\"; id \"STS(v5.0) Bishop vs Knight.040\";\n" +
            "3qr2k/p5r1/1pRp1n1b/1P1Pp2p/1P3p1P/2N1nP1Q/1R3BP1/5BK1 w - - bm Bxe3; c0 \"Bxe3=10\"; id \"STS(v5.0) Bishop vs Knight.041\";\n" +
            "3r1rk1/1b2p1bp/pp6/4Pp1n/B2n4/2NNBP1p/PP3KPP/3RR3 w - - bm Bxd4; c0 \"Bxd4=10, Bb3+=4, Ne2=6, gxh3=3\"; id \"STS(v5.0) Bishop vs Knight.042\";\n" +
            "3r2k1/1b3ppp/1p2pq2/p7/2P5/1Pn1QNP1/4PPBP/4R1K1 b - - bm Bxf3; c0 \"Bxf3=10, Rd6=6, a4=7, b5=6\"; id \"STS(v5.0) Bishop vs Knight.043\";\n" +
            "3r2k1/5p1p/p2qn1p1/P3N1r1/1P1pb1B1/2nN3P/5PPQ/2R1R2K b - - bm Bxd3; c0 \"Bxd3=10\"; id \"STS(v5.0) Bishop vs Knight.044\";\n" +
            "3r2k1/pr2bpp1/6bp/3nN3/2R3P1/4B2P/P3BP2/3R2K1 b - - bm Nxe3; c0 \"Nxe3=10, Bf6=7, Kh7=7, Nb6=8\"; id \"STS(v5.0) Bishop vs Knight.045\";\n" +
            "3rk2r/1p2bp2/p1n1q2p/3pPbp1/3B4/2P3Q1/BN3PPP/R3R1K1 b k - bm Nxd4; c0 \"Nxd4=10, O-O=3, Rd7=3, h5=2\"; id \"STS(v5.0) Bishop vs Knight.046\";\n" +
            "3rr1k1/p1p1p1bp/2p2pp1/3bP3/5P2/BPP2N2/P5PP/4RRK1 b - - bm Bxf3; c0 \"Bxf3=10\"; id \"STS(v5.0) Bishop vs Knight.047\";\n" +
            "3rr3/p3b1kp/2p2pp1/1q1npb2/4N1PB/1NP2Q2/PP3P1P/R2R2K1 b - - bm Bxe4; c0 \"Bxe4=10, Bc8=6, Bd7=4, Bxg4=6\"; id \"STS(v5.0) Bishop vs Knight.048\";\n" +
            "4qr1k/2bn1p1p/2N1p3/p2nP3/NpRQ1B2/1P4PP/P6K/8 b - - bm Nxf4; c0 \"Nxf4=10, Qc8=2, Rg8=2, h5=1\"; id \"STS(v5.0) Bishop vs Knight.049\";\n" +
            "4r1k1/p4bp1/1p1p1p2/1P1N2n1/4P1P1/4qP1P/P4RQ1/5BK1 b - - bm Bxd5; c0 \"Bxd5=10, Qc5=2, Qd4=6, Qe1=5\"; id \"STS(v5.0) Bishop vs Knight.050\";\n" +
            "4r1k1/p4p1p/1p2q1pB/1Q2Pn2/P3B2P/5P2/bR3P2/6K1 w - - bm Bxf5; c0 \"Bxf5=10, Bf4=7, Rxa2=7, a5=7\"; id \"STS(v5.0) Bishop vs Knight.051\";\n" +
            "4r1k1/pb6/2pbpp1B/2N2p2/3P4/1P6/P4KPP/R7 b - - bm Bxc5; c0 \"Bxc5=10, Ba8=2, Re7=5\"; id \"STS(v5.0) Bishop vs Knight.052\";\n" +
            "4r3/1p2rpk1/p1p1bqp1/2QnN1bp/1B1PB3/P6P/1P2RPP1/4R1K1 b - - bm Nxb4; c0 \"Nxb4=10, Bf4=4, Bh4=4, h4=5\"; id \"STS(v5.0) Bishop vs Knight.053\";\n" +
            "4r3/5pkp/2b1n1p1/3p3n/p1pP4/P1N1BP1B/1P3KPP/2R5 w - - bm Bxe6; c0 \"Bxe6=10, Ne2=7, g3=8, g4=5\"; id \"STS(v5.0) Bishop vs Knight.054\";\n" +
            "4rrk1/2q3pp/pp1p1bn1/2nR4/2P2B2/N4PP1/PP3Q1P/3R1BK1 b - - bm Nxf4; c0 \"Nxf4=10, Be7=6, Nb7=6\"; id \"STS(v5.0) Bishop vs Knight.055\";\n" +
            "5k2/p2b3r/1ppb1p2/1q1p1P2/1P1PrNpP/4P1P1/1RQ2BK1/2R5 b - - bm Bxf4; c0 \"Bxf4=10, Bb8=2\"; id \"STS(v5.0) Bishop vs Knight.056\";\n" +
            "5k2/p2q2p1/bp1p1p1p/3PnP1Q/1P1BP3/2P5/3N2P1/6K1 w - - bm Bxe5; c0 \"Bxe5=10, Kh2=7, Qd1=8, Qh3=7\"; id \"STS(v5.0) Bishop vs Knight.057\";\n" +
            "6k1/r2rbpp1/p1q2n2/1pp2PB1/5Q2/1PN1R2P/1P3P2/4R1K1 w - - bm Bxf6; c0 \"Bxf6=10, R3e2=2\"; id \"STS(v5.0) Bishop vs Knight.058\";\n" +
            "6k1/r6p/2pb1ppn/p1Np4/1P1PrPP1/P6P/2R5/3K1RB1 b - - bm Bxc5; c0 \"Bxc5=10, Re8=1, Ree7=5\"; id \"STS(v5.0) Bishop vs Knight.059\";\n" +
            "6n1/p6k/2R5/2p1rp2/p1P1n3/P3PB1P/5PP1/6K1 w - - bm Bxe4; c0 \"Bxe4=10, Bd1=1, Be2=4, Ra6=1\"; id \"STS(v5.0) Bishop vs Knight.060\";\n" +
            "6r1/r2Nbnk1/2R3pp/p4p2/PpB2PP1/1P6/6K1/3R4 w - - bm Bxf7; c0 \"Bxf7=10, Be6=1, Re6=4, gxf5=3\"; id \"STS(v5.0) Bishop vs Knight.061\";\n" +
            "8/1R3pp1/p3rnk1/3pBb1p/3P3P/1B1P1PP1/5K2/8 w - - bm Bxf6; c0 \"Bxf6=10, Ba4=3, Ke2=4, Ke3=4\"; id \"STS(v5.0) Bishop vs Knight.062\";\n" +
            "8/5p1k/2p3pp/2p1p3/2P2nPP/2Q2P2/1P1B1K2/q7 w - - bm Bxf4; c0 \"Bxf4=10, Qa3=7, b3=7, h5=7\"; id \"STS(v5.0) Bishop vs Knight.063\";\n" +
            "8/6k1/4ppp1/r2Pn2p/bpq1PB1P/6P1/R4PK1/1QN5 w - - bm Bxe5; c0 \"Bxe5=10, Nb3=1, Rb2=1, dxe6=5\"; id \"STS(v5.0) Bishop vs Knight.064\";\n" +
            "8/r2b1nk1/Np1qp2p/pP1pNrpP/Pp1P4/3Q1PP1/2R3K1/7R w - - bm Nxd7; c0 \"Nxd7=10\"; id \"STS(v5.0) Bishop vs Knight.065\";\n" +
            "q1r1r1k1/p2nppbp/1pnpb1p1/6N1/1PP1P3/P1N1Q2P/4BPPB/1R1R2K1 w - - bm Nxe6; c0 \"Nxe6=10\"; id \"STS(v5.0) Bishop vs Knight.066\";\n" +
            "q5k1/1pbb2pn/2ppNr1p/p3rP2/P1P1pNB1/1P5P/5PP1/1RQR2K1 b - - bm Bxe6; c0 \"Bxe6=10, Qb8=6, Qc8=5, Rfxe6=6\"; id \"STS(v5.0) Bishop vs Knight.067\";\n" +
            "r1b1r1k1/p1qn1ppp/2pb1n2/4pN2/Np2P3/7P/PPQ1BPP1/R1B2RK1 w - - bm Nxd6; c0 \"Nxd6=10, Be3=1, Rd1=1\"; id \"STS(v5.0) Bishop vs Knight.068\";\n" +
            "r1b1r1k1/ppq2ppp/2n5/3p4/P1pPn3/B1P1PN2/2B2PPP/R2Q1RK1 w - - bm Bxe4; c0 \"Bxe4=10, Bb2=7, Qb1=6, Qe1=4\"; id \"STS(v5.0) Bishop vs Knight.069\";\n" +
            "r1b2rk1/p3qppp/1pn5/2b5/3pN3/5NP1/PP2PPBP/R2Q1RK1 w - - bm Nxc5; c0 \"Nxc5=10, Ne1=1, Nfd2=1, Qd3=1\"; id \"STS(v5.0) Bishop vs Knight.070\";\n" +
            "r1bq2k1/pp3rbp/2n1p1p1/1BBpPn2/8/2N2N1P/PP3PP1/R2Q1RK1 w - - bm Bxc6; c0 \"Bxc6=10, Qe1=3, Qe2=3, Re1=1\"; id \"STS(v5.0) Bishop vs Knight.071\";\n" +
            "r1br4/pp3pk1/2p2npp/2n1N3/P2q4/2NB2QP/1PP2PP1/R3R1K1 b - - bm Nxd3; c0 \"Nxd3=10, Nh5=1, g5=5, h5=7\"; id \"STS(v5.0) Bishop vs Knight.072\";\n" +
            "r1r3k1/1nqb1ppp/3p4/pp1Pb3/1P2Pp2/R1PB1N1P/Q4PP1/1N2R1K1 w - - bm Nxe5; c0 \"Nxe5=10, Be2=2, Bf1=2, Rd1=3\"; id \"STS(v5.0) Bishop vs Knight.073\";\n" +
            "r1r3k1/1p1b1p2/1q1p3p/1NpPb1pB/PpP1Pp2/1P6/R4PPP/1Q1R3K b - - bm Bxb5; c0 \"Bxb5=10\"; id \"STS(v5.0) Bishop vs Knight.074\";\n" +
            "r1r4k/pb1nq1pp/1p1bp3/1N1p1p2/3Pn3/1P1NB1P1/PQ2PPBP/R4RK1 w - - bm Nxd6; c0 \"Nxd6=10, Rac1=7, Rfc1=7, a4=7\"; id \"STS(v5.0) Bishop vs Knight.075\";\n" +
            "r2q1rk1/2pb2b1/1p1p2pp/p1nP1p1n/2P5/2N1B1P1/PPQ1B2P/R3NRK1 w - - bm Bxh5; c0 \"Bxh5=10\"; id \"STS(v5.0) Bishop vs Knight.076\";\n" +
            "r2q1rk1/pb3pbp/1pn1p1p1/2pnN3/2N2B2/3P2P1/PPP2PBP/R2QR1K1 b - - bm Nxf4; c0 \"Nxf4=10, Nce7=2, Nd4=2, Rc8=2\"; id \"STS(v5.0) Bishop vs Knight.077\";\n" +
            "r2q1rk1/pppb1ppp/1b1p2n1/3Pp3/2N1P1n1/2PB1N2/PP3PPP/R1BQR1K1 w - - bm Nxb6; c0 \"Nxb6=10, Be3=4, Re2=1, Rf1=6\"; id \"STS(v5.0) Bishop vs Knight.078\";\n" +
            "r2r2k1/1p2qppp/2n1bn2/p2Bp1N1/2P5/R5P1/1P1NPP1P/1Q1R2K1 w - - bm Nxe6; c0 \"Nxe6=10, Bxc6=2, Bxe6=3\"; id \"STS(v5.0) Bishop vs Knight.079\";\n" +
            "r2r2k1/1pq2pp1/p1b1p2p/3n4/8/1QB3P1/PP2PPBP/R3R1K1 b - - bm Nxc3; c0 \"Nxc3=10, Qd7=7, Rac8=8, a5=7\"; id \"STS(v5.0) Bishop vs Knight.080\";\n" +
            "r2r2k1/ppp2pb1/2n1p1pp/1B2P3/3PNn1q/2Q1BP2/PP3P1P/2R1K2R w K - bm Bxf4; c0 \"Bxf4=10, Bc4=7, Bxc6=6, Rd1=6\"; id \"STS(v5.0) Bishop vs Knight.081\";\n" +
            "r3k2r/ppqbnpb1/n1p1p2p/P2pP1p1/3P4/1P1BBN1P/1NPQ1PP1/R3K2R w KQkq - bm Bxa6; c0 \"Bxa6=10\"; id \"STS(v5.0) Bishop vs Knight.082\";\n" +
            "r3kb1B/1p1b3p/pqnpp1p1/7n/8/2NRp3/PPP1B3/1K1RQ3 w q - bm Bxh5; c0 \"Bxh5=10, Ne4=2\"; id \"STS(v5.0) Bishop vs Knight.083\";\n" +
            "r3qrk1/1pp2bp1/p1n1p2p/2Pp1pbP/1P1P4/P1NBPN2/2Q2PP1/2R1K2R w K - bm Nxg5; c0 \"Nxg5=10, Ne2=7, Rd1=7, b5=2\"; id \"STS(v5.0) Bishop vs Knight.084\";\n" +
            "r3r1k1/p1q3bp/2p1bpp1/2nnp3/2N1N3/2PB2B1/PPQ2PPP/R3R1K1 b - - bm Nxd3; c0 \"Nxd3=10, Bf8=1, Nb7=1, Nxe4=2\"; id \"STS(v5.0) Bishop vs Knight.085\";\n" +
            "r3r1k1/pp3pbp/2p3p1/P3p3/1n2P1n1/2N1B3/1PPR1PPP/R3N1K1 b - - bm Nxe3; c0 \"Nxe3=10, Bf8=4, Na6=4, f6=3\"; id \"STS(v5.0) Bishop vs Knight.086\";\n" +
            "r3r1k1/ppq2pb1/2p1b1p1/P3n2p/2PN1B2/7P/1P1QBPP1/R2R2K1 w - - bm Nxe6; c0 \"Nxe6=10, Ra3=8, Rac1=7, a6=7\"; id \"STS(v5.0) Bishop vs Knight.087\";\n" +
            "r3r2k/6p1/q3pp1p/1b1pn2P/3Q2B1/1P6/1BP2PP1/1K1RR3 b - - bm Nxg4; c0 \"Nxg4=10, Nc6=1, Nf7=1, Qa2+=5\"; id \"STS(v5.0) Bishop vs Knight.088\";\n" +
            "r3rbk1/1p3pp1/1qp1b2p/p1Nn4/2Rp4/P2P1BP1/1P1BPP1P/2Q1R1K1 b - - bm Bxc5; c0 \"Bxc5=10, Bd6=4, Be7=5, Nc7=3\"; id \"STS(v5.0) Bishop vs Knight.089\";\n" +
            "r4r1k/1p2b1pp/pBq3n1/2PNp3/2N1Ppn1/1Q4P1/PP1R3P/5RK1 w - - bm Nxe7; c0 \"Nxe7=10, Nd6=3, Qa3=3, Rc2=4\"; id \"STS(v5.0) Bishop vs Knight.090\";\n" +
            "r4r2/p1n1q1bk/6pp/1pp2b2/2P1p3/P3N1P1/R2QPPBP/2BR2K1 w - - bm Nxf5; c0 \"Nxf5=10, Qa5=6, Qc2=2, Qd6=4\"; id \"STS(v5.0) Bishop vs Knight.091\";\n" +
            "r4r2/p3pp1k/1nR1b2p/5Np1/4P3/5NP1/Pb3PBP/5RK1 b - - bm Bxf5; c0 \"Bxf5=10, Rfc8=1\"; id \"STS(v5.0) Bishop vs Knight.092\";\n" +
            "r4rk1/1bq2pp1/1p1bpn1p/p2nN3/P1BP4/5NP1/1P1BQPKP/R3R3 b - - bm Bxe5; c0 \"Bxe5=10, Nb4=2, Ne7=3, Qe7=2\"; id \"STS(v5.0) Bishop vs Knight.093\";\n" +
            "r4rk1/1p2bppp/p2p1nn1/3P4/2PN1B2/8/PP2B1PP/R4R1K b - - bm Nxf4; c0 \"Nxf4=10, Ne4=1, Rae8=1\"; id \"STS(v5.0) Bishop vs Knight.094\";\n" +
            "r4rk1/1ppbq1bp/1n4p1/p1NPpp2/1n6/1PN1P1PP/PB3PB1/R2Q1RK1 w - - bm Nxd7; c0 \"Nxd7=10, N5a4=5, Nxb7=5, d6=4\"; id \"STS(v5.0) Bishop vs Knight.095\";\n" +
            "r4rk1/ppq1ppbp/1np1P1p1/2Nb4/Pn6/3B1N1P/1PP1QPP1/R1B1R1K1 b - - bm Nxd3; c0 \"Nxd3=10, Bxf3=6, Qd6=6, f5=6\"; id \"STS(v5.0) Bishop vs Knight.096\";\n" +
            "r5k1/pp1n1rpp/2pb1q2/2N5/1P3B2/P2P2P1/3Q1PKP/1R2R3 b - - bm Bxc5; c0 \"Bxc5=10, Bxf4=4, Nxc5=5, Qd4=7\"; id \"STS(v5.0) Bishop vs Knight.097\";\n" +
            "r5k1/pp2q1bp/n2p2p1/2pPpr2/2P1Nn2/PQ3N1P/1P1B1PP1/1R2R1K1 w - - bm Bxf4; c0 \"Bxf4=10, Kh2=7, Rbd1=6, g4=6\"; id \"STS(v5.0) Bishop vs Knight.098\";\n" +
            "r6r/4bkpp/2p1np2/p1p5/2P1NB2/6P1/PP2P2P/3R1R1K b - - bm Nxf4; c0 \"Nxf4=10\"; id \"STS(v5.0) Bishop vs Knight.099\";\n" +
            "rnq1k2r/p3ppbp/1p2bnp1/2p3N1/4P3/3BB2P/P1QN1PP1/1R3RK1 w kq - bm Nxe6; c0 \"Nxe6=10, Ndf3=4, Rfd1=4, f4=4\"; id \"STS(v5.0) Bishop vs Knight.100\";" +
            "";

    private static final String[] splitUpPositions = positions.split("\n");

}

    