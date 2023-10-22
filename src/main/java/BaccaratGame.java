import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;



public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;

	HashMap<String, Scene> sceneMap= new HashMap<>();
	Scene scene;

	String css;

	HashMap<String, Image> playDeck= new HashMap<>();
	PseudoClass cChips= PseudoClass.getPseudoClass("chips"), playButtons= PseudoClass.getPseudoClass("playButtons");;

	Button chip_100K, chip_50K, chip_40K, chip_20K, chip_10K, options, b1, exit, freshStart, reBet, clearBet, deal;
	Text score;
	Text playerCount= new Text(), bankerCount= new Text(), playerBet= new Text("0"), bankerBet= new Text("0"), tieBet= new Text("0");
	Stage primaryStage;
	BorderPane gamePgBody= new BorderPane();

	VBox chips= new VBox(), stage= new VBox(), controls= new VBox();
	BorderPane stageHeader= makestageHeader(), stageBody1= makeBody1(),
			stageBody2= new BorderPane(), stageFooter= makeStageFooter();
	BorderPane gamePgHeader = new BorderPane();
	VBox gameBox= new VBox(gamePgHeader, gamePgBody);
	public double evaluateWinnings(){

		return 0d;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage= primaryStage;
		visualizeDeck();
		theDealer= new BaccaratDealer();
		gameLogic= new BaccaratGameLogic();
		theDealer.generateDeck();

		primaryStage.setTitle("Welcome to JavaFX");
		css = Objects.requireNonNull(this.getClass().getResource("/assets/style.css")).toExternalForm();

		sceneMap.put("LandingPage", createLandingPage());
		sceneMap.put("GamePage", createGamePage());
		sceneMap.put("OptionPage", createOptionPage());
		b1.setOnAction(e-> changeScene("GamePage"));
		options.setOnAction(e->changeScene("OptionPage"));
		deal.setOnAction(e->{
			playGame();
			setStageBody();
			deal.setDisable(true);
			reBet.setDisable(false);
			clearBet.setDisable(false);

		});
		reBet.setOnAction(e->{
			unMountStage();
			playGame();
			setStageBody();
		});
		exit.setOnAction(e-> Platform.exit());
//		freshStart.setOnAction();
		changeScene("LandingPage");


		
	}
	public Scene createLandingPage(){
		Text t1= new Text("Baccarat");
		b1= new Button("Play Game");
		t1.setId("t1");
		t1.setFill(Color.web("#ffd700"));
		VBox homeBox= new VBox(t1, b1);
		homeBox.setSpacing(150);
		homeBox.setAlignment(Pos.CENTER);
		homeBox.setId("homeBox");
		homeBox.setMaxHeight(500);
		homeBox.setMaxWidth(500);
		BorderPane root = new BorderPane();
		root.setCenter(homeBox);
		root.setId("root");
		return new Scene(root, 1200,700);

	}
	public void changeScene(String str){
		scene= sceneMap.get(str);
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public Scene createGamePage(){
		score= new Text("$99999");
		options = new Button("Option");

		gamePgHeader.setLeft(score);
		gamePgHeader.setRight(options);

		stageHeader.setId("stageHeader");

		gamePgBody.setLeft(chips);
		gamePgBody.setCenter(stage);
		gamePgBody.setRight(controls);
		initializeChips();
		initializeControls();
		controls.getChildren().setAll(reBet, clearBet, deal);
		controls.setSpacing(100);
		controls.setId("controls");
		chips.setSpacing(30);
		chips.setId("chips");

		chips.getChildren().addAll(chip_100K,chip_50K, chip_40K, chip_20K, chip_10K);
		stage.getChildren().setAll(stageHeader, stageBody1, stageBody2, stageFooter);
		stageBody2.setMinHeight(200);
		stageFooter.setId("stageFooter");

		gameBox.setId("gameBox");
//		deal.setDisable(true);
		reBet.setDisable(true);
		clearBet.setDisable(true);
		return new Scene(gameBox, 1200, 700);
	}
	public Scene createOptionPage(){
		Text t1= new Text("Baccarat");
		Text t2= new Text("Options");
		exit= new Button("Exit Game");
		freshStart= new Button("FreshStart");
		t1.setId("t1");
		t1.setFill(Color.web("#ffd700"));
		VBox homeBox= new VBox(t1, t2, freshStart, exit);
		homeBox.setSpacing(90);
		homeBox.setAlignment(Pos.CENTER);
		homeBox.setId("homeBox");
		homeBox.setMaxHeight(500);
		homeBox.setMaxWidth(500);
		BorderPane gameBox = new BorderPane();
		gameBox.setCenter(homeBox);
		return new Scene(gameBox, 1200, 700);
	}
	public void initializeChips(){
		chip_100K= new Button("100K");
		chip_50K= new Button("50K");
		chip_40K= new Button("40K");
		chip_20K= new Button("20K");
		chip_10K= new Button("10K");
		chip_100K.setId("chip_100K");
		chip_50K.setId("chip_50K");
		chip_40K.setId("chip_40K");
		chip_20K.setId("chip_20K");
		chip_10K.setId("chip_10K");
		chip_100K.pseudoClassStateChanged(cChips, true);
		chip_50K.pseudoClassStateChanged(cChips, true);
		chip_40K.pseudoClassStateChanged(cChips, true);
		chip_20K.pseudoClassStateChanged(cChips, true);
		chip_10K.pseudoClassStateChanged(cChips, true);

	}
	public void initializeControls(){
		reBet= new Button("REBET");
		clearBet= new Button("CLEAR BET");
		deal = new Button("DEAL");
		reBet.setId("rebet");
		clearBet.setId("clearBet");
		deal.setId("deal");
		reBet.pseudoClassStateChanged(playButtons, true);
		clearBet.pseudoClassStateChanged(playButtons, true);
		deal.pseudoClassStateChanged(playButtons, true);
	}
	public  BorderPane makestageHeader(){
		BorderPane p1= new BorderPane();
		Text player= new Text("Player:");
		Text banker= new Text("Banker: ");
		player.setId("player");
		banker.setId("banker");

		HBox stageHeader_left= new HBox(player,playerCount ), stageHeader_right= new HBox(banker, bankerCount);
		p1.setLeft(stageHeader_left);
		p1.setRight(stageHeader_right);
		return p1;

	}
	public BorderPane makeBody1(){
		BorderPane p1= new BorderPane();
		ImageView deck= new ImageView();
		deck.setImage(new Image("assets/images/deckback.png"));
		deck.setId("deck");
		deck.setFitHeight(GeneralUtil.cardLength);
		deck.setFitWidth(GeneralUtil.cardWidth);
		p1.setCenter(deck);

		return p1;
	}

	public BorderPane makeStageFooter(){
		BorderPane p1= new BorderPane();
		Text player= new Text("Player Bet:");
		HBox left= new HBox(player, playerBet);
		left.setMinWidth(325);
		Text tie= new Text("Tie Bet:");
		HBox center= new HBox(tie, tieBet);
		Text banker= new Text("Banker Bet:");
		HBox right= new HBox(banker,bankerBet);
		p1.setLeft(left);
		p1.setCenter(center);
		p1.setRight(right);

		return p1;
	}

	public void playGame(){


		playerHand= theDealer.dealHand();
		bankerHand= theDealer.dealHand();
		Boolean playerdrawthird= gameLogic.evaluatePlayerDraw(playerHand);
		if(playerdrawthird){
			playerHand.add(theDealer.drawOne());
			gameLogic.evaluateBankerDraw(bankerHand, playerHand.get(2));
		}
		else{
			gameLogic.evaluateBankerDraw(bankerHand, null);
			bankerHand.add(theDealer.drawOne());
		}
//		String winner= gameLogic.whoWon(playerHand, bankerHand);


	}
	public void visualizeDeck(){

		String suites[]= {"spades", "diamonds", "hearts", "clubs"};
		for(int i=2; i<10; i++) {//adding the numbered cards
			playDeck.put(String.format("%d of %s", i, suites[0]),new Image(String.format("assets/images/cardspng/%d_of_%s.png", i, suites[0])));
			playDeck.put(String.format("%d of %s", i, suites[1]),new Image(String.format("assets/images/cardspng/%d_of_%s.png", i, suites[1])));
			playDeck.put(String.format("%d of %s", i, suites[2]),new Image(String.format("assets/images/cardspng/%d_of_%s.png", i, suites[2])));
			playDeck.put(String.format("%d of %s", i, suites[3]),new Image(String.format("assets/images/cardspng/%d_of_%s.png", i, suites[3])));

		}
		String faceCard[]= {"king", "queen", "jack"};
		for(var a: faceCard){//adding the face card (King, Queen, Jack)
			playDeck.put(String.format("%s of %s", a, suites[0]),new Image(String.format("assets/images/cardspng/%s_of_%s.png", a, suites[0])));
			playDeck.put(String.format("%s of %s", a, suites[1]),new Image(String.format("assets/images/cardspng/%s_of_%s.png", a, suites[1])));
			playDeck.put(String.format("%s of %s", a, suites[2]),new Image(String.format("assets/images/cardspng/%s_of_%s.png", a, suites[2])));
			playDeck.put(String.format("%s of %s", a, suites[3]),new Image(String.format("assets/images/cardspng/%s_of_%s.png", a, suites[3])));
		}
		for(int i=0; i<4; i++){
			playDeck.put(String.format("ace of %s", suites[i]),new Image(String.format("assets/images/cardspng/ace_of_%s.png", suites[i])));

		}
		for(int i=0; i<4; i++){
			playDeck.put(String.format("10 of %s", suites[i]),new Image(String.format("assets/images/cardspng/10_of_%s.png", suites[i])));

		}
//		playDeck.put(String.format("ace of %s", suites[0]),new Image(String.format("ace_of_%s.png", suites[0])));
//		playDeck.put(String.format("ace of %s", suites[1]),new Image(String.format("ace_of_%s.png", suites[1])));
//		playDeck.put(String.format("ace of %s", suites[2]),new Image(String.format("ace_of_%s.png", suites[2])));
//		playDeck.put(String.format("ace of %s", suites[3]),new Image(String.format("ace_of_%s.png", suites[3])));
//
//		playDeck.put(String.format("10 of %s", suites[0]),new Image(String.format("10_of_%s.png", suites[0])));
//		playDeck.put(String.format("10 of %s", suites[1]),new Image(String.format("10_of_%s.png", suites[1])));
//		playDeck.put(String.format("10 of %s", suites[2]),new Image(String.format("10_of_%s.png", suites[2])));
//		playDeck.put(String.format("10 of %s", suites[3]),new Image(String.format("10_of_%s.png", suites[3])));
//

	}
	void setStageBody(){//makes edits to the stage body
		ImageView playerCard1= new ImageView();
		ImageView playerCard2= new ImageView();
		ImageView playerCard3;
		playerCard1.setImage(playDeck.get(playerHand.get(0).suite));
		playerCard2.setImage(playDeck.get(playerHand.get(1).suite));
		playerCard1.setFitHeight(GeneralUtil.cardLength);
		playerCard1.setFitWidth(GeneralUtil.cardWidth);
		playerCard2.setFitHeight(GeneralUtil.cardLength);
		playerCard2.setFitWidth(GeneralUtil.cardWidth);
		HBox pHand= new HBox(playerCard1, playerCard2);
		pHand.setSpacing(5);
		ImageView bankerCard1= new ImageView();
		ImageView bankerCard2= new ImageView();
		ImageView bankerCard3;
		bankerCard1.setImage(playDeck.get(bankerHand.get(0).suite));
		bankerCard2.setImage(playDeck.get(bankerHand.get(1).suite));
		bankerCard1.setFitHeight(GeneralUtil.cardLength);
		bankerCard1.setFitWidth(GeneralUtil.cardWidth);
		bankerCard2.setFitHeight(GeneralUtil.cardLength);
		bankerCard2.setFitWidth(GeneralUtil.cardWidth);
		HBox pHand2= new HBox(bankerCard1, bankerCard2);
		pHand2.setSpacing(5);
		stageBody1.setLeft(pHand);
		stageBody1.setRight(pHand2);
		if(playerHand.size()>2){
			playerCard3= new ImageView(playDeck.get(playerHand.get(2).suite));
			playerCard3.setFitHeight(GeneralUtil.cardLength);
			playerCard3.setFitWidth(GeneralUtil.cardWidth);
			stageBody2.setLeft(playerCard3);
		}
		if(bankerHand.size()>2){
			bankerCard3= new ImageView(playDeck.get(bankerHand.get(2).suite));
			bankerCard3.setFitHeight(GeneralUtil.cardLength);
			bankerCard3.setFitWidth(GeneralUtil.cardWidth);
			stageBody2.setRight(bankerCard3);
		}
		playerCount.setId("Count");
		bankerCount.setId("Count");
		playerCount.setText(String.format("%d", gameLogic.handTotal(playerHand)));
		bankerCount.setText(String.format("%d", gameLogic.handTotal(bankerHand)));



	}
	public void unMountStage(){

		stageBody1.setRight(null);
		stageBody1.setLeft(null);
		stageBody2.setLeft(null);
		stageBody2.setRight(null);
		stageBody2.setCenter(null);

	}
}
