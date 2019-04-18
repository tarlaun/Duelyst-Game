package Model;

import java.util.ArrayList;

public class Buff {
    BuffType type;
    int power;
    String targetType;
    ModifiedAttribute attribute;
    DispelType dispelType;
    ActivationType activationType;
    int turnCount;
    ArrayList<Coordinate> effectArea = new ArrayList<>();

    public Buff(String[] info) {
        this.type = BuffType.valueOf(info[BuffInfoOrder.BUFF_TYPE.ordinal()]);
        this.power = Integer.parseInt(info[BuffInfoOrder.POWER.ordinal()]);
        this.targetType = info[BuffInfoOrder.TARGET_TYPE.ordinal()];
        this.attribute = ModifiedAttribute.valueOf(info[BuffInfoOrder.MODIFIED_ATTRIBUTE.ordinal()]);
        this.dispelType = DispelType.valueOf(info[BuffInfoOrder.DISPEL_TYPE.ordinal()]);
        this.activationType = ActivationType.valueOf(info[BuffInfoOrder.ACTIVATION_TYPE.ordinal()]);
        this.turnCount = Integer.parseInt(info[BuffInfoOrder.TURN_COUNT.ordinal()]);
        for (int i = BuffInfoOrder.EFFECT_AREA.ordinal(); i < info.length; i += 2) {
            this.effectArea.add(new Coordinate(Integer.parseInt(info[i]), Integer.parseInt(info[i + 1])));
        }
    }
}
