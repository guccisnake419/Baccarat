import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.Objects;


public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;

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
		primaryStage.setTitle("Welcome to JavaFX");



		Text t1= new Text("Baccarat");
		 Button b1= new Button("Play Game");
		 t1.setId("t1");
		 VBox homeBox= new VBox(t1, b1);
		 homeBox.setSpacing(150);
		 homeBox.setAlignment(Pos.CENTER);
		 homeBox.setId("homeBox");
		 homeBox.setMaxHeight(500);
		 homeBox.setMaxWidth(500);
		 BorderPane root = new BorderPane();
		 root.setCenter(homeBox);
		Scene scene = new Scene(root, 700,700);
		String css = Objects.requireNonNull(this.getClass().getResource("/assets/style.css")).toExternalForm();
		scene.getStylesheets().add(css);
			primaryStage.setScene(scene);
			primaryStage.show();

		
	}

}
