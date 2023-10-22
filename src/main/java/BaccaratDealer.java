import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
    ArrayList<Card> deck;

    public void generateDeck(){
        deck= new ArrayList<>();
        String suites[]= {"spades", "diamonds", "hearts", "clubs"};
        for(int i=2; i<10; i++) {//adding the numbered cards
            deck.add(new Card(String.format("%d of %s", i, suites[0]), i));
            deck.add(new Card(String.format("%d of %s", i, suites[1]), i));
            deck.add(new Card(String.format("%d of %s", i, suites[2]), i));
            deck.add(new Card(String.format("%d of %s", i, suites[3]), i));

        }
        String faceCard[]= {"king", "queen", "jack"};
        for(var a: faceCard){//adding the face card (King, Queen, Jack)
            deck.add(new Card(String.format("%s of %s", a, suites[0]), 0));
            deck.add(new Card(String.format("%s of %s", a, suites[1]), 0));
            deck.add(new Card(String.format("%s of %s", a, suites[2]), 0));
            deck.add(new Card(String.format("%s of %s", a, suites[3]), 0));
        }
        //adding aces
        deck.add(new Card(String.format("ace of %s", suites[0]), 1));
        deck.add(new Card(String.format("ace of %s", suites[1]), 1));
        deck.add(new Card(String.format("ace of %s", suites[2]), 1));
        deck.add(new Card(String.format("ace of %s", suites[3]), 1));

        deck.add(new Card(String.format("10 of %s", suites[0]), 0));
        deck.add(new Card(String.format("10 of %s", suites[1]), 0));
        deck.add(new Card(String.format("10 of %s", suites[2]), 0));
        deck.add(new Card(String.format("10 of %s", suites[3]), 0));
        shuffleDeck();

    }
    public ArrayList<Card> dealHand(){
        ArrayList<Card> temp= new ArrayList<>();
        temp.add(this.drawOne());
        temp.add(this.drawOne());
        return temp;
    }

    public Card drawOne(){

        if(deckSize()==0){
            return null;
        }
        return deck.remove(0);
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }
    public int deckSize(){
        return deck.size();
    }

}
