public class Output {
    public void printList(String prefix, List list){
        ListIterator listIterator = list.getIterator();

        if (list.isEmpty()) { return; }

        System.out.print(prefix);
        do { System.out.print(listIterator.getPiece().toString()); }
        while (listIterator.hasNext());

        System.out.println();
    }

    public void printList_WithNodePositions(String prefix, List list){
        ListIterator listIterator = list.getIterator();

        if (list.isEmpty()) { return; }

        System.out.print(prefix);
        int pos = 1;
        do { System.out.printf("%d - %s ", pos++, listIterator.getPiece().toString()); }
        while (listIterator.hasNext());

        System.out.println();
    }

    public void printGame(Player player, List mesa) {
        System.out.print(
            "\n\n                               Situação do jogo:                             \n" +
            "|---------------------------------------------------------------------------|\n\n"
        );
        this.printList("| Mesa: ", mesa);
        this.printList("| Mão do Player: ", player.getPieces());
        System.out.println("|---------------------------------------------------------------------------|");
        System.out.println();
    }

    public void announcePlay(Player player, Piece piece) {
        System.out.printf("%s jogou a peça %s.\n", player.getName(), piece.toString());
    }

    public void announcePlayerTurn(Player player) {
        System.out.printf("Turno do %s.\n", player.getName());
    }

    public void announceWinner(Player player) {
        System.out.printf("%s venceu o jogo!\n", player.getName());
    }

    public void announceDraw() {
        System.out.printf("O jogo terminou em empate, pois nenhum jogadore pode jogar e ambos têm a mesma pontuação!\n");
    }

    public void announceFirstPlay(Player player) {
        System.out.printf("O %s começa jogando, pois tem a maior peça.\n", player.getName());
    }

    public void askPieceChoice(){
        System.out.print("Selecione a posição da peça que deseja jogar: ");
    }

    public void askAction_NoPlayablePieces(){
        System.out.print(
            "1 - Comprar peça do monte \n2 - Passar sua vez \n" +
            "Selecione o que deseja fazer: "
        );
    }

    public void askTipChoice(){
        System.out.print(
            "1 - Esquerda \n2 - Direita \n" +
            "Selecione o lado que deseja colocar a peça: "
        );
    }

    public void announceBuy(Player player) {
        System.out.printf("%s comprou uma peça do monte.\n", player.getName());
    }

    public void announcePassTurn(Player player) {
        System.out.printf("%s passou a vez.\n", player.getName());
    }

    public void announceInvalidOperation(String operacao) {
        System.out.printf("Operação inválida: %s!\n", operacao);
    }
}
