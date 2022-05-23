package BusinessLogic;

/**
 *  Represents the positions of the bishops.
 */
public class Position {
    private int row, column;

    /**
     * Creates a new Position object.
     * @param row is the row component of the position
     * @param column is the column component of the position
     */
    public Position(int row,int column){
        this.row=row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean equals(Position position){
        return this.row==position.getRow() && this.column== position.getColumn();
    }
}
