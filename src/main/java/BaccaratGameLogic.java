import java.util.ArrayList;

public class BaccaratGameLogic {
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2){
        int playerTotal= handTotal(hand1);
        int bankerTotal= handTotal(hand2);
        if(playerTotal> bankerTotal){
            return "Player";
        }
        else if(playerTotal== bankerTotal){
            return "Tie";
        }
        else{
            return "Banker";
        }
    }
    public int handTotal(ArrayList<Card> hand){
        int total=0;
       for(var a: hand){
           if(a != null) total+= a.value;
       }
       while(total>=10){
           total= total-10;
       }
       return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card
            playerCard){
        int sum=handTotal(hand);
        if(sum>=7) return false;
       if(sum==0){
           int m=0;
       }
        if(sum <=2) return true;
        if(sum <=6){
            switch(sum){
                case 3:
                    if(playerCard == null) return true;
                    if(playerCard.value != 8)return true;
                    break;
                case 4:
                    if(playerCard == null) return true;
                    if(playerCard.value != 8 && playerCard.value != 9 && playerCard.value != 0 && playerCard.value != 1)return true;
                    break;
                case 5:
                    if(playerCard == null) return true;
                    if(playerCard.value != 0 && playerCard.value != 1 && playerCard.value != 2 && playerCard.value != 3
                            && playerCard.value != 8 && playerCard.value != 9)return true;
                    break;
                case 6:
                    if(playerCard == null) return false;
                    if(playerCard.value == 6||playerCard.value == 7 )return true;
                    break;
            }
        }

        return false;
    }

    public boolean evaluatePlayerDraw(ArrayList<Card> hand){
        int sum=handTotal(hand);

        return sum<=5;
    }



}
