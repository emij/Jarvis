package client;

import voice.Command;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUIMediator extends Application {
	private LoginScreen login;
	private MainGUI mainGUI;
	private Command command = new Command();
	private Client client;
	
	public GUIMediator() {
		client = new Client(command);
		login = new LoginScreen(this, client);
		mainGUI = new MainGUI(this);
		}

	@Override
	public void start(Stage primaryStage) throws Exception {
		login.start(primaryStage);
	}
	public static void main(String[] args){
        launch(args);
	}

}
