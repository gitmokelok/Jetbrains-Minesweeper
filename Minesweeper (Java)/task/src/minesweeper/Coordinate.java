package minesweeper;

import java.util.Objects;

public class Coordinate {
    public int rowNumber;
    public int columnNumber;

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
