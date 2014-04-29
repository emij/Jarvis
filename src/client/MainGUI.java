package client;

import util.ClientCommand;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainGUI extends Application {
	private GUIMediator mediator;
	private Client client;
	private ClientCommand command;
	public MainGUI(GUIMediator mediator, Client client, ClientCommand command) {
		this.client = client;
		this.mediator = mediator;
		this.command = command;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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
		Text scenetitle = new Text("Control Device");
		scenetitle.setId("device");
		GridPane.setMargin(scenetitle, new Insets(0, 45, 0, 45));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		// Left Grid
		leftGrid.setPadding(new Insets(25, 25, 25, 25));
		Label lamp = new Label("Lamp");
		leftGrid.add(lamp, 1, 1);
		
		Label kitchenLamp = new Label("Kitchen Lamp");
		leftGrid.add(kitchenLamp, 1, 2);
		
		
		// Right Grid
		rightGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button lampEnable = new Button();
        lampEnable.setText("Enable");
        lampEnable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Enable");
                command.addDevice("lamp");
                command.addAction("enable");
                client.writeToServer(command);
                command.resetCommand();
                
            }
        });
        
        rightGrid.add(lampEnable, 1, 0);
        
        Button lampDisable = new Button();
        lampDisable.setText("Disable");
        lampDisable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Disable");
                command.addDevice("lamp");
                command.addAction("disable");
                client.writeToServer(command);
                command.resetCommand();
            }
        });
        GridPane.setMargin(lampEnable, new Insets(0, 25, 5, 0));
        rightGrid.add(lampDisable,2,0);
        
        Button kitchenLampEnable = new Button();
        kitchenLampEnable.setText("Enable");
        kitchenLampEnable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Enable");
                command.addDevice("kitchenlamp");
                command.addAction("enable");
                client.writeToServer(command);
                command.resetCommand();
                
            }
        });
        
        rightGrid.add(kitchenLampEnable, 1, 1);
        
        Button kitchenLampDisable = new Button();
        kitchenLampDisable.setText("Disable");
        kitchenLampDisable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Disable");
                command.addDevice("kitchenlamp");
                command.addAction("disable");
                client.writeToServer(command);
                command.resetCommand();
            }
        });
        GridPane.setMargin(kitchenLampEnable, new Insets(0, 25, 5, 0));
        rightGrid.add(kitchenLampDisable,2,1);
        
        
		
		Scene scene = new Scene(grid, 500, 475);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		(LoginScreen.class.getResource("mainGUI.css").toExternalForm());
		primaryStage.show();
	}
	
	

}
