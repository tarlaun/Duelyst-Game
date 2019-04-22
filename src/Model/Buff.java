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
    private ArrayList<Coordinate> effectArea = new ArrayList<>();

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
