package client;

import java.util.prefs.Preferences;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {
	private GUIMediator mediator;
	private Preferences prefs;
	private String IP1,IP2,IP3,IP4, connectionPort, user; 
	
	public LoginScreen(GUIMediator mediator){
		this.mediator = mediator;
		prefs = Preferences.userRoot().node(this.getClass().getName());
		setUpKeys();
	}
	
	private void setUpKeys() {
		IP1 = "IP1";
		IP2 = "IP2";
		IP3 = "IP3";
		IP4 = "IP4";
		connectionPort = "connectionPort";
		user = "user";
		
	}

	public Preferences getPref(){
		return prefs;
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
		
		TextField IP1TextField = new TextField(prefs.get(IP1, "192"));
		grid.add(IP1TextField, 1, 1);
		TextField IP2TextField = new TextField(prefs.get(IP2, "168"));
		grid.add(IP2TextField, 1, 1);
		TextField IP3TextField = new TextField(prefs.get(IP3, "1"));
		grid.add(IP3TextField, 1, 1);
		TextField IP4TextField = new TextField(prefs.get(IP4, "1"));
		grid.add(IP4TextField, 1, 1);

		Label port = new Label("Port:");
		grid.add(port, 0, 2);
		
		TextField portTextField = new TextField(prefs.get(connectionPort, "6789"));
		grid.add(portTextField, 1, 2);

		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 3);

		TextField userTextField = new TextField(prefs.get(user, "jarvis"));
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
		 (LoginScreen.class.getResource("login.css").toExternalForm());
		primaryStage.show();
	}


}