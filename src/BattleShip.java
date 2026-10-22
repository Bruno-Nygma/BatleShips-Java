import java.util.Scanner;

public class BattleShip {
    final static char SYMBOL_WATER = '~';
    final static char SYMBOL_SHIP = 'N';
    final static char SYMBOL_HIT = 'X';
    final static char SYMBOL_MISS = 'O';
    final static int FIELD_SIZE = 5;
    final static int SHIP_SIZE = 3;
    final static int MAX_SHOTS = 15;
    static int shotCount;
    static int successfulShots;
    static int shipParts;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        shipParts = 0;
        shotCount = 0;
        successfulShots = 0;

        char[][] visibleField = new char[FIELD_SIZE][FIELD_SIZE];
        fillField(visibleField);
        printField(visibleField);

        char[][] shipsField = new char[FIELD_SIZE][FIELD_SIZE];
        fillField(shipsField);
        placeShipRandom(shipsField, SHIP_SIZE);

        int[] playerShotCoordinates;

        while (successfulShots < shipParts && shotCount < MAX_SHOTS){
            playerShotCoordinates = askShotCoordinates(scanner);
            if (!coordinatesCheck(visibleField, playerShotCoordinates)){
                continue;
            }
            shotOutcome(visibleField, shipsField, playerShotCoordinates);
            printField(visibleField);
        }

        if (successfulShots == shipParts){
            System.out.println("Nave affondata in " + shotCount + " colpi!");
        } else {
            System.out.println("Hai perso! La nave era qui:");
            showShips(visibleField, shipsField);
            printField(visibleField);
        }

        scanner.close();
    }

    static void fillField(char[][] field){
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                field[i][j] = SYMBOL_WATER;
            }
        }
    }

    static void printField(char[][] field){
        String row = "   ";
        for (int k = 0; k < FIELD_SIZE; k++){
            row += (k + 1) + " ";
        }
        System.out.println(row);
        for (int i = 0; i < field.length; i++){
            row = (i + 1) + "  ";
            for (int j = 0; j < field.length; j++){
                row = row + field[i][j] + " ";
            }
            System.out.println(row);
        }
    }

    static int[] askShotCoordinates(Scanner scanner){
        System.out.print("Dove vuoi sparare?\nRiga > ");
        int shotRow = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.print("Colonna > ");
        int shotColumn = scanner.nextInt() - 1;
        scanner.nextLine();

        return new int[]{shotRow, shotColumn};
    }

    static void shotOutcome(char[][] visibleField, char[][] shipsField, int[] shotCoordinates){
        if (shipsField[shotCoordinates[0]][shotCoordinates[1]] == SYMBOL_SHIP){
            System.out.println("Colpito!");
            visibleField[shotCoordinates[0]][shotCoordinates[1]] = SYMBOL_HIT;
            successfulShots++;
        } else {
            System.out.println("Mancato!");
            visibleField[shotCoordinates[0]][shotCoordinates[1]] = SYMBOL_MISS;
        }
        shotCount++;
    }

    static boolean coordinatesCheck(char[][] visibleField, int[] shotCoordinates){
        for(int number : shotCoordinates){
            if (number >= FIELD_SIZE || number < 0){
                System.out.println("Coordinate fuori dalla griglia, riprova");
                return false;
            }
        }
        if (visibleField[shotCoordinates[0]][shotCoordinates[1]] != SYMBOL_WATER){
            System.out.println("Hai già sparato qui, riprova");
            return false;
        }
        return true;
    }

    static void placeShipRandom(char[][] shipsField, int shipSize){
        int maxStartTile = FIELD_SIZE - shipSize;
        boolean horizontal = Math.random() < 0.5;

        int fixedCoordinate = (int) (Math.random() * FIELD_SIZE);
        int startTile = (int) (Math.random() * maxStartTile + 1);

        if (horizontal){
            for (int i = 0; i < shipSize; i++){
                shipsField[fixedCoordinate][startTile + i] = SYMBOL_SHIP;
            }
        } else {
            for (int i = 0; i < shipSize; i++){
                shipsField[startTile + i][fixedCoordinate] = SYMBOL_SHIP;
            }
        }
        shipParts += shipSize;
    }

    static void showShips (char[][] visibleField, char[][] shipsField){
        for (int i = 0; i < FIELD_SIZE; i++){
            for (int j = 0; j < FIELD_SIZE; j++){
                if (shipsField[i][j] == SYMBOL_SHIP && visibleField[i][j] == SYMBOL_WATER){
                    visibleField[i][j] = SYMBOL_SHIP;
                }
            }
        }
    }
}