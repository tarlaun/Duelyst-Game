package Model;

import java.util.ArrayList;

public class Buff {
    private BuffType type;
    private int power;
    private String targetType;
    private DispelType dispelType;
    private ActivationType activationType;
    private int turnCount;
    private Side side;
    private ModifiedAttribute attribute;
    private ArrayList<Coordinate> effectArea = new ArrayList<>();

    public void setType(BuffType type) {
        this.type = type;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setAttribute(ModifiedAttribute attribute) {
        this.attribute = attribute;
    }

    public void setDispelType(DispelType dispelType) {
        this.dispelType = dispelType;
    }

    public void setActivationType(ActivationType activationType) {
        this.activationType = activationType;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setEffectArea(ArrayList<Coordinate> effectArea) {
        this.effectArea = effectArea;
    }

    public BuffType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public String getTargetType() {
        return targetType;
    }

    public ModifiedAttribute getAttribute() {
        return attribute;
    }

    public DispelType getDispelType() {
        return dispelType;
    }

    public ActivationType getActivationType() {
        return activationType;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public Side getSide() {
        return side;
    }

    public ArrayList<Coordinate> getEffectArea() {
        return effectArea;
    }


    public Buff(String[] info) {
        this.type = BuffType.valueOf(info[BuffInfoOrder.BUFF_TYPE.ordinal()]);
        this.power = Integer.parseInt(info[BuffInfoOrder.POWER.ordinal()]);
        this.targetType = info[BuffInfoOrder.TARGET_TYPE.ordinal()];
        this.dispelType = DispelType.valueOf(info[BuffInfoOrder.DISPEL_TYPE.ordinal()]);
        this.activationType = ActivationType.valueOf(info[BuffInfoOrder.ACTIVATION_TYPE.ordinal()]);
        this.turnCount = Integer.parseInt(info[BuffInfoOrder.TURN_COUNT.ordinal()]);
        this.side = Side.valueOf(info[BuffInfoOrder.SIDE.ordinal()]);
        for (int i = BuffInfoOrder.EFFECT_AREA.ordinal(); i < info.length; i += 2) {
            this.effectArea.add(new Coordinate(Integer.parseInt(info[i]), Integer.parseInt(info[i + 1])));
        }
    }

    public Buff(Buff buff) {
        this.type = buff.type;
        this.power = buff.power;
        this.targetType = buff.targetType;
        this.dispelType = buff.dispelType;
        this.activationType = buff.activationType;
        this.turnCount = buff.turnCount;
        this.side = buff.side;
        this.effectArea = buff.effectArea;
    }
}
