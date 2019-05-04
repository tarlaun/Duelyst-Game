package View;

import Model.Card;
import Model.Coordinate;

import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private final String[] strings = {
            "create account ",
            "login ",
            "show leaderboard",
            "save",
            "logout",
            "help",
            "Enter ",
            "exit",
            "show",
            "search ",
            "create deck ",
            "delete deck ",
            "add ",
            "remove ",
            "validate deck ",
            "select deck ",
            "show deck ",
            "show all decks",
            "show collection",
            "search collection ",
            "save in collection ",
            "buy ",
            "sell ",
            "Game info",
            "Show my minions",
            "Show opponent minions",
            "Show card info ",
            "Select ",
            "Move to ( ",
            "Attack ",
            "Attack combo ",
            "Use special power ( ",
            "Show hand",
            "Insert ",
            "End turn",
            "Show collectables",
            "Show info",
            "Use ( ",
            "Show Next Card",
            "Show cards",
            "End Game",
            "Show menu"
    };
    private String command;
    private RequestType type;

    public boolean checkAccountCreationSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_ACCOUNT.ordinal()] + "\\w+");
    }

    public boolean checkLeaderBoardSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.LEADERBOARD.ordinal()]);
    }

    public boolean checkPasswordValidation() {
        return true;
    }

    public boolean checkLoginSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.LOGIN.ordinal()]);
    }

    public boolean checkLogoutSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.LOGOUT.ordinal()]);
    }

    public boolean checkMenuEntrnaceSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.ENTRANCE.ordinal()] + "Collection|Shop|Battle|Exit");

    }

    public boolean checkSearchSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SEARCH_COLLECTION.ordinal()] + "\\w+");
    }

    public boolean checkDeckSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_DECK.ordinal()] + "\\w+");
    }


    public boolean checkToDeckAdditionSyntax() {

    }

    public boolean checkFromDeckDeletionSyntax() {

    }

    public boolean checkValidationSyntax() {

    }

    public boolean checkShowDeckSyntax() {

    }

    public boolean checkDeckSelectionSyntax() {

    }

    public boolean checkShowAllDeckSyntax() {

    }

    public boolean checkBuySyntax() {

    }

    public boolean checkCardSelectionSyntax() {

    }

    public void checkCollectionSearchSyntax() {

    }

    public boolean checkSellSyntax() {

    }

    public boolean checkMoveSyntax() {

    }

    public boolean checkAssaultSyntax() {

    }

    public boolean checkComboSyntax() {

    }

    public boolean checkSPUsageSyntax() {

    }

    public boolean checkCardInsertSyntax() {

    }

    public void checkItemUseSyntax() {

    }

    public boolean checkFetchInfoSyntax() {

    }

    public RequestType getType() {
        for (int i = 0; i < strings.length; i++) {
            if (command.substring(0, strings[i].length()).equals(strings[i]))
                return RequestType.values()[i];
        }
    }

    public String getCommand() {
        return command;
    }

    public String[] getStrings() {
        return strings;
    }

    public String getNewCommand() {
        return scanner.nextLine();
    }

    public String[] getSplittedCommand(String input) {
        return input.split(" ");
    }

    public String getAccountName(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getPassword(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getMenu(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getObjectName(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getInsertedName(String input) {
        return this.getSplittedCommand(input)[1];
    }

    public String getDeckName(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public int getObjectID(String input) {
        return Integer.parseInt(this.getSplittedCommand(input)[1]);
    }

    public Coordinate getCoordinate(String input) {
        return new Coordinate(
                Integer.parseInt(this.getSplittedCommand(input)[this.getSplittedCommand(input).length - 2]),
                Integer.parseInt(this.getSplittedCommand(input)[this.getSplittedCommand(input).length - 1])
        );
    }

    public int[] getComboComradesId(String input) {
        int[] comradesId = new int[this.getSplittedCommand(input).length - 3];
        for (int i = 3; i < this.getSplittedCommand(input).length; i++) {
            comradesId[i - 3] = Integer.parseInt(this.getSplittedCommand(input)[i]);
        }
    }

    public int getOppIdInCombo(String input) {
        return Integer.parseInt(this.getSplittedCommand(input)[2]);
    }
}
