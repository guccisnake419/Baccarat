import java.util.ArrayList;

public class BaccaratGameLogic {
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2){
        int playerTotal= handTotal(hand1);
        int bankerTotal= handTotal(hand2);
        return "null";
    }
    public int handTotal(ArrayList<Card> hand){
        int total=0;
       for(var a: hand){
           total+= a.value;
       }
       return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card
            playerCard){
        return false;
    }

    public boolean evaluatePlayerDraw(ArrayList<Card> hand){
        return false;
    }



}
