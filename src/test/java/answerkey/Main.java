package answerkey;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String WELCOME_TEXT = "Welcome to the Baseball Database";
    private static final String ENTER_ID_NAME_AWARD_ETC_TEXT = "Enter id, name, awards, or quit:";
    private static final String ENTER_LAST_NAME = "Enter player's last name:";
    private static final String ENTER_ID = "Enter the id:";
    private static final String CLOSING_TEXT = "Closing Baseball Database";
    private static final String HUH = "I don't understand your command.";

    public static void main(String[] args) {
        answerkey.Awards awards = answerkey.Awards.loadFiles();
        Players players = awards.players();

        Scanner scan = new Scanner(System.in);

        printWelcomeText();
        inputTransition(awards, players, scan);
    }

    public static void inputTransition(answerkey.Awards awards, Players players, Scanner scan) {
        String input = printAndGetInput(scan, ENTER_ID_NAME_AWARD_ETC_TEXT);

        switch (input) {
            case "name":
                handleName(scan, players);
                inputTransition(awards, players, scan);
                break;
            case "id":
                handleId(scan, players);
                inputTransition(awards, players, scan);
                break;
            case "awards":
                handleAwards(scan, awards);
                inputTransition(awards, players, scan);
                break;
            case "quit":
                System.out.println(CLOSING_TEXT);
                break;
            default:
                System.out.println(HUH);
                inputTransition(awards, players, scan);
        }
    }

    public static void handleName(Scanner scan, Players players) {
        String nameInput = printAndGetInput(scan, ENTER_LAST_NAME);
        handleByLastName(nameInput, players);
    }

    public static void handleId(Scanner scan, Players players) {
        String idInput = printAndGetInput(scan, ENTER_ID);
        handleById(idInput, players);
    }

    public static void handleAwards(Scanner scan, answerkey.Awards awards) {
        String idInput = printAndGetInput(scan, ENTER_ID);
        handleGetAwards(idInput, awards);
    }

    public static String printAndGetInput(Scanner scan, String prompt) {
        System.out.println(prompt);
        return scan.nextLine();
    }

    public static void printWelcomeText() {
        System.out.println(WELCOME_TEXT);
    }

    public static void handleByLastName(String lastName, Players players) {
        List<answerkey.Player> playersFiltered = players.findByLastName(lastName);

        if (!playersFiltered.isEmpty()) {
            playersFiltered.forEach((player) -> System.out.println(player.id() + ": " + player.lastName() + ", " + player.firstName()));
        }
    }

    public static void handleById(String id, Players players) {
        List<answerkey.Player> playersFiltered = players.findById(id);

        if (!playersFiltered.isEmpty()) {
            playersFiltered.forEach((player) -> System.out.println("Player: " + player.lastName() + ", " + player.firstName()));
        }

    }

    public static void handleGetAwards(String playerId, answerkey.Awards awards) {
        List<answerkey.Award> awardsFound = awards.find(playerId);

        if (!awardsFound.isEmpty()) {
            awardsFound.forEach(System.out::println);
        } else {
            System.out.println("No awards for this player.");
        }

    }

}

