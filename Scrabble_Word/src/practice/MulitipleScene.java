package practice;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class MulitipleScene extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlowPane flowPane=new FlowPane();
		Button button=new Button("alamin");
		flowPane.getChildren().add(button);
		
		
		
		button.setOnMouseClicked(e->{
			
		
		
			
			System.out.println("in new thread");
	
			Stage stage=new Stage();
			FlowPane flowPane2=new FlowPane();
			Button button2=new Button("alamin23");
			flowPane2.getChildren().add(button2);
			
			Scene scene2 =new Scene(flowPane2);
			stage.setScene(scene2);
			stage.show();
			
		});
	
		System.out.println("start function over");
		
		
		Scene scene =new Scene(flowPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
