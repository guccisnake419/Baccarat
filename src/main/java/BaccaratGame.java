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
import javafx.scene.control.TextField;
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

	Button chip_100K, chip_50K, chip_40K, chip_20K, chip_10K, options, b1, exit, freshStart, reBet, clearBet, deal, backbutton;
	Button player= new Button("Player Bet"), banker=new Button("Banker Bet"), tie= new Button("Tie Bet:");
	Text score, addition;
	Text playerCount= new Text(), bankerCount= new Text(), playerBet= new Text("0"), bankerBet= new Text("0"), tieBet= new Text("0");
	Stage primaryStage;
	TextArea message= new TextArea();
	BorderPane gamePgBody= new BorderPane();

	VBox chips= new VBox(), stage= new VBox(), controls= new VBox();
	BorderPane stageHeader= makestageHeader(), stageBody1= makeBody1(),
			stageBody2= makeBody2(), stageFooter= makeStageFooter();
	BorderPane gamePgHeader = new BorderPane();
	VBox gameBox= new VBox(gamePgHeader, gamePgBody);
	String winner;
	int prevBet=-1;
	public double evaluateWinnings(){
		double gains=0;
		double loss=0;
		switch (winner) {
			case "Player":
				gains = Integer.valueOf(playerBet.getText());
				loss= Integer.valueOf(bankerBet.getText())+ Integer.valueOf(tieBet.getText());
				break;
			case "Banker":
				gains = Integer.valueOf(bankerBet.getText());
				gains= (0.95* gains);
				loss= Integer.valueOf(playerBet.getText())+ Integer.valueOf(tieBet.getText());
				break;
			case "Tie":
				gains = Integer.valueOf(tieBet.getText());
				gains = (8* gains);
				loss= Integer.valueOf(playerBet.getText())+ Integer.valueOf(bankerBet.getText());
				break;

		}

		return gains-loss;
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
		currentBet=0;
		totalWinnings=0;
		player.setDisable(false);
		tie.setDisable(false);
		banker.setDisable(false);

		primaryStage.setTitle("Baccarat Game");
		css = Objects.requireNonNull(this.getClass().getResource("/assets/style.css")).toExternalForm();

		sceneMap.put("LandingPage", createLandingPage());
		sceneMap.put("GamePage", createGamePage());
		sceneMap.put("OptionPage", createOptionPage());
		b1.setOnAction(e-> changeScene("GamePage"));
		options.setOnAction(e->changeScene("OptionPage"));
		deal.setOnAction(e->{
			winner = playGame();
			setStageBody();

			deal.setDisable(true);
			player.setDisable(true);
			tie.setDisable(true);
			banker.setDisable(true);
			chip_100K.setDisable(true);
			chip_50K.setDisable(true);
			chip_40K.setDisable(true);
			chip_20K.setDisable(true);
			chip_10K.setDisable(true);

			reBet.setDisable(false);
			clearBet.setDisable(false);

		});
//		freshStart.setOnAction(e->);
		reBet.setOnAction(e->{
			unMountStage();
			winner =playGame();
			setStageBody();
		});
		clearBet.setOnAction(e->{
			handleClearBet();

		});

		player.setOnAction(e->{handleBet(playerBet, 0);});
		tie.setOnAction(e->{handleBet(tieBet, 1);});
		banker.setOnAction(e->{handleBet(bankerBet, 2);});
		chip_100K.setOnAction(e->handleChips(100000));
		chip_50K.setOnAction(e->handleChips(50000));
		chip_40K.setOnAction(e->handleChips(40000));
		chip_20K.setOnAction(e->handleChips(20000));
		chip_10K.setOnAction(e->handleChips(10000));

		backbutton.setOnAction(e->{
			changeScene("GamePage");
		});
		exit.setOnAction(e-> Platform.exit());
		freshStart.setOnAction(e-> {
			try {
				resetAll();
				start(new Stage());
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});
		changeScene("LandingPage");


		
	}
	public void handleBet(Text t1, int num){
		message.setText("Select an Amount");
		chip_100K.setDisable(false);
		chip_50K.setDisable(false);
		chip_40K.setDisable(false);
		chip_20K.setDisable(false);
		chip_10K.setDisable(false);
		prevBet= num;
	}

	public void handleChips(int num){
		int prev_bet=0;
		if (prevBet== -1){
			message.setText("Make Sure to Select a Bet First");
			return;
		}

		if(prevBet==0){//add num to playerBet and to currentBet
			prev_bet= Integer.valueOf(playerBet.getText());
			playerBet.setText(String.valueOf((num+prev_bet)));
			currentBet= currentBet+num;

			tie.setDisable(true);
			banker.setDisable(true);
		} else if (prevBet==1) {//add num to tieBet and to currentBet
			prev_bet= Integer.valueOf(tieBet.getText());
			tieBet.setText(String.valueOf((num+prev_bet)));
			currentBet= currentBet+num;
			player.setDisable(true);
			banker.setDisable(true);

		}else if(prevBet==2){//add num to bankerBet...
			prev_bet= Integer.valueOf(bankerBet.getText());
			bankerBet.setText(String.valueOf((num+prev_bet)));
			currentBet= currentBet+num;
			player.setDisable(true);
			tie.setDisable(true);
		}
		message.setText(null);
		deal.setDisable(false);
		clearBet.setDisable(false);
		message.setText("Click Deal to Start");


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
		score.setId("scores");
		score.setFill(Color.web("#ffd700"));
		options = new Button("Option");
		addition= new Text();
		HBox headerLeft= new HBox(score,addition);
		headerLeft.setSpacing(5);
		gamePgHeader.setLeft(headerLeft);
		gamePgHeader.setRight(options);
		gamePgHeader.setId("gamePgHeader");

		stageHeader.setId("stageHeader");

		gamePgBody.setLeft(chips);
		gamePgBody.setCenter(stage);
		gamePgBody.setRight(controls);
		initializeChips();
		initializeControls();
		controls.getChildren().setAll(reBet, clearBet, deal);
		controls.setSpacing(75);

		controls.setId("controls");
		chips.setSpacing(20);
		chips.setId("chips");

		chips.getChildren().addAll(chip_100K,chip_50K, chip_40K, chip_20K, chip_10K);
		stage.getChildren().setAll(stageHeader, stageBody1, stageBody2, stageFooter);
		stageBody2.setMinHeight(200);
		stageFooter.setId("stageFooter");

		gameBox.setId("gameBox");
		deal.setDisable(true);
		reBet.setDisable(true);
		clearBet.setDisable(true);
		chip_100K.setDisable(true);
		chip_50K.setDisable(true);
		chip_40K.setDisable(true);
		chip_20K.setDisable(true);
		chip_10K.setDisable(true);
		return new Scene(gameBox, 1200, 700);
	}
	public Scene createOptionPage(){
		Text t1= new Text("Baccarat");
		Text t2= new Text("Options");
		exit= new Button("Exit Game");
		freshStart= new Button("FreshStart");
		backbutton= new Button("Go back");
		t1.setId("t1");
		t1.setFill(Color.web("#ffd700"));
		VBox homeBox= new VBox(t1, t2,backbutton, freshStart, exit);
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
	public BorderPane makeBody2(){
		BorderPane p1= new BorderPane();
		message.setText("Select a Bet First");
		message.setMaxWidth(400);
		message.setId("message");
		message.setEditable(false);
		p1.setCenter(message);
		return p1;
	}


	public BorderPane makeStageFooter(){
		BorderPane p1= new BorderPane();

		HBox left= new HBox(player, playerBet);
		left.setMinWidth(325);

		HBox center= new HBox(tie, tieBet);

		HBox right= new HBox(banker,bankerBet);
		bankerBet.setId("bets");
		playerBet.setId("bets");
		tieBet.setId("bets");
		p1.setLeft(left);

		p1.setCenter(center);
		p1.setRight(right);

		return p1;
	}

	public String playGame(){

		if(theDealer.deckSize()<6) theDealer.generateDeck();
		playerHand= theDealer.dealHand();
		bankerHand= theDealer.dealHand();
		if(gameLogic.handTotal(playerHand)==8 ||gameLogic.handTotal(playerHand)==9||gameLogic.handTotal(bankerHand)==8 ||gameLogic.handTotal(bankerHand)==9){
			return	gameLogic.whoWon(playerHand, bankerHand);
		}
		Boolean playerdrawthird= gameLogic.evaluatePlayerDraw(playerHand);
		if(playerdrawthird){
			playerHand.add(theDealer.drawOne());
			if(gameLogic.evaluateBankerDraw(bankerHand, playerHand.get(2)))
				bankerHand.add(theDealer.drawOne());;

		}
		else{

			if(gameLogic.evaluateBankerDraw(bankerHand, null))
				bankerHand.add(theDealer.drawOne());;

		}

		return	gameLogic.whoWon(playerHand, bankerHand);


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
		ImageView playerCard3= new ImageView();
		playerCard3.setImage(null);
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
		ImageView bankerCard3= new ImageView();
		bankerCard3.setImage(null);
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

		if(playerHand.size()>2 && playerHand.get(2)!= null){
//			PauseTransition pause = new PauseTransition(Duration.seconds(1));
//			pause.setOnFinished(event ->
//					label.setText("Finished: 1 second elapsed");
//);
			playerCard3= new ImageView(playDeck.get(playerHand.get(2).suite));

		}
		if(bankerHand.size()>2 && bankerHand.get(2) != null){

			bankerCard3= new ImageView(playDeck.get(bankerHand.get(2).suite));

		}
		playerCard3.setFitHeight(GeneralUtil.cardLength);
		playerCard3.setFitWidth(GeneralUtil.cardWidth);
		bankerCard3.setFitHeight(GeneralUtil.cardLength);
		bankerCard3.setFitWidth(GeneralUtil.cardWidth);
		if(playerCard3.getImage()!= null|| bankerCard3.getImage()!= null){
			PauseTransition pause= new PauseTransition(Duration.seconds(1));
			ImageView finalPlayerCard = playerCard3;
			ImageView finalBankerCard = bankerCard3;
			pause.setOnFinished(e->{
				thirdcardfun(finalPlayerCard, finalBankerCard);
			});


			pause.play();
			return;
		}


		stageBody2.setLeft(playerCard3);

		stageBody2.setRight(bankerCard3);
		playerCount.setId("Count");
		bankerCount.setId("Count");

		playerCount.setText(String.format("%d", gameLogic.handTotal(playerHand)));
		bankerCount.setText(String.format("%d", gameLogic.handTotal(bankerHand)));
		String winMessage;
		if(winner== "Player"){
			winMessage= "Player Wins";
		}
		else if(winner=="Banker"){
			winMessage= "Banker Wins";
		}
		else {
			winMessage= "Tie";
		}
		message.setText(String.format("Player Total: %s Banker Total: %s\n%s\n",playerCount.getText(),bankerCount.getText(), winMessage));
		String sStr= score.getText();
		double nScore=0d;
		double winnings= evaluateWinnings();
		sStr= sStr.replace("$","");
		if(sStr.contains("-")){
			sStr= sStr.replace("-", "");
			nScore= Integer.parseInt(sStr)*-1 + winnings;

		}else nScore= Integer.parseInt(sStr)+ winnings;
		if(nScore<0){
			score.setText(String.format("-$%d", (int)nScore *(-1)));
		}
		else score.setText(String.format("$%d", (int)nScore));
		addition.setId("additionsGreater");

		if(winnings>0){
			addition.setText(String.format("+%d", (int)winnings));

			addition.setFill(Color.web("#00ff00"));

		}
		else {
			addition.setText(String.format("%d", (int)winnings));
			addition.setFill(Color.web("#ff0000"));

		}

	}
	public void thirdcardfun(ImageView playerCard3, ImageView bankerCard3){

		stageBody2.setLeft(playerCard3);

		stageBody2.setRight(bankerCard3);
		stageBody2.setLeft(playerCard3);

		stageBody2.setRight(bankerCard3);
		playerCount.setId("Count");
		bankerCount.setId("Count");

		playerCount.setText(String.format("%d", gameLogic.handTotal(playerHand)));
		bankerCount.setText(String.format("%d", gameLogic.handTotal(bankerHand)));
		String winMessage;
		if(winner== "Player"){
			winMessage= "Player Wins";
		}
		else if(winner=="Banker"){
			winMessage= "Banker Wins";
		}
		else {
			winMessage= "Tie";
		}
		message.setText(String.format("Player Total: %s Banker Total: %s\n%s\n",playerCount.getText(),bankerCount.getText(), winMessage));
		String sStr= score.getText();
		double nScore=0d;
		double winnings= evaluateWinnings();
		sStr= sStr.replace("$","");
		if(sStr.contains("-")){
			sStr= sStr.replace("-", "");
			nScore= Integer.parseInt(sStr)*-1 + winnings;

		}else nScore= Integer.parseInt(sStr)+ winnings;
		if(nScore<0){
			score.setText(String.format("-$%d", (int)nScore *(-1)));
		}
		else score.setText(String.format("$%d", (int)nScore));
		addition.setId("additionsGreater");

		if(winnings>0){
			addition.setText(String.format("+%d", (int)winnings));

			addition.setFill(Color.web("#00ff00"));

		}
		else {
			addition.setText(String.format("%d", (int)winnings));
			addition.setFill(Color.web("#ff0000"));

		}

	}
	public void unMountStage(){

		playerCount.setText(null);
		bankerCount.setText(null);
		stageBody1.setRight(null);
		stageBody1.setLeft(null);
		stageBody2.setLeft(null);
		stageBody2.setRight(null);
		message.setText(null);
		addition.setText(null);

	}

	public void handleClearBet(){
		unMountStage();
		tie.setDisable(false);
		player.setDisable(false);
		banker.setDisable(false);
		playerBet.setText("0");
		tieBet.setText("0");
		bankerBet.setText("0");
		prevBet=-1;
		currentBet=0;
		deal.setDisable(true);
		reBet.setDisable(true);
		clearBet.setDisable(true);
		chip_100K.setDisable(true);
		chip_50K.setDisable(true);
		chip_40K.setDisable(true);
		chip_20K.setDisable(true);
		chip_10K.setDisable(true);
		message.setText("Select a Bet First");

	}
	public void resetAll(){
//		playerHand=null;
//		bankerHand=null;
//		theDealer= null;
//		theDealer= null;
//		gameLogic= null;
//		currentBet= 0d;
//		totalWinnings= 0d;
		sceneMap= new HashMap<>();
		scene= null;
		css= null;
		playDeck= new HashMap<>();
		playerCount= new Text();
		bankerCount= new Text();
		playerBet= new Text("0");
		bankerBet= new Text("0");
		tieBet= new Text("0");
		message= new TextArea();
		gamePgBody= new BorderPane();
		chips= new VBox(); stage= new VBox(); controls= new VBox();
		stageHeader= makestageHeader(); stageBody1= makeBody1();
		stageBody2= makeBody2(); stageFooter= makeStageFooter();
		gamePgHeader = new BorderPane();
		gameBox= new VBox(gamePgHeader, gamePgBody);
		prevBet=-1;
	}
}
