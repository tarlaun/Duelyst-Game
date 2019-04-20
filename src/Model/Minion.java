package Model;

public class Minion extends Card {

    private int [][] attackCount = new int [40][2];

    public int[][] getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(int i , int j , int k) {
        this.attackCount[i][j] = k ;
    }

    public Minion(int id, String[] info, Buff... buffs) {
        super(id, info);
    }


}


