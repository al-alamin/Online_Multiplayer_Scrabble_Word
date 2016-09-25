package practice;

import java.io.BufferedInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class TestMain extends Application {

	public void start(Stage primaryStage) throws Exception {
		
		FlowPane flowPane=new FlowPane();
		flowPane.getChildren().add(new Button("b"));
		Scene scene = new Scene(flowPane,400,400);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	//	new Test();
		
		
		
		
	}
	
	
	public static void main(String[] args) {
			
			launch(args);
			
		}
}