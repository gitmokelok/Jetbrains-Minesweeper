package minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int numberOfMines = 0;
        int numberOfColumns = 9;
        int numberOfRows = 9;
        int numberOfMarks = 0;

        Board board = new Board(numberOfRows, numberOfColumns);

        System.out.print("How many mines do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        numberOfMines = Integer.parseInt(scanner.nextLine());


        board.placeMines(numberOfMines);

        board.placeHints();

        System.out.print(board.toStringHiddenMines());

        while (true) {
            System.out.print("Set/delete mine marks (x and y coordinates): ");
            String userInputCoordinates = scanner.nextLine();

            int x = Integer.parseInt(userInputCoordinates.split(" ")[1])-1;
            int y = Integer.parseInt(userInputCoordinates.split(" ")[0])-1;

            String coordinateValueOnTheBoard = board.fields[x][y];
            switch (coordinateValueOnTheBoard) {
                case "1", "2", "3", "4", "5","6","7","8" -> System.out.println("There is a number here!");
                case ".", "X" -> {
                    board.fields[x][y] = "*";
                    numberOfMarks += 1;
                    System.out.print(board.toStringHiddenMines());
                }
                case "*" -> {
                    if (board.minesList.contains(new Coordinate(x, y))) {
                        board.fields[x][y] = "X";
                    } else {
                        board.fields[x][y] = ".";
                    }
                    numberOfMarks -= 1;
                    System.out.print(board.toStringHiddenMines());
                }
            }

            if (numberOfMarks == numberOfMines && !board.toString().contains("X")) {
                break;
            }

        }

        System.out.println("Congratulations! You found all the mines!");




    }



}
