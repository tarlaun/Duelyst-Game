package Model;

public class Minion extends Card {
    private int[][] attackCount = new int[40][2];

    public Minion(String[] info) {
        this.setName( info[MainInfoOrder.NAME.ordinal()]);
        this.setPrice(Integer.parseInt(info[MainInfoOrder.PRICE.ordinal()]));
        this.setAssaultPower(Integer.parseInt(info[MainInfoOrder.AP.ordinal()]));
        this.setOriginalAssaultPower(this.getAssaultPower());
        this.setMaxPossibleMoving( Integer.parseInt(info[MainInfoOrder.MAX_MOVE.ordinal()]));
        this.setHealthPoint(Integer.parseInt(info[MainInfoOrder.HP.ordinal()]));
        this.setMaxRange(Integer.parseInt(info[MainInfoOrder.MAX_RANGE.ordinal()]));
        this.setManaPoint(Integer.parseInt(info[MainInfoOrder.MANA.ordinal()]));
        this.setAssaultType(AssaultType.valueOf(info[MainInfoOrder.ASSAULT_TYPE.ordinal()]));
        for (int i = MainInfoOrder.BUFF.ordinal(); i < info.length; i++) {
            this.buffs.add(new Buff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
        }
    }

    public int getAttackCount(int id) {
        for (int i = 0; i < 40; i++) {
            if (this.attackCount[i][0] == id) {
                return attackCount[i][1];
            }
        }
        return 0;
    }

    public void setAttackCount(int i, int j, int k) {
        this.attackCount[i][j] = k;
    }


}


