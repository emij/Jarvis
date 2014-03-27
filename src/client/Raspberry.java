package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Raspberry extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		Text scenetitle = new Text("Welcome");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label IP = new Label("IP:");
		grid.add(IP, 0, 1);
		
		TextField IPTextField = new TextField();
		grid.add(IPTextField, 1, 1);

		Label port = new Label("Port:");
		grid.add(port, 0, 2);
		
		TextField portTextField = new TextField();
		grid.add(portTextField, 1, 2);

		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 3);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 3);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 4);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 4);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 5);
		
		final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	actiontarget.setId("actiontarget");
                actiontarget.setText("Sign in button pressed");
            }
        });
        
		Scene scene = new Scene(grid, 500, 475);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		 (Raspberry.class.getResource("login.css").toExternalForm());
		primaryStage.show();
	}


}