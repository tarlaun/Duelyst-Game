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

    public ItemBuff(String[] info) {
        this.type = BuffType.valueOf(info[ItemBuffInfoOrder.BUFF_TYPE.ordinal()]);
        this.power = Integer.parseInt(info[ItemBuffInfoOrder.POWER.ordinal()]);
        this.turnsCount = Integer.parseInt(info[ItemBuffInfoOrder.TURN_COUNT.ordinal()]);
        this.targetCard = info[ItemBuffInfoOrder.TARGET_TYPE.ordinal()];
        this.side = Side.valueOf(info[ItemBuffInfoOrder.SIDE.ordinal()]);
        this.activationType = ActivationType.valueOf(info[ItemBuffInfoOrder.ACTIVATION_TYPE.ordinal()]);
        this.casterCard = info[ItemBuffInfoOrder.CASTER.ordinal()];
        for (int i = ItemBuffInfoOrder.RANGE_TYPE.ordinal(); i < info.length; i++) {
            this.rangeTypes.add(RangeType.valueOf(info[i]));
        }
    }

    public ItemBuff(ItemBuff buff) {
        this.type = buff.type;
        this.power = buff.power;
        this.turnsCount = buff.turnsCount;
        this.targetCard = buff.targetCard;
        this.side = buff.side;
        this.activationType = buff.activationType;
        this.casterCard = buff.casterCard;
        this.rangeTypes = buff.rangeTypes;
    }
}
