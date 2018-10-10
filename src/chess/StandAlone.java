package chess;

import Engine.Engine;
import check.CheckChecker;
import moveGeneration.MoveGeneratorMaster;
import moveMaking.MoveOrganiser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StandAlone {

    private static boolean logfile = false; // true if we're using a logfile

    public static Chessboard board;

    private static int totalMoves = 1;

    public static void main(String[] args) throws IOException {
        InputStreamReader stdin;

        stdin = new InputStreamReader(System.in);

        Move[] moveArray;
        board = new Chessboard();
        String command, prompt;
        Move move;
        Engine engine = new Engine();
//
//        System.out.println("Computer player: "+player.getName());

        while(true) {
//            player.newGame(5*60*1000, 0);


            while (true) {
//                b = player.getBoard();

                if (board.isWhiteTurn()) prompt = "White"; else prompt = "Black";

                System.out.println("\nPosition ("+prompt+" to move):\n" + Art.boardArt(board));

                List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
                moveArray = moves.toArray(new Move[moves.size()]);

                if (moveArray.length == 0) {
                    if (CheckChecker.boardInCheck(board, board.isWhiteTurn())){
                        System.out.println("Checkmate");
                    }
                    else {
                        System.out.println("Stalemate");
                    }
                    break;
                }

                System.out.println("Moves:");
                System.out.print("   ");
                for (int i=0; i<moveArray.length; i++) {
                    if ((i % 10) == 0 && i>0) System.out.print("\n   ");
                    System.out.print(moveArray[i]+", ");
                }

                System.out.println();
                System.out.println(moveArray.length +  " moves in total.");
                System.out.println();

                while(true) {
                    System.out.print(prompt + " move (or \"go\" or \"quit\")> ");
                    command = readCommand(stdin);
                    System.out.println("This is move number " + totalMoves+".");

                    if (command.equals("go")) {
//                        move = player.computeMove(1*60*1000, 0);
//                        System.out.println("Computer Moves: " + move);

                        move = engine.search(board, 1000);

                        break;
                    }

                    else if (command.equals("quit")) {
                        System.out.println("QUIT.\n");
                        System.exit(1);
                    }

                    else {
                        move = null;
                        for (int i=0; i<moveArray.length; i++) {
                            if (command.equals(moveArray[i].toString())) {
                                move = moveArray[i];
                                break;
                            }
                        }
                        if (move != null) break;
                        System.out.println("\""+command+"\" is not a legal move");
                    }
                }


                MoveOrganiser.makeMoveMaster(board, move);
                board.setWhiteTurn(!board.isWhiteTurn());
                totalMoves++;
                System.out.println(prompt + " made move "+move);
            }



            while(true) {
                System.out.print("Play again? (y/n):");
                command = readCommand(stdin);
                if (command.equals("n")) System.exit(1);
                if (command.equals("y")) {
                    totalMoves = 0;
                    break;
                }
            }
        }
    }
    static String readCommand(InputStreamReader stdin) throws IOException {
        final int MAX = 100;
        int len = 0;
        char[] cbuf = new char[MAX];
        //len = stdin.read(cbuf, 0, MAX);
        for(int i=0; i<cbuf.length; i++){
            if(logfile && !stdin.ready()) return "quit"; // file is done.

            cbuf[i] = (char)stdin.read();
            len++;
            if(cbuf[i] == '\n')
                break;
            if(cbuf[i] == -1){
                System.out.println("An error occurred reading input");
                System.exit(1);
            }
        }

		/*if (len == -1){
            System.out.println("An error occurred reading input");
            System.exit(1);
        }*/
        return new String(cbuf, 0, len).trim();  /* trim() removes \n in unix and \r\n in windows */
    }
}