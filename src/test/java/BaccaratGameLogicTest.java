import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
}
