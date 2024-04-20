public class ListIterator {
    private Node currentNode;
    private Node nextNode;

    public ListIterator(Node first) {
        this.currentNode = null;
        this.nextNode = first;
    }

    public boolean hasNext() {
        return this.nextNode != null;
    }

    public Piece getPiece() {
        if (this.currentNode == null) {
            this.currentNode = this.nextNode;
            this.nextNode = this.nextNode.next;
        } else {
            this.currentNode = this.currentNode.next;
            this.nextNode = this.nextNode.next;
        }
        return this.currentNode.getPiece();
    }

    public Piece next(){
        this.currentNode = this.nextNode;
        this.nextNode = this.nextNode.next;
        return this.currentNode.getPiece();
    }
}
