public class Piece {
	private int firstValue;
    private int secondValue;
    private String color;

    public Piece(int firstValue, int secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.color = Colors.ANSI_RESET;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void flipPiece() {
        int temp = this.firstValue;
        this.firstValue = this.secondValue;
        this.secondValue = temp;
    }

    @Override
    public String toString() {
        return String.format("%s[%d|%d]%s", this.color, this.firstValue, this.secondValue, Colors.ANSI_RESET);
    }
}
