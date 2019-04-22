package Model;

import java.util.ArrayList;

public class ItemBuff {
    private BuffType type;
    private int power;
    private int turnsCount;
    private String targetCard;
    private Side side;
    private ActivationType activationType;
    private String casterCard;
    private ArrayList<RangeType> rangeTypes = new ArrayList<>();
}
