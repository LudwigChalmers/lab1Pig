import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();
    boolean aborted = false;  // Game aborted by player?
    final int winPts = 30;    // Points to win

    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        Player[] players;         // The players (array of Player objects)
        // Actual player for round (must use)
        int playerPosition = 0;
        // Hard coded 2 players, replace *last* of all with ...
        players = new Player[]{new Player("Olle"), new Player("Fia")};
        Player actual = players[0];
        //players = getPlayers();  // ... this (method to read in all players)

        welcomeMsg(winPts);
        statusMsg(players);
        // Player actual = players[switchPlayer(playerPosition, players)];

        while (!aborted) {

            String choice = getPlayerChoice(actual);
            switch (choice) {
                case "r":
                    int diceRoll = rollTheDice();
                    if (diceRoll > 1) {
                        addRoundPts(diceRoll, actual, players, playerPosition);

                        if(actual.totalPts >= winPts || actual.roundPts >= winPts) {
                            aborted = true; // ends while-loop
                            gameOverMsg(actual, false);
                        }
                    }

                    else {
                        actual.totalPts = actual.totalPts - actual.roundPts;
                        actual.roundPts = 0;
                        playerPosition = switchPlayer(playerPosition, players);
                        actual = players[playerPosition];
                    }
                    if(!aborted) {
                        roundMsg(diceRoll, actual);
                    }
                    break;
                case "q":
                    gameOverMsg(actual, true);
                    aborted = true;
                    break;
               /* case "t": //Testing totalpoints (debug)
                    out.println(actual.totalPts);
                    break; */
                case "n":

                    actual.totalPts += actual.roundPts;
                    playerPosition = switchPlayer(playerPosition, players);
                    actual = players[playerPosition];

                    break;
            }


        }


        // TODO Game logic, using small step, functional decomposition

    }

    // ---- Game logic methods --------------
    int rollTheDice() {
        return rand.nextInt(6) + 1; //Returns a diceroll
    }

    void addRoundPts(int diceRoll, Player actual, Player[] players, int i) {
        if (diceRoll > 1) {
            actual.roundPts += diceRoll;
            actual.totalPts += diceRoll;
        } else {


        }
    }

    int switchPlayer(int playerPosition, Player[] players) {
        return (playerPosition + 1) % players.length;
    }

    // TODO

    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) {
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player actual) {
        if (result > 1) {
            out.println("Got " + result + " running total are " + actual.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    Player[] getPlayers() {
        // TODO
        return null;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        Player(String name) {
            this.name = name;
        }
    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data, an array of Players 
        Player[] players = {new Player("a"), new Player("b"), new Player("c")};


        exit(0);   // End program
    }
}



