package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUIMediator extends Application {
	private LoginScreen login;
	private MainGUI mainGUI;
	private ClientCommand command = new ClientCommand();
	private Client client;
	private Stage stage;
	
	public GUIMediator() {
		client = new Client(command);
		login = new LoginScreen(this, client);
		mainGUI = new MainGUI(this);
		}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		mainGUI.start(stage);
	}
	public static void main(String[] args){
        launch(args);
	}

	public void changeToMainGUI() throws Exception{
		mainGUI.start(stage);
	}
}
