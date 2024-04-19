public class ListIterator {
	private Node currentNode;

    public ListIterator(Node first){
        this.currentNode = first;
    }

    public boolean hasNext(){
        this.currentNode = this.currentNode.next;
        return this.currentNode != null;
    }

    public Piece getPiece(){
        return this.currentNode.getPiece();
    }
}
