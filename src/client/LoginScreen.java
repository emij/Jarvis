package client;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {
	private GUIMediator mediator;
	private Preferences prefs;
	private String IP1,IP2,IP3,IP4, connectionPort, user; 
	private Insets inputBoxInsets = new Insets(3, 1, 3, 1);
	private Client client;
	private InetAddress host;
	
	public LoginScreen(GUIMediator mediator, Client client){
		this.mediator = mediator;
		this.client = client;
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
		primaryStage.setTitle("Jarvis");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		GridPane leftGrid = new GridPane();
		GridPane rightGrid = new GridPane();
		
		
		

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		grid.getColumnConstraints().addAll(column1, column2);

		grid.add(leftGrid, 0, 1);
		grid.add(rightGrid,1,1);
		Text scenetitle = new Text("Connect to Jarvis");
		scenetitle.setId("jarvis");
		GridPane.setMargin(scenetitle, new Insets(0, 45, 0, 45));
		grid.add(scenetitle, 0, 0, 2, 1);

		// LeftGrid
		
		leftGrid.setPadding(new Insets(25, 25, 25, 25));

		Label userName = new Label("User Name:");
		leftGrid.add(userName, 0, 1);

		Label pw = new Label("Password:");
		leftGrid.add(pw, 0, 2);

		Label IP = new Label("IP:");
		leftGrid.add(IP, 0, 3);

		Label port = new Label("Port:");
		leftGrid.add(port, 0, 4);
		
		final Text actiontarget = new Text();
		leftGrid.add(actiontarget, 0, 5);

		// RightGrid
		rightGrid.setPadding(new Insets(25, 25, 25, 25));
		
		final TextField userTextField = new TextField(prefs.get(user, "jarvis"));
		rightGrid.add(userTextField, 0, 1,4,1);
		GridPane.setMargin(userTextField, inputBoxInsets);

		PasswordField pwBox = new PasswordField();
		rightGrid.add(pwBox, 0, 2,4,1);
		GridPane.setMargin(pwBox, inputBoxInsets);
		
		final TextField IP1TextField = new TextField(prefs.get(IP1, "127"));
		rightGrid.add(IP1TextField, 0, 3);
		GridPane.setMargin(IP1TextField, inputBoxInsets);
		final TextField IP2TextField = new TextField(prefs.get(IP2, "0"));
		rightGrid.add(IP2TextField, 1, 3);
		GridPane.setMargin(IP2TextField, inputBoxInsets);
		final TextField IP3TextField = new TextField(prefs.get(IP3, "0"));
		rightGrid.add(IP3TextField, 2, 3);
		GridPane.setMargin(IP3TextField, inputBoxInsets);
		final TextField IP4TextField = new TextField(prefs.get(IP4, "1"));
		rightGrid.add(IP4TextField, 3, 3);
		GridPane.setMargin(IP4TextField, inputBoxInsets);
		
		final TextField portTextField = new TextField(prefs.get(connectionPort, "6789"));
		rightGrid.add(portTextField, 0, 4,4,1);
		GridPane.setMargin(portTextField, new Insets(3, 0, 25, 0));

		Button connectButton = new Button("Connect");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(connectButton);
		rightGrid.add(hbBtn, 2, 5,2,1);

		connectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// Save all the fields
				savePreferences();
				
				// TODO Checks for proper input should be handled in respective textfield
				StringBuilder strAdress = new StringBuilder();
				strAdress.append(IP1TextField.getText());
				strAdress.append('.');
				strAdress.append(IP2TextField.getText());
				strAdress.append('.');
				strAdress.append(IP3TextField.getText());
				strAdress.append('.');
				strAdress.append(IP4TextField.getText());
				
				int port = Integer.parseInt(portTextField.getText());
				try {
					host = Inet4Address.getByName(strAdress.toString());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(strAdress);
				
				// client.makeConnection(host, port);
				
				actiontarget.setId("actiontarget");
				actiontarget.setText("Connecting");
				try {
					mediator.changeToMainGUI();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void savePreferences() {
				prefs.put(user, userTextField.getText());
				prefs.put(IP1,IP1TextField.getText());
				prefs.put(IP2,IP2TextField.getText());
				prefs.put(IP3,IP3TextField.getText());
				prefs.put(IP4,IP4TextField.getText());
				prefs.put(connectionPort, portTextField.getText());
			}
		});

		Scene scene = new Scene(grid, 500, 475);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		(LoginScreen.class.getResource("login.css").toExternalForm());
		primaryStage.show();
	}


}