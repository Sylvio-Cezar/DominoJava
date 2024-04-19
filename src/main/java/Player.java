public class Player {
	private String name;
    private List pieces;

    public Player(String name) {
        this.name = name;
        this.pieces = new List();
    }

    public String getName() {
        return name;
    }

    public List getPieces() {
        return pieces;
    }
}
