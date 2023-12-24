import java.util.Random;
import java.util.Scanner;

public class GameBoard {

    char[][] oceanGrid;
    Scanner input = new Scanner(System.in);

    public GameBoard(){
        oceanGrid = createOceanMap();
    }

    public  void updateOceanMap(int x, int y, char result) {
        oceanGrid[x][y] = result;
    }

    public  char[][] createOceanMap() {
        char[][] oceanGrid = new char[10][10];
        
        for (int row = 0; row <10; row++) {
            for (int col = 0; col < 10; col++) {
                oceanGrid[row][col] = ' ';
            }
        }
        
        return oceanGrid;
    }

    public  void displayEmptyOceanMap() {
    
        System.out.println("****** Welcome to Battle Ships game ******");
        System.out.println("Right now, the sea is empty");   

         System.out.println("\n   0123456789");

        for (int row = 0; row < 10; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < 10; col++) {               
                System.out.print(' ');               
            }
            System.out.println("| " + row);
        }
        
        System.out.println("   0123456789\n");
    
    }

    public void displayResult(int playerShips, int computerShips){

        System.out.println("\n   0123456789");
    
        for (int row = 0; row < 10; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < 10; col++) {
                if (oceanGrid != null) {
                    char cellValue = oceanGrid[row][col];
                    if (cellValue == '1') {
                        System.out.print('@'); // Display player's ship as '@'
                    } else if (cellValue == '2') {
                        System.out.print(' '); // Display computer's ship as ' '
                    } else {
                        System.out.print(cellValue);
                    }
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println("| " + row);
        }
    
        System.out.println("   0123456789\n");
        System.out.printf("Your ships: %d  | Computer ships: %d\n", playerShips, computerShips);
    }

    public  void gameOver(int playerShips, int computerShips) {

        this.displayResult(playerShips, computerShips);
        System.out.println(' ');
        if (playerShips == 0) {
            System.out.println("The computer wins! :(");
        } else {
            System.out.println("Hooray! You win the battle :)");
        }

        input.close();
    }

    public boolean isValid(int x, int y){
         if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            System.out.println("Invalid coordinates. Choose coordinates within the 10x10 grid.");
            return false;
        } else if (oceanGrid[x][y] == 'x' || oceanGrid[x][y] == '!' || oceanGrid[x][y] == '-'){
            System.out.println("Invalid coordinates. The location has already been chosen..");
            return false;
        }
        return true;
    }
    
    public boolean isPlacementValid(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            System.out.println("Invalid coordinates. Choose coordinates within the 10x10 grid.");
            return false;
        } else if (oceanGrid[x][y] == '1' || oceanGrid[x][y] == '2') {
            System.out.println("Invalid coordinates. You can't place two ships on the same location.");
            return false;
        }
        return true;
    }

    public void deployPlayerShip(){

        for (int shipNumber = 1; shipNumber <= 5; shipNumber++) {
            int x, y;
            do {
                System.out.print("Enter X coordinate for your " + shipNumber + " ship: ");
                x = input.nextInt();
                System.out.print("Enter Y coordinate for your " + shipNumber + " ship: ");
                y = input.nextInt();
            } while (!isPlacementValid(x, y));

            oceanGrid[x][y] = '1';
            
        }
        
    }

    public void deployComputerShips() {
        Random random = new Random();
        for (int shipNumber = 1; shipNumber <= 5; shipNumber++) {
            int x, y;
            do {
                x = random.nextInt(10); // Generate a random X coordinate
                y = random.nextInt(10); // Generate a random Y coordinate
            } while (!isPlacementValid(x, y));

            oceanGrid[x][y] = '2';
            
            System.out.println(shipNumber + ". ship DEPLOYED");
        }
    }

    public int[] playerTurn(int playerShips, int computerShips){ 

        System.out.println(" \n Your Turn:");
        int playerX, playerY;
        do {
            System.out.print("Enter X coordinate: ");
            playerX = input.nextInt();
            System.out.print("Enter Y coordinate: ");
            playerY = input.nextInt();
        } while (!isValid(playerX, playerY));

        char result = this.evaluateGuess(playerX, playerY);
        
        this.updateOceanMap(playerX, playerY, result);
        return this.generateResult(result, playerShips, computerShips, 1);

}

    public char evaluateGuess(int x, int y) {
        if (oceanGrid[x][y] == '1') {

            return'x'; // Player's ship is hit

        } else if (oceanGrid[x][y] == '2') {
            return '!'; // Computer's ship is hit
        
        } else {
            return '-'; // Missed
        }
    }

    public int[] generateResult(char result, int playerShips, int computerShips, int player){

        String displayResult;

        if (result == 'x' && player == 1 ) {
                playerShips--;
                displayResult = "Oh no, you sunk your own ship :( ";
            
            }  else if (result== '!' && player == 1) {
                computerShips--;
                displayResult = "Boom! You sunk the ship! :)";
            
            }  else if (result == '-' && player == 1){
                displayResult = "Sorry, you missed";
            
            }  else if ( result == '!' && player == 2){
                computerShips--;
                displayResult = "The computer sunk one of its own ships ";
            
            }   else if (result == 'x' && player == 2 ){
                playerShips--;
                displayResult = "The computer sunk one of your ships";
            
            }   else{
                displayResult = "The computer missed";

            }
        
        
        this.displayResult(playerShips, computerShips);
        System.out.println("\n"+ displayResult);
        return new int[] {playerShips, computerShips};
    }

    public int[] computerTurn(int playerShips, int computerShips){
        int computerX, computerY;

        System.out.println("\n Computer's Turn:");
            
        do {
                computerX = getRandomCoordinate();
                computerY = getRandomCoordinate();
            } while (!isValid(computerX, computerY));
        
        
        char result = this.evaluateGuess(computerX, computerY);
        this.updateOceanMap(computerX, computerY, result);        
        return this.generateResult(result, playerShips, computerShips, 2);
    } 

    public int getRandomCoordinate() {
        Random random = new Random();
        return random.nextInt(10);
    }  


}


