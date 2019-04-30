package Model;

import java.util.ArrayList;

public class ItemBuff {
    private BuffType type;
    private int power;
    private int turnsCount;
    private String targetCard;
    private Side side;
    private ActivationType activationType;
    private ActivationType casterActivationType;
    private String casterCard;
    private RangeType rangeType;
    private RangeType casterRangeType;

    public ItemBuff(String[] info) {
        this.type = BuffType.valueOf(info[ItemBuffInfoOrder.BUFF_TYPE.ordinal()]);
        this.power = Integer.parseInt(info[ItemBuffInfoOrder.POWER.ordinal()]);
        this.turnsCount = Integer.parseInt(info[ItemBuffInfoOrder.TURN_COUNT.ordinal()]);
        this.targetCard = info[ItemBuffInfoOrder.TARGET_TYPE.ordinal()];
        this.side = Side.valueOf(info[ItemBuffInfoOrder.SIDE.ordinal()]);
        this.activationType = ActivationType.valueOf(info[ItemBuffInfoOrder.ACTIVATION_TYPE.ordinal()]);
        this.rangeType = RangeType.valueOf(info[ItemBuffInfoOrder.RANGE_TYPE.ordinal()]);
        this.casterCard = info[ItemBuffInfoOrder.CASTER.ordinal()];
        this.casterActivationType = ActivationType.valueOf(info[ItemBuffInfoOrder.CASTER_ACTIVATION_TYPE.ordinal()]);
        this.casterRangeType = RangeType.valueOf(info[ItemBuffInfoOrder.CASTER_RANGE_TYPE.ordinal()]);
    }

    public ItemBuff(ItemBuff buff) {
        this.type = buff.type;
        this.power = buff.power;
        this.turnsCount = buff.turnsCount;
        this.targetCard = buff.targetCard;
        this.side = buff.side;
        this.activationType = buff.activationType;
        this.rangeType = buff.rangeType;
        this.casterCard = buff.casterCard;
        this.casterActivationType = buff.casterActivationType;
        this.casterRangeType = buff.casterRangeType;
    }

    public BuffType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public Side getSide() {
        return side;
    }

    public ActivationType getActivationType() {
        return activationType;
    }

    public ActivationType getCasterActivationType() {
        return casterActivationType;
    }

    public String getCasterCard() {
        return casterCard;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    public RangeType getCasterRangeType() {
        return casterRangeType;
    }
}
