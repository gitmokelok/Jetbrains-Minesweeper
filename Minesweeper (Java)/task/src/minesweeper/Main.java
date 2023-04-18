package minesweeper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final Random random = new Random(1);
    public static void main(String[] args) {
        // write your code here
        int numberOfMines = 0;
        int numberOfColumns = 9;
        int numberOfRows = 9;

        System.out.print("How many mines do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        numberOfMines = scanner.nextInt();

        ArrayList<Coordinate> minesList = new ArrayList<>();

        for (int i =0; i < numberOfMines; i++) {
            Coordinate newCoord = generateMineCoordinate(numberOfRows, numberOfColumns);
            while (true) {
                if (minesList.contains(newCoord)) {
                    newCoord = generateMineCoordinate(numberOfRows, numberOfColumns);
                } else {
                    minesList.add(newCoord);
                    break;
                }
            }
        }

        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (minesList.contains(currentCoordinate)) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

    }


    public static Coordinate generateMineCoordinate(int numberOfRows, int numbersOfColumns) {
        return new Coordinate(random.nextInt(0, numberOfRows), random.nextInt(0, numbersOfColumns));
    }
}
