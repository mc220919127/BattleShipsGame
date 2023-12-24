public class BattleShipsGame {

    public static void main(String[] args) {
        GameBoard g = new GameBoard();

        int playerShips = 5;
        int computerShips = 5;

        //initiating board and players' input
        g.displayEmptyOceanMap();
        g.deployPlayerShip();
        g.displayResult(playerShips, computerShips);
        g.deployComputerShips();
        g.displayResult(playerShips, computerShips);

        //game starts 
        int[] result;

        while (playerShips > 0 && computerShips > 0) {
            result = g.playerTurn(playerShips, computerShips);
            playerShips = result[0];
            computerShips = result[1];

            if (playerShips <= 0 || computerShips <= 0) {
                break;
            }

            result = g.computerTurn(playerShips, computerShips);
            playerShips = result[0];
            computerShips = result[1];

        }

        g.gameOver(playerShips, computerShips);
    }
}

   
