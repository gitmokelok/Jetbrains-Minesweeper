package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    static final Random random = new Random(1);
    public int numberOfRows;
    public int numberOfColumns;

    public String[][] fields;

    public ArrayList<Coordinate> minesList = new ArrayList<>();

    public Board(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initializeBoard();
    }

    private void initializeBoard() {
        fields = new String[numberOfRows][numberOfColumns];
        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                fields[i][j] = ".";
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                sb.append(fields[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void placeMines(int numberOfMines) {
        for (int i =0; i < numberOfMines; i++) {
            Coordinate newCoord = generateMineCoordinate();
            while (true) {
                if (minesList.contains(newCoord)) {
                    newCoord = generateMineCoordinate();
                } else {
                    minesList.add(newCoord);
                    fields[newCoord.rowNumber][newCoord.columnNumber] = "X";
                    break;
                }
            }
        }
    }

    public void placeHints() {
        for (Coordinate bomb : minesList) {
            int rowNum = bomb.rowNumber;
            int columnNum = bomb.columnNumber;
            for (int i = 0; i < 3; i++) {
                int targetRow = rowNum + i - 1;
                for (int j = 0; j < 3; j++) {
                    int targetColumn = columnNum + j - 1;
                    if (targetRow >= 0 && targetColumn >= 0 && targetRow < numberOfRows && targetColumn < numberOfColumns) {
                        String targetValue = fields[targetRow][targetColumn];
                        switch (targetValue) {
                            case "." -> fields[targetRow][targetColumn] = "1";
                            case "1", "2", "3", "4", "5","6","7","8" -> fields[targetRow][targetColumn] = String.valueOf(Integer.parseInt(targetValue) + 1);
                        }
                    }
                }
            }
        }
    }

    private Coordinate generateMineCoordinate() {
        return new Coordinate(random.nextInt(0, numberOfRows), random.nextInt(0, numberOfColumns));
    }

}




