import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    Deque<Player> players;
    Board gameBoard;

    TicTacToeGame() {
        initializeGame();
    }

    public void initializeGame() {

        // Creating Two players for simplicity
        // If we have more players we can initialize in constructor above
        // and give each player a playing piece using hashmap

        players = new ArrayDeque<>();
        PlayingPieceX xPiece = new PlayingPieceX();
        Player p1 = new Player("p1", xPiece);
        PlayingPieceO oPiece = new PlayingPieceO();
        Player p2 = new Player("p2", oPiece);
        players.add(p1);
        players.add(p2);
        // Initialize board to 3 x 3
        gameBoard = new Board(3);
    }

    public String startGame() {
        boolean noWinner = true;
        while (noWinner) {
            Player playerTurn = players.removeFirst();

            gameBoard.printBoard();
            // get free cells from board
            List<Pair> freeSpaces =  gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }
            //read the user input
            System.out.print("Player:" + playerTurn.name + " Enter row,column: ");
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            //place the piece
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow,inputColumn, playerTurn.playingPiece);
            if(!pieceAddedSuccessfully) {
                //player can not insert the piece into this cell, player has to choose another cell
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isWinner(inputRow, inputColumn, playerTurn.playingPiece.pieceType);
            if(winner) {
                return playerTurn.name;
            }
        }

        return "tie";
    }

    public boolean isWinner(int row, int column, PieceType pieceType) {

        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i< gameBoard.size;i++) {

            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != pieceType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != pieceType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}
