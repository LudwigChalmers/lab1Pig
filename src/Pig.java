import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    public static void main(String[] args) {
        new Pig().program();
    }

    private int i;
    private int winPts = 20;// Points to win
    void program() {
        //test();                 // <-------------- Uncomment to run tests!



        Player[] players;         // The players (array of Player objects)
        Player actual;            // Actual player for round (must use)
        boolean aborted = false;  // Game aborted by player?

        // Hard coded 2 players, replace *last* of all with ...
        players = new Player[]{new Player("Olle"), new Player("Fia")};
        //players = getPlayers();  // ... this (method to read in all players)

        welcomeMsg(winPts);
        statusMsg(players);
        startGame(players);
        // TODO Game logic, using small step, functional decomposition


        //gameOverMsg(actual, aborted);
    }

    // ---- Game logic methods --------------

    // TODO
    void startGame(Player[] players) {
        int imax = players.length;
        if (i == imax) {
            i = 0;
        }
        Player currentPlayer = players[i];
        getPlayerChoice(currentPlayer, players);

    }

    void rollDice(Player player, Player[] players) {
        int roll = rand.nextInt(6) + 1;
        roundMsg(roll, player, players);
    }

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

    void roundMsg(int result, Player actual, Player[] players) {
        if (result > 1) {
            actual.roundPts = actual.roundPts + result;
            actual.totalPts = actual.totalPts + result;
            if(actual.totalPts >= winPts ){
                gameOverMsg(actual, false);
            }
            out.println("Got " + result + " running total are " + actual.roundPts);
            getPlayerChoice(actual, players);

        } else {
            actual.roundPts = 0;
            out.println("Got 1 lost it all!");
            i++;
            startGame(players);
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts) + " points");
            exit(0);
        }
    }

    void getPlayerChoice(Player player, Player[] players) {
        out.print("Player is " + player.name + " > ");
        char c = sc.nextLine().charAt(0);
        switch (c) {
            case 'r':
                rollDice(player, players);
                break;
            case 'n':
                i++;
                statusMsg(players);
                startGame(players);
                break;
            case 'q':
                gameOverMsg(player, true);
                break;
        }
    }

    Player[] getPlayers() {
        // TODO
        return null;
    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data, an array of Players
        Player[] players = {new Player("a"), new Player("b"), new Player("c")};


        exit(0);   // End program
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
}



