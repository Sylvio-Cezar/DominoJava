public class StartApp {
    public static void main(String[] args) {

        startGame();

    }

    private static void startGame(){
        GameController gameController = new GameController(6);

        gameController.start();

    }

}
