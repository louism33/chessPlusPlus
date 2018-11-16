package javacode.chessengine.main;

import javacode.chessengine.search.Engine;
import javacode.chessprogram.check.CheckChecker;
import javacode.chessprogram.chess.Chessboard;
import javacode.chessprogram.chess.Move;
import javacode.chessprogram.graphicsandui.Art;
import javacode.chessprogram.moveGeneration.MoveGeneratorMaster;
import javacode.chessprogram.moveMaking.MoveOrganiser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StandAlone {

    private static int totalMoves = 1;
    private static long timeLimit = 20000;

    public static void main(String[] args) throws IOException {
        InputStreamReader stdin;

        stdin = new InputStreamReader(System.in);

        Move[] moveArray;
        Chessboard board = new Chessboard();
        String command, prompt;
        Move move;
        Engine engine = new Engine();

        while(true) {

            while (true) {

                if (board.isWhiteTurn()){
                    prompt = "White"; 
                }
                else {
                    prompt = "Black";
                }

                System.out.println("\nPosition ("+prompt+" to move):\n" + Art.boardArt(board));

                List<Move> moves = MoveGeneratorMaster.generateLegalMoves(board, board.isWhiteTurn());
                moveArray = moves.toArray(new Move[0]);

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

                label:
                while (true) {
                    System.out.print(prompt + " move (or \"go\" or \"quit\")> ");
                    command = readCommand(stdin);
                    System.out.println("This is move number " + totalMoves + ".");

                    switch (command) {
                        case "go":
                            move = engine.searchFixedTime(board, timeLimit);
                            break label;
                        case "quit":
                            System.out.println("QUIT.\n");
                            System.exit(1);
                        default:
                            move = null;
                            for (Move aMoveArray : moveArray) {
                                if (command.equals(aMoveArray.toString())) {
                                    move = aMoveArray;
                                    break;
                                }
                            }
                            if (move != null) break label;
                            System.out.println("\"" + command + "\" is not a legal move");
                            break;
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
    private static String readCommand(InputStreamReader stdin) throws IOException {
        int MAX = 100;
        int len = 0;
        char[] cbuf = new char[MAX];
        //len = stdin.read(cbuf, 0, MAX);
        for(int i=0; i<cbuf.length; i++){

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