package BusinessLogic;

/**
 *  Represents the positions of the bishops.
 */
public class Position {
    private int row, column;

    /**
     * Creates a new Position object.
     *
     * @param row is the row component of the position
     * @param column is the column component of the position
     */
    public Position(int row,int column){
        this.row=row;
        this.column=column;
    }

    /**
     * Returns the row of the position.
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the position.
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the row of the position.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the column of the position.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    public boolean equals(Position position){
        return this.row==position.getRow() && this.column== position.getColumn();
    }
    @Override
    public String toString(){
        return String.format("%d, %d", row,column);
    }
}
