package theater.bo;

public class Seat {
    private int row;
    private int column;
    private boolean isAvailable;

    public Seat(int row, int column, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
    }

    public int getRow() {
        return row;
    }


    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

