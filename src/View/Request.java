package View;

import java.util.ArrayList;
import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private final String[] strings = {
            "create account ",
            "login ",
            "show leaderboard"
    }
    private String command;
    private RequestType type;

    public boolean checkAccountCreationSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_ACCOUNT.ordinal()] + "\\w+");
    }

    public boolean checkLeaderBoardSyntax(){
        return this.getCommand().matches(this.getStrings()[RequestType.LEADERBOARD.ordinal()]);
    }

    public boolean checkPasswordValidation() {
        return true;
    }

    public boolean checkLoginSyntax() {

    }

    public void checkMenuEntrnaceSyntax() {

    }

    public void checkSearchSyntax() {

    }

    public void checkDeckCreationSyntax() {

    }

    public void checkShowDeckSyntax() {

    }

    public void checkDeckDeletionSyntax() {

    }

    public void checkToDeckAdditionSyntax() {

    }

    public void checkFromDeckDeletionSyntax() {

    }

    public void checkDeckSelectionSyntax() {

    }

    public void checkPurchaseSyntax() {

    }

    public void checkCollectionSearchSyntax() {

    }

    public void checkSellSyntax() {

    }

    public void checkMoveSyntax() {

    }

    public void checkAssaultSyntax() {

    }

    public void checkComboSyntax() {

    }

    public void checkSPUsageSyntax() {

    }

    public void checkCardInsertSyntax() {

    }

    public void checkItemUseSyntax() {

    }

    public void checkFetchInfoSyntax() {

    }

    public RequestType getType() {
        return type;
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
}
