import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;



class BaccaratGameLogicTest {
     @Test
     void evaluateBankerDrawTest(){
         ArrayList<Card>hand=new ArrayList<>();
         hand.add(new Card("10 of spades",0));
         hand.add(new Card("k of diamonds", 0));
         BaccaratGameLogic logic= new BaccaratGameLogic();
         assertEquals(logic.evaluateBankerDraw(hand, null), true);
     }

     @Test
     void evaluateBankerDrawTest2(){
         ArrayList<Card>hand=new ArrayList<>();
         hand.add(new Card("7 of spades",7));
         BaccaratGameLogic logic= new BaccaratGameLogic();
         assertEquals(logic.evaluateBankerDraw(hand, null), false);
     }



    @Test
    void whoWonTestPlayerWin(){
         BaccaratGameLogic logic = new BaccaratGameLogic();
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> bankerHand = new ArrayList<>();
        playerHand.add(new Card("5 of spades", 5));
        bankerHand.add(new Card("3 of spades", 3));
        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("Player", winner);

    }

    @Test
    void whoWonTestBankerWin(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> bankerHand = new ArrayList<>();
        playerHand.add(new Card("10 of spades", 10));
        bankerHand.add(new Card("3 of spades", 3));
        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("Banker", winner);

    }

    @Test
    void whoWonTestTie(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> bankerHand = new ArrayList<>();
        playerHand.add(new Card("3 of hearts", 3));
        bankerHand.add(new Card("3 of spades", 3));
        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("Tie", winner);

    }

    @Test
    void handTotalTest(){
        ArrayList<Card> hand = new ArrayList<>();
        BaccaratGameLogic logic = new BaccaratGameLogic();
        hand.add(new Card("3 of hearts", 3));
        hand.add(new Card("5 of spades", 5));
        int total = logic.handTotal(hand);
        assertEquals(8, total);
    }

    @Test
    void handTotalTestAboveTen(){
        ArrayList<Card> hand = new ArrayList<>();
        BaccaratGameLogic logic = new BaccaratGameLogic();
        hand.add(new Card("8 of hearts", 8));
        hand.add(new Card("5 of spades", 5));
        int total = logic.handTotal(hand);
        assertEquals(3, total);
    }

    @Test
    void evaluatePlayerDrawTest(){
        ArrayList<Card> hand = new ArrayList<>();
        BaccaratGameLogic logic = new BaccaratGameLogic();
        hand.add(new Card("4 of hearts", 4));
        assertTrue(logic.evaluatePlayerDraw(hand));
    }

    @Test
    void evaluatePlayerDrawTest2(){
        ArrayList<Card> hand = new ArrayList<>();
        BaccaratGameLogic logic = new BaccaratGameLogic();
        hand.add(new Card("6 of hearts", 6));
        assertFalse(logic.evaluatePlayerDraw(hand));
    }


}
