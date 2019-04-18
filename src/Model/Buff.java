package Model;

import java.util.ArrayList;

public class Buff {
    BuffType type;
    int power;
    String cardType;
    AffectionTarget target;
    DispelType dispelType;
    ActivationType activationType;
    ArrayList<Coordinate> effectArea = new ArrayList<>();
    int affectionTurnCount;
}
