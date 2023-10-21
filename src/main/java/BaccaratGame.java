import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
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
	Button b1;
	Button exit;
	Button freshStart;
	String css;

	Button options;
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

		VBox gameBox= new VBox(header);
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

}
