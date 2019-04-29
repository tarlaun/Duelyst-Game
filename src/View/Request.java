package View;

import javax.swing.text.rtf.RTFEditorKit;
import java.util.ArrayList;
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
            "exit"
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
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_DECK.ordinal()]+"\\w+");
    }


    public boolean checkToDeckAdditionSyntax() {

    }

    public boolean checkFromDeckDeletionSyntax() {

    }

    public boolean checkValidationSyntax(){

    }

    public void checkShowDeckSyntax() {

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

    public String getMenu(String input) {
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getCardName(String input){
        return this.getSplittedCommand(input)[getSplittedCommand(input).length - 1];
    }

    public String getDeckName(String input){
        return this.getSplittedCommand(input)[getSplittedCommand(input).length-1];
    }

    public int getObjectID(String input){
        return Integer.parseInt(this.getSplittedCommand(input)[1]);
    }
}
