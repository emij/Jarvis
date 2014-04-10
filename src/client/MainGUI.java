package client;

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
	public MainGUI(GUIMediator mediator) {
		this.mediator = mediator;
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
		Label userName = new Label("Lamp");
		leftGrid.add(userName, 1, 1);
		
		// Right Grid
		rightGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button enable = new Button();
        enable.setText("Enable");
        enable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Enable");
            }
        });
        
        rightGrid.add(enable, 1, 0);
        
        Button disable = new Button();
        disable.setText("Disable");
        disable.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Disable");
            }
        });
        
        GridPane.setMargin(enable, new Insets(0, 25, 0, 0));
        rightGrid.add(disable,2,0);
		
		Scene scene = new Scene(grid, 500, 475);
		primaryStage.setScene(scene);
		scene.getStylesheets().add
		(LoginScreen.class.getResource("mainGUI.css").toExternalForm());
		primaryStage.show();
	}
	
	

}
