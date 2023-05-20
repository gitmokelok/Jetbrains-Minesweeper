package minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int numberOfMines = 0;
        int numberOfColumns = 9;
        int numberOfRows = 9;
        int numberOfMarks = 0;
        boolean gameFirstMove = true;

        Board board = new Board(numberOfRows, numberOfColumns);

        System.out.print("How many mines do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        numberOfMines = Integer.parseInt(scanner.nextLine());




        System.out.print(board.toStringWhileActiveGame());

        while (true) {
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            String userInputLine = scanner.nextLine();

            int rowId = Integer.parseInt(userInputLine.split(" ")[1])-1;
            int columnId = Integer.parseInt(userInputLine.split(" ")[0])-1;
            String playerCommand = userInputLine.split(" ")[2];

            switch (playerCommand) {
                case "mine" -> {
                    Coordinate targetCoordinate = board.getCoordinateByRowAndColumn(rowId, columnId);
                    if (targetCoordinate.coordinateState == '.') {
                        targetCoordinate.coordinateState = '*';
                    } else if (targetCoordinate.coordinateState == '*') {
                        targetCoordinate.coordinateState = '.';
                    }



                    //numberOfMarks += 1;

                    if (board.allMinesAreIdentified()) {
                        System.out.println();
                        System.out.println(board);
                        System.out.println("Congratulations! You found all the mines!");
                        return;
                    }

                    System.out.println();
                    System.out.println(board);
                }
                case "free" -> {
                    Coordinate targetCoordinate = board.getCoordinateByRowAndColumn(rowId, columnId);


                    //if (targetCoordinate.coordinateState == '.') {

                        if (gameFirstMove) {
                            board.placeMines(numberOfMines);
                            board.setHintsAroundBombs();
                            gameFirstMove = false;
                        }
                    if (targetCoordinate.isMine && targetCoordinate.coordinateState == '.') {
                        board.revealMines();
                        System.out.println();
                        System.out.println(board);
                        System.out.println("You stepped on a mine and failed!");
                        return;
                    }
                    targetCoordinate.coordinateState = targetCoordinate.hintNumericValue == 0 ? '/' :  Character.forDigit(targetCoordinate.hintNumericValue, 10);
                    board.revealUnexploredCells();
                        //board.revealUnexploredCells();

                    System.out.println();
                    System.out.println(board);
                }
            }
        }
    }



}
