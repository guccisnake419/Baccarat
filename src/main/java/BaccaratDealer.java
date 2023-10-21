import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
    ArrayList<Card> deck;

    public void generateDeck(){
        String suites[]= {"Spades", "Diamonds", "Hearts", "Clubs"};
        for(int i=2; i<10; i++) {//adding the numbered cards
            deck.add(new Card(suites[0], i));
            deck.add(new Card(suites[1], i));
            deck.add(new Card(suites[2], i));
            deck.add(new Card(suites[3], i));

        }
        for(int i=0; i< 3; i++){//adding the face card (King, Queen Jack)
            deck.add(new Card(suites[0], 0));
            deck.add(new Card(suites[1], 0));
            deck.add(new Card(suites[2], 0));
            deck.add(new Card(suites[3], 0));
        }
        //adding aces
        deck.add(new Card(suites[0], 0));
        deck.add(new Card(suites[1], 0));
        deck.add(new Card(suites[2], 0));
        deck.add(new Card(suites[3], 0));


    }
    public ArrayList<Card> dealHand(){
        ArrayList<Card> temp= new ArrayList<>();
        temp.add(this.drawOne());
        temp.add(this.drawOne());
        return temp;
    }

    public Card drawOne(){


        return deck.remove(0);
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }
    public int deckSize(){
        return deck.size();
    }

}
