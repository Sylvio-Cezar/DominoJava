public class List {
	Node start;
    Node end;
    int listLength;

    public List() {
        this.listLength = 0;
    }

    public int getlistLength() {
        return listLength;
    }

    public boolean isEmpty(){
        return this.start == null;
    }

    public ListIterator getIterator(){
        return new ListIterator(this.start);
    }

    public Piece getStart(){
        return this.start.getPiece();
    }

    public Piece getEnd(){
        return this.end.getPiece();
    }

    public void insert(Piece piece){
        Node addedNode;

        if (this.isEmpty()){
            addedNode = new Node(piece);
            this.start = addedNode;
        } else {
            addedNode = new Node(piece, this.end);
            this.end.next = addedNode;
        }

        this.end = addedNode;
        this.listLength++;
    }

    public void insert(Piece piece, int position) throws IllegalArgumentException {
        if (position < 1 || position > this.listLength){
           throw new IllegalArgumentException("Valor inválido!");
        }
        Node addedNode;

        if (position == 1){
            addedNode = new Node(piece, null, this.start);
            this.start.previous = addedNode;
            this.start = addedNode;

        } else if (position == this.listLength) {
            addedNode = new Node(piece, this.end.previous, this.end);
            this.end.previous.next = addedNode;
            this.end.previous = addedNode;

        } else {
            int pos = 1;
            Node nodeInPosition = this.start;

            while (pos++ < position){
                nodeInPosition = nodeInPosition.next;
            }

            addedNode = new Node(piece, nodeInPosition.previous, nodeInPosition);
            nodeInPosition.previous.next = addedNode;
            nodeInPosition.previous = addedNode;
        }

        this.listLength++;
    }

    public Piece remover() {
        if (this.isEmpty()){
            return null;
        }

        Node removedNode = this.end;

        if (removedNode.previous != null)
            removedNode.previous.next = null;
        this.end = removedNode.previous;
        this.listLength--;

        return removedNode.getPiece();
    }

    public Piece remover(int position) throws IllegalArgumentException {
        if (position < 1 || position > this.listLength){
            throw new IllegalArgumentException("Valor fora do permitido");
        }
        Node removedNode;

        if (position == 1){
            removedNode = this.start;
            if (removedNode.next != null)
                removedNode.next.previous = null;
            this.start = removedNode.next;

        } else if (position == this.listLength) {
            removedNode = this.end;
            if (removedNode.previous != null)
                removedNode.previous.next = null;
            this.end = removedNode.previous;

        } else {
            int pos = 1;
            Node nodeInPosition = this.start;

            while (pos++ < position){
                nodeInPosition = nodeInPosition.next;
            }

            removedNode = nodeInPosition;
            if (removedNode.previous != null)
                removedNode.previous.next = removedNode.next;
            if (removedNode.next != null)
                removedNode.next.previous = removedNode.previous;
        }

        this.listLength--;
        return removedNode.getPiece();
    }
}
