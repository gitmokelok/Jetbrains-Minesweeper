package minesweeper;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate {
    public int rowNumber;
    public int columnNumber;
    ///Use the following symbols to represent each cell's state:
    //
    //'.' as unexplored cells
    //
    //'/' as explored free cells without mines around it
    //
    //Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
    //
    //'X' as mines
    //
    //'*' as unexplored marked cells
    public char coordinateState;
    public int hintNumericValue = 0;
    public boolean isMine = false;

    ArrayList<Coordinate> neighbours = new ArrayList<>();

    public Coordinate(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return rowNumber == that.rowNumber && columnNumber == that.columnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, columnNumber);
    }

}
