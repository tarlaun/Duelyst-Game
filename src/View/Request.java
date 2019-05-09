package View;

import Model.BattleMode;
import Model.Card;
import Model.Coordinate;
import Model.GameType;
import Model.Process;

import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private final String[] strings = {
            "createAccount",
            "login",
            "showLeaderboard",
            "save",
            "logout",
            "help",
            "Enter",
            "exit",
            "show",
            "search",
            "createDeck",
            "deleteDeck",
            "add",
            "remove",
            "validateDeck",
            "selectDeck",
            "showDeck",
            "showAllDecks",
            "showCollection",
            "searchCollection",
            "saveInCollection",
            "buy",
            "sell",
            "GameInfo",
            "ShowMyMinions",
            "ShowOpponentMinions",
            "ShowCardInfo",
            "Select",
            "MoveTo (",
            "Attack",
            "AttackCombo",
            "UseSpecialPower(",
            "ShowHand",
            "Insert",
            "EndTurn",
            "ShowCollectables",
            "ShowInfo",
            "Use(",
            "ShowNextCard",
            "ShowCards",
            "EndGame",
            "showMenu",
            "showMatchHistory",
            "SinglePlayer",
            "MultiPlayer",
            "KillEnemyHero",
            "Collecting",
            "Flag",
            "Story",
            "Custom",
            "selectUser",
            "showMap"
    };
    private String command;

    public boolean checkAccountCreationSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_ACCOUNT.ordinal()] + "\\s\\w+");
    }

    public boolean checkLeaderBoardSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.LEADERBOARD.ordinal()]);
    }

    public boolean checkPasswordValidation() {
        return true;
    }

    public boolean checkLoginSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.LOGIN.ordinal()] + "\\s\\w+");
    }

    public boolean checkMatchHistory() {
        return this.getCommand().matches(this.getStrings()[RequestType.SHOW_MATCH_HISTORY.ordinal()] + "\\s\\w");
    }

    public boolean checkMenuEntrnaceSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.ENTRANCE.ordinal()] + "\\s(Collection|Shop|Battle|Exit|Game)");

    }

    public boolean checkSearchSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SEARCH.ordinal()] + "\\s\\w+");
    }

    public boolean checkSearchTheCollectionSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SEARCH_COLLECTION.ordinal()] + "\\s\\w+");
    }

    public boolean checkDeckSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.CREATE_DECK.ordinal()] + "\\s\\w+");
    }


    public boolean checkToDeckAdditionSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.ADD.ordinal()] + "\\s\\d+\\sto\\sdeck\\s\\w+");

    }

    public boolean checkFromDeckDeletionSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.REMOVE.ordinal()] + "\\s\\d+\\sfrom\\sdeck\\s\\w+");

    }

    public boolean checkValidationSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.VALIDATION.ordinal()] + "\\s\\w+");
    }

    public boolean checkShowDeckSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SHOW_DECK.ordinal()] + "\\s\\w+");

    }

    public boolean checkDeckSelectionSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SELECT_DECK.ordinal()] + "\\s\\w+");

    }

    public boolean checkShowAllDeckSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SHOW_ALL_DECK.ordinal()]);

    }

    public boolean checkBuySyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.BUY.ordinal()] + "\\s\\w+");

    }

    public boolean checkCardSelectionSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SELECTION.ordinal()] + "\\s\\d+");

    }

    public boolean checkSellSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SELL.ordinal()] + "\\s\\d+");

    }

    public boolean checkMoveSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.MOVE.ordinal()] + "\\s\\d+\\s,\\s\\d+\\s\\)");

    }

    public boolean checkAssaultSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.ATTACK.ordinal()] + "\\s\\w+");

    }

    public boolean checkComboSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.COMBO.ordinal()] + "\\s\\w+");

    }

    public boolean checkSPUsageSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.USE_SP.ordinal()] + "\\s\\d+\\s,\\s\\d+\\s\\)");

    }

    public boolean checkCardInsertSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.INSERTION.ordinal()] + "\\s\\w+\\sin" +
                "\\s\\(\\s\\d+\\s,\\s\\d+\\s\\)");
    }

    public boolean checkItemUseSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.USE_ITEM.ordinal()] + "\\s\\d+\\s,\\s\\d+\\s\\)");

    }

    public boolean checkSelectUserSyntax() {
        return this.getCommand().matches(this.getStrings()[RequestType.SELECT_USER.ordinal()] + "\\s\\w+");
    }

    public RequestType getType() {
        for (int i = 0; i < strings.length; i++) {
            try {
                if (this.getSplittedCommand(this.command)[0].equals(strings[i]))
                    return RequestType.values()[i];
            } catch (StringIndexOutOfBoundsException e) {

            }
        }
        return RequestType.NULL;
    }

    public String getCommand() {
        return command;
    }

    public String[] getStrings() {
        return strings;
    }

    public String getNewCommand() {
        this.command = scanner.nextLine();
        return command;
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
                Integer.parseInt(this.getSplittedCommand(input)[this.getSplittedCommand(input).length - 4]),
                Integer.parseInt(this.getSplittedCommand(input)[this.getSplittedCommand(input).length - 2])
        );
    }

    public int[] getComboComradesId(String input) {
        int[] comradesId = new int[this.getSplittedCommand(input).length - 2];
        for (int i = 2; i < this.getSplittedCommand(input).length; i++) {
            comradesId[i - 2] = Integer.parseInt(this.getSplittedCommand(input)[i]);
        }
        return comradesId;
    }

    public int getOppIdInCombo(String input) {
        return Integer.parseInt(this.getSplittedCommand(input)[1]);
    }

    public GameType getGameType(String input) {
        return GameType.valueOf(input.toUpperCase());
    }

    public BattleMode getBattleMode(String input) {
        return BattleMode.valueOf(input.toUpperCase());
    }

    public boolean isBattleMode() {
        return this.command.matches("(KillEnemyHero|Collecting|Flag)");
    }

    public boolean isGameType() {
        return this.command.matches("(SinglePlayer|MultiPlayer)");
    }

    public boolean isProcess() {
        return this.command.matches("(Story|Custom)");
    }

    public Process getProcess(String input) {
        return Process.valueOf(input.toUpperCase());
    }
}
