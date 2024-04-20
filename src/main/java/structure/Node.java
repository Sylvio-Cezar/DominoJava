package structure;

import game.Piece;

public class Node {
	private Piece piece;
    Node previous;
    Node next;

    public Node(Piece piece) {
        this.piece = piece;
    }

    public Node(Piece piece, Node previous) {
        this.piece = piece;
        this.previous = previous;
    }

    public Node(Piece piece, Node previous, Node next) {
        this.piece = piece;
        this.previous = previous;
        this.next = next;
    }

    public Piece getPiece() {
        return piece;
    }
}
