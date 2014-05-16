package client;

import util.ClientCommand;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Mediator class for controlling what screen to show in the GUI
 *  
 * @author Emil Johansson
 */
public class GUIMediator extends Application {
	private LoginScreen login;
	private MainGUI mainGUI;
	private ClientCommand command = new ClientCommand();
	private Client client;
	private Stage stage;

	public GUIMediator() {
		client = new Client();
		login = new LoginScreen(this, client);
		mainGUI = new MainGUI(this, client, command);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		login.start(stage);
	}
	public static void main(String[] args){
		launch(args);
	}

	public void changeToMainGUI() throws Exception{
		mainGUI.start(stage);
	}
	@Override
	public void stop(){
		if(client.connectionActive()){
			client.writeToServer("quit");
		}
	}
}
