package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUIMediator extends Application {
	private LoginScreen login;
	private MainGUI mainGUI;
	
	public GUIMediator() {
		login = new LoginScreen(this);
		mainGUI = new MainGUI(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
