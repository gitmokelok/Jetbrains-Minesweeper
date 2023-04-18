package minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int numberOfMines = 0;
        int numberOfColumns = 9;
        int numberOfRows = 9;

        Board board = new Board(numberOfRows, numberOfColumns);

        System.out.print("How many mines do you want on the field? ");
        try (Scanner scanner = new Scanner(System.in)) {
            numberOfMines = scanner.nextInt();
        }

        board.placeMines(numberOfMines);

        board.placeHints();

        System.out.print(board);




    }



}
