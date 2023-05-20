package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    static final Random random = new Random(1);
    public int numberOfRows;
    public int numberOfColumns;

    public String[][] fields;
    public ArrayList<Coordinate> coordinates = new ArrayList<>();
    public ArrayList<Coordinate> minesList = new ArrayList<>();
    public ArrayList<Coordinate> exploredFields = new ArrayList<>();

    public Board(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initializeBoard();
    }

    private void initializeBoard_old() {
        fields = new String[numberOfRows][numberOfColumns];
        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                fields[i][j] = ".";
            }
        }
    }

    private void initializeBoard() {
        //fields = new String[numberOfRows][numberOfColumns];
        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                coordinate.coordinateState = '.';
                coordinate.isMine = false;
                coordinate.hintNumericValue = 0;
                coordinates.add(coordinate);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" |123456789|\n");
        sb.append("-|---------|\n");
        for (int i = 0; i< numberOfRows; i++) {
            sb.append(i+1);
            sb.append("|");
            for (int j = 0; j < numberOfColumns; j++) {
                //sb.append(fields[i][j]);
                Coordinate c = getCoordinateByRowAndColumn(i,j);
                sb.append(c.coordinateState);
            }
            sb.append("|\n");
        }
        sb.append("-|---------|\n");
        return sb.toString();
    }


    public String toStringHiddenMines() {
        StringBuilder sb = new StringBuilder();
        sb.append(" |123456789|\n");
        sb.append("-|---------|\n");
        for (int i = 0; i< numberOfRows; i++) {
            sb.append(i+1);
            sb.append("|");
            for (int j = 0; j < numberOfColumns; j++) {
                if ("X".equals(fields[i][j])) {
                    sb.append(".");
                } else {
                    sb.append(fields[i][j]);
                }
            }
            sb.append("|\n");
        }
        sb.append("-|---------|\n");
        return sb.toString();
    }

    public String toStringWhileActiveGame() {
        StringBuilder sb = new StringBuilder();
        sb.append(" |123456789|\n");
        sb.append("-|---------|\n");
        for (int i = 0; i< numberOfRows; i++) {
            sb.append(i+1);
            sb.append("|");
            for (int j = 0; j < numberOfColumns; j++) {
                Coordinate c = getCoordinateByRowAndColumn(i, j);
                if (!exploredFields.contains(c)) {
                    sb.append(".");
                } else {
                    sb.append(c.coordinateState);
                }
            }
            sb.append("|\n");
        }
        sb.append("-|---------|\n");
        return sb.toString();
    }

//    public void placeMines(int numberOfMines) {
//        for (int i =0; i < numberOfMines; i++) {
//            Coordinate newCoord = generateMineCoordinate();
//            while (true) {
//                if (minesList.contains(newCoord)) {
//                    newCoord = generateMineCoordinate();
//                } else {
//                    minesList.add(newCoord);
//                    fields[newCoord.rowNumber][newCoord.columnNumber] = "X";
//                    break;
//                }
//            }
//        }
//    }

    public void placeMines(int numberOfMines) {
        for (int i =0; i < numberOfMines; i++) {

            Coordinate coordinate = getRandomCoordinate();
            while (true) {
                if (minesList.contains(coordinate) || coordinate.coordinateState == '/') {
                    coordinate = getRandomCoordinate();
                } else {
                    coordinate.isMine = true;
                    minesList.add(coordinate);
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
                        if (minesList.contains(new Coordinate(targetRow, targetColumn))) {
                            continue;
                        }
                        switch (targetValue) {
                            case "." -> fields[targetRow][targetColumn] = "1";
                            case "1", "2", "3", "4", "5","6","7","8" -> fields[targetRow][targetColumn] = String.valueOf(Integer.parseInt(targetValue) + 1);
                        }
                    }
                }
            }
        }
    }
    public void revealMines(){
        for (Coordinate bomb : minesList) {
            if (bomb.coordinateState == '.'){
                bomb.coordinateState = 'X';
            }
        }
    }
    public void setHintsAroundBombs() {
        for (Coordinate bomb : minesList) {
            int rowNum = bomb.rowNumber;
            int columnNum = bomb.columnNumber;
            for (int i = 0; i < 3; i++) {
                int targetRow = rowNum + i - 1;
                for (int j = 0; j < 3; j++) {
                    int targetColumn = columnNum + j - 1;
                    if (targetRow >= 0 && targetColumn >= 0 && targetRow < numberOfRows && targetColumn < numberOfColumns) {
                        Coordinate c = getCoordinateByRowAndColumn(targetRow,targetColumn);
                        if (c.isMine) {
                            continue;
                        }
                        switch (c.hintNumericValue) {
                            case 0 -> getCoordinateByRowAndColumn(targetRow, targetColumn).hintNumericValue = 1;
                            case 1, 2, 3, 4, 5,6,7,8 -> c.hintNumericValue++;

                        }
                    }
                }
            }
        }
    }
    private Coordinate generateMineCoordinate() {
        return new Coordinate(random.nextInt(0, numberOfRows), random.nextInt(0, numberOfColumns));
    }

    private Coordinate getRandomCoordinate() {
        return coordinates.get(random.nextInt(0,coordinates.size()));
    }

    
    public Coordinate getCoordinateByRowAndColumn(int row, int column) {
        for (Coordinate c : coordinates) {
            if (c.rowNumber == row && c.columnNumber == column) {
                return c;
            }
        }
        return null;
    }

    public boolean allMinesAreIdentified(){
        for (Coordinate c: minesList) {
            if (c.coordinateState != '*') {
                return false;
            }
        }
        return true;
    }

    public void revealUnexploredCells(){
        while (!allFreeCellsRevelead()) {
            for (Coordinate c : coordinates) {
                if (c.coordinateState == '/') {
                    ArrayList<Coordinate> neighbours = getCoordinateNeighbours(c);
                    for (Coordinate n : neighbours) {
                        if (!n.isMine && (n.coordinateState == '.' || n.coordinateState == '*')) {
                            n.coordinateState = n.hintNumericValue == 0 ? '/': Character.forDigit(n.hintNumericValue, 10);
                        }
                    }
                }
            }
        }


    }

    private ArrayList<Coordinate> getCoordinateNeighbours(Coordinate targetCoordinate) {
        ArrayList<Coordinate> neighbours = new ArrayList<>();
        int rowNum = targetCoordinate.rowNumber;
        int columnNum = targetCoordinate.columnNumber;

        for(int i = 0; i < 3; i++){
            Coordinate neighbour = getCoordinateByRowAndColumn(rowNum-1+i, columnNum-1);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
            neighbour = getCoordinateByRowAndColumn(rowNum-1+i, columnNum);
            if (neighbour != null && !neighbour.equals(targetCoordinate)) {
                neighbours.add(neighbour);
            }
            neighbour = getCoordinateByRowAndColumn(rowNum-1+i, columnNum+1);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private boolean allFreeCellsRevelead(){
        for (Coordinate c: coordinates){
            if (c.coordinateState == '/'){
                ArrayList<Coordinate> neighbours = getCoordinateNeighbours(c);
                for (Coordinate n : neighbours) {
                    if (!n.isMine && n.coordinateState == '.') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}




