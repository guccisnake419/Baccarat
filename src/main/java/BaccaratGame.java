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
	PseudoClass cChips= PseudoClass.getPseudoClass("chips"), playButtons= PseudoClass.getPseudoClass("playButtons");;

	Button chip_100K, chip_50K, chip_40K, chip_20K, chip_10K, options, b1, exit, freshStart, reBet, clearBet, deal;
	Text score;
	Stage primaryStage;
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

		primaryStage.setTitle("Welcome to JavaFX");
		css = Objects.requireNonNull(this.getClass().getResource("/assets/style.css")).toExternalForm();

		sceneMap.put("LandingPage", createLandingPage());
		sceneMap.put("GamePage", createGamePage());
		sceneMap.put("OptionPage", createOptionPage());
		b1.setOnAction(e-> changeScene("GamePage"));
		options.setOnAction(e->changeScene("OptionPage"));
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
		BorderPane header = new BorderPane();
		header.setLeft(score);
		header.setRight(options);
		BorderPane body= new BorderPane();
		//Vbox for stage is a temp placeholder
		VBox chips= new VBox(), stage= new VBox(), controls= new VBox();
		body.setLeft(chips);
		body.setCenter(stage);
		body.setRight(controls);
		initializeChips();
		initializeControls();
		controls.getChildren().setAll(reBet, clearBet, deal);
		controls.setSpacing(100);
		chips.setSpacing(30);
		chips.getChildren().addAll(chip_100K,chip_50K, chip_40K, chip_20K, chip_10K);

		VBox gameBox= new VBox(header, body);
		gameBox.setId("gameBox");
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
}
