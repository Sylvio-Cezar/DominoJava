import java.util.Random;

public class GameController {
    private Player player;
    private Player computer;
    private Player turnPlayer;
    private List table;
    private List mount;
    private Output output;
    private Input input;

    public GameController(int highestPiece) {
        this.player = new Player("Player");
        this.computer = new Player("Computador");
        this.table = new List();

        this.output = new Output();
        this.input = new Input();

        this.createMount(highestPiece);
        this.spreadPieces();
    }

    private void createMount(int limit){
        this.mount = new List();

        for(int i = 0; i <= limit; i++) {
            for(int j = i; j <= limit; j++) {
                mount.insert(new Piece(i, j));
            }
        }
    }

    private void spreadPieces(){
        int initialQtt = Math.floorDiv(this.mount.getlistLength(), 4);

        Piece piece;
        for (int i = 0; i < initialQtt; i++){
            piece = mount.remove(getRandomInteger(1, mount.getlistLength()));
            piece.setColor(Colors.ANSI_CYAN);
            player.getPieces().insert(piece);

            piece = mount.remove(getRandomInteger(1, mount.getlistLength()));
            piece.setColor(Colors.ANSI_YELLOW);
            computer.getPieces().insert(piece);
        }
    }

    public void start() {
        this.doFirstMove();
        this.startMatch();
    }

    private void startMatch() {
        boolean isPlaying = true;
        do{
            output.printGame(this.player, this.table);
            this.changeTurnPlayer();

            if (this.turnPlayer.equals(this.player)){
                this.doPlayerTurn();
            } else {
                this.doComputerTurn();
            }

            if (this.turnPlayer.getPieces().getlistLength() == 0) {
                output.announceWinner(this.turnPlayer);
                isPlaying = false;
            }

            if (this.mount.isEmpty()) {
                List playerPlayablePositions = this.getPlayablePiecesPositions(this.player);
                List computerPlayablePositions = this.getPlayablePiecesPositions(this.computer);
                if (playerPlayablePositions.isEmpty() && computerPlayablePositions.isEmpty()) {
                    PiecePoints playerPoints = this.getPiecePoints(this.player.getPieces());
                    PiecePoints computerPoints = this.getPiecePoints(this.computer.getPieces());

                    if (playerPoints.sumTotalValues == computerPoints.sumTotalValues) {
                        output.announceDraw();
                    } else {
                        // Vençe o player com a menor pontuação somando os valores de todas as peças
                        output.announceWinner(computerPoints.sumTotalValues > playerPoints.sumTotalValues ? this.player : this.computer);
                    }

                    isPlaying = false;
                }
            }

        } while(isPlaying);
    }

    /* private void doComputerTurn() {
        do {
            List playablePositions = this.getPlayablePiecesPositions(this.turnPlayer);
            if (playablePositions.isEmpty()){
                if (getRandomInteger(1, 2) != 1){
                    output.announcePassTurn(this.turnPlayer);
                    break;
                }

                try {
                    this.buyPiece(this.turnPlayer);
                    continue;
                } catch (UnsupportedOperationException e) {
                    output.announcePassTurn(this.turnPlayer);
                    break;
                }
            }

            List pieces = this.turnPlayer.getPieces();
            Piece pieceToRemove = playablePositions.remove();
            int piecePosition = 1;
            ListIterator iterator = pieces.getIterator();
            while (iterator.hasNext()) {
                Piece piece = iterator.next();
                if (piece.equals(pieceToRemove)) {
                    break;
                }
                piecePosition++;
            }
            playPiece(this.turnPlayer, piecePosition);

            break;
        } while (true);
    } */

    private void doComputerTurn() {
        do {
            List playablePositions = this.getPlayablePiecesPositions(this.turnPlayer);
            if (playablePositions.isEmpty()){
                if (getRandomInteger(1, 2)!= 1){
                    output.announcePassTurn(this.turnPlayer);
                    break;
                }

                try {
                    this.buyPiece(this.turnPlayer);
                    continue;
                } catch (UnsupportedOperationException e) {
                    output.announcePassTurn(this.turnPlayer);
                    break;
                }
            }

            List pieces = this.turnPlayer.getPieces();
            Piece pieceToRemove = playablePositions.remove();
            int piecePosition = 1;
            ListIterator iterator = pieces.getIterator();
            while (iterator.hasNext()) {
                Piece piece = iterator.next();
                if (piece.equals(pieceToRemove)) {
                    break;
                }
                piecePosition++;
            }
            playPiece(this.turnPlayer, piecePosition);

            break;
        } while (true);
    }

    private void doPlayerTurn() {
        do {
            List playablePositions = this.getPlayablePiecesPositions(this.turnPlayer);
            if (playablePositions.isEmpty()){
                output.askAction_NoPlayablePieces();
                int selectedAction = -1;
                try {
                    selectedAction = input.getInteger();
                    if (selectedAction == 2){
                        output.announcePassTurn(this.turnPlayer);
                        break;
                    } else if (selectedAction == 1) {
                        try {
                            this.buyPiece(this.turnPlayer);
                            output.printGame(this.player, this.table);
                            continue;
                        } catch (UnsupportedOperationException e) {
                            output.announcePassTurn(this.turnPlayer);
                            break;
                        }
                    } else {
                        output.announceInvalidOperation("Valor não corresponde a uma ação disponível");
                        continue;
                    }
                }
                catch (NumberFormatException e) {
                    output.announceInvalidOperation("valor digitado não é um número");
                    continue;
                }
            }
            output.printList("Peças na Mesa: ", this.table);
            output.printList_WithNodePositions("Mão do Player: ", this.turnPlayer.getPieces());
            output.askPieceChoice();
            try {
                playPiece(this.turnPlayer, input.getInteger());
            } catch (IllegalArgumentException e) {
                output.announceInvalidOperation(e.getMessage());
                continue;
            }
            break;
        } while(true);
    }

    private void doFirstMove() {
        this.turnPlayer = (playerStarts()) ? this.player : this.computer;
        PiecePoints piecePoints = getPiecePoints(this.turnPlayer.getPieces());
        output.announceFirstPlay(this.turnPlayer);
        playPiece(this.turnPlayer, (piecePoints.highestPair != -1) ? piecePoints.HighestPairPosition : piecePoints.HighestValuePosition);
    }

    private void changeTurnPlayer(){
        this.turnPlayer = (turnPlayer.equals(this.player)) ? this.computer : this.player;
        output.announcePlayerTurn(this.turnPlayer);
    }

    private int getRandomInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private boolean playerStarts() {
        PiecePoints playerPoints = getPiecePoints(this.player.getPieces());
        PiecePoints computerPoints = getPiecePoints(this.computer.getPieces());

        if (playerPoints.highestPair != -1 || computerPoints.highestPair != -1) {
            return playerPoints.highestPair > computerPoints.highestPair;
        } else {
            return playerPoints.highestValue > computerPoints.highestValue;
        }
    }

    private class PiecePoints {
        int highestPair;
        int highestValue;
        int HighestPairPosition;
        int HighestValuePosition;
        int sumTotalValues;

        public PiecePoints(int highestPair, int highestValue, int HighestPairPosition, int HighestValuePosition, int somaValoresTotal) {
            this.highestPair = highestPair;
            this.highestValue = highestValue;
            this.HighestPairPosition = HighestPairPosition;
            this.HighestValuePosition = HighestValuePosition;
            this.sumTotalValues = somaValoresTotal;
        }
    }

    private PiecePoints getPiecePoints(List list) {
        int highestPair = -1;
        int highestValue = -1;
        int HighestPairPosition = -1;
        int HighestValuePosition = -1;
        int sumTotalValues = 0;

        int pos = 1;
        ListIterator listIterator = list.getIterator();

        do {
            Piece piece = listIterator.getPiece();
            int firstValue = piece.getFirstValue();
            int secondValue = piece.getSecondValue();

            if (firstValue == secondValue && firstValue > highestPair){
                highestPair = firstValue;
                HighestPairPosition = pos;
            }

            int pieceValue = firstValue + secondValue;
            if (pieceValue > highestValue){
                highestValue = pieceValue;
                HighestValuePosition = pos;
            }

            sumTotalValues += pieceValue;

            pos++;
        } while (listIterator.hasNext());

        return new PiecePoints(highestPair, highestValue, HighestPairPosition, HighestValuePosition, sumTotalValues);
    }

    private void playPiece(Player player, int piecePosition) throws IllegalArgumentException{
        Piece playedPiece = player.getPieces().remove(piecePosition);
        if (table.isEmpty()){
            this.table.insert(playedPiece);
            this.output.announcePlay(player, playedPiece);
        } else { // avaliar jogada
            int firstTip = table.getStart().getFirstValue();
            int secondTip = table.getEnd().getSecondValue();

            int playedPiece_FirstValue = playedPiece.getFirstValue();
            int playedPiece_SecondValue = playedPiece.getSecondValue();

            boolean canPlayOnFirstTip = playedPiece_FirstValue == firstTip
                    || playedPiece_SecondValue == firstTip;
            boolean canPlayOnSecondTip = playedPiece_FirstValue == secondTip
                    || playedPiece_SecondValue == secondTip;

            boolean choseFirst = true;
            if (canPlayOnFirstTip && canPlayOnSecondTip) {
                if (this.turnPlayer.equals(this.player)){
                    int selectedAction = -1;
                    do{
                        output.askTipChoice();
                        try {
                            selectedAction = input.getInteger();
                            choseFirst = selectedAction == 1;
                        }
                        catch (NumberFormatException e) { output.announceInvalidOperation("Valor digitado não é um número"); }
                    } while (selectedAction != 1 && selectedAction != 2);
                } else {
                    choseFirst = getRandomInteger(1, 2) == 1;
                }
            }

            if (canPlayOnFirstTip && choseFirst) {
                if (playedPiece_SecondValue != firstTip) {
                    playedPiece.flipPiece();
                }
                this.table.insert(playedPiece, 1);
                this.output.announcePlay(player, playedPiece);
            } else if (canPlayOnSecondTip) {
                if (playedPiece_FirstValue != secondTip) {
                    playedPiece.flipPiece();
                }
                this.table.insert(playedPiece);
                this.output.announcePlay(player, playedPiece);
            } else {
                player.getPieces().insert(playedPiece, piecePosition);
                throw new IllegalArgumentException("Peça não pode ser jogada em nenhum dos lados da table");
            }
        }
    }

    private void buyPiece(Player player) throws UnsupportedOperationException {
        if (this.mount.isEmpty()){
            throw new UnsupportedOperationException("Monte está vazio.");
        }

        Piece buyedPiece = this.mount.remove(this.getRandomInteger(1, this.mount.getlistLength()));
        buyedPiece.setColor(player.equals(this.player) ? Colors.ANSI_CYAN : Colors.ANSI_YELLOW);
        player.getPieces().insert(buyedPiece);
        this.output.announceBuy(player);
    }

    private List getPlayablePiecesPositions(Player player) {
        List playablePositions = new List();

        int firstTip = table.getStart().getFirstValue();
        int secondTip = table.getEnd().getSecondValue();

        int pos = 1;
        ListIterator listIterator = player.getPieces().getIterator();

        do {
            Piece piece = listIterator.getPiece();
            int firstValue = piece.getFirstValue();
            int secondValue = piece.getSecondValue();

            boolean canPlayOnFirstTip = firstValue == firstTip
                    || secondValue == firstTip;
            boolean canPlayOnSecondTip = firstValue == secondTip
                    || secondValue == secondTip;

            if (canPlayOnFirstTip || canPlayOnSecondTip) {
                // playablePositions.insert(pos);
                Piece newPiece = new Piece(pos, pos); // assuming pos is the value you want to insert
                playablePositions.insert(newPiece);
            }

            pos++;
        } while (listIterator.hasNext());

        return playablePositions;
    }
}
