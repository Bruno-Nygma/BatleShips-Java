import java.util.Scanner;

public class BattleShip {
    final static char SYMBOL_WATER = '~';
    final static char SYMBOL_SHIP = 'N';
    final static char SYMBOL_HIT = 'X';
    final static char SYMBOL_MISS = 'O';
    final static int FIELD_SIZE = 5;
    final static int[][] FIXED_SHIP = {{2, 1}, {2, 2}, {2, 3}};
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
        addShip(shipsField, FIXED_SHIP);

        int[] playerShotCoordinates;

        while (successfulShots < shipParts){
            playerShotCoordinates = askShotCoordinates(scanner);
            if (!coordinatesCheck(visibleField, playerShotCoordinates)){
                continue;
            }
            shotOutcome(visibleField, shipsField, playerShotCoordinates);
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
        String row = "";
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field.length; j++){
                row = row + field[i][j] + " ";
            }
            System.out.println(row);
            row = "";
        }
    }

    static void addShip(char[][] field, int[][] boat){
        for (int[] coordinate : boat){
            field[coordinate[0]][coordinate[1]] = SYMBOL_SHIP;
            shipParts++;
        }
    }

    static int[] askShotCoordinates(Scanner scanner){
        System.out.print("Dove vuoi sparare?\nRiga > ");
        int shotRow = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Colonna > ");
        int shotColumn = scanner.nextInt();
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
}