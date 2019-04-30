package Model;

public class Minion extends Card {
    public Minion(String info) {
        super(info.split(Constants.CARD_INFO_SPLITTER));

    private int [][] attackCount = new int [40][2];


    public int getAttackCount(int id) {
        for (int i = 0; i <40 ; i++) {
            if(this.attackCount[i][0]==id){
                return attackCount[i][1];
            }
        }
        return 0;
    }

    public void setAttackCount(int i , int j , int k) {
        this.attackCount[i][j] = k ;
    }

}


