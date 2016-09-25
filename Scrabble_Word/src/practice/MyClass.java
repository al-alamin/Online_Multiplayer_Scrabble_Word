package practice;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class MyClass extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
//	
//		ArrayList<PlayerInformation> playerList=new ArrayList<PlayerInformation>();
//		
//		
//		playerList.add(new PlayerInformation("alamin",22));
//		
//		playerList.add(new PlayerInformation("a",223));
//		
//		playerList.add(new PlayerInformation("sfdn",2342));
//		playerList.add(new PlayerInformation("sdf",40));
//		
//		
//		Collections.sort(playerList);
////		
		
//		GridPane pane=new GridPane();
//		pane.setAlignment(Pos.CENTER);
//		pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
//		pane.setHgap(5.5);
//		pane.setVgap(5.5);
//		
//		pane.add(new Label("THE GAME IS OVER:\nRESULT IS:"), 0, 0);
//		for (int i=0;i<(playerList.size());i++)
//		{
//			Label temp1=new Label(playerList.get(i).playerName);
//			int t=playerList.get(i).score;
//			Label temp2=new Label(t+"");
//			pane.add(temp1, 0, i+4);
//			pane.add(temp2, 1, i+4 );
//			
//		}
//		
//		
//		Button newGame=new Button("NEW GAME");
//		Button exit=new Button("EXIT");
//		
//		newGame.setOnMouseClicked(e->{
//			System.out.println("new game button has been clicked");
//		//	new MyClass().start();
//			
//		});
//		exit.setOnMouseClicked(e->{
//			System.out.println("exit button has been clicked");
//			
//		});
//		
//		pane.add(newGame, 0, 15);
//		pane.add(exit, 1, 15);
//		
		
		GridPane pane1=new GridPane();
		
		pane1.setAlignment(Pos.CENTER);
		pane1.setPadding(new Insets(11.5,12.5,13.5,14.5));
		pane1.setHgap(10);
		pane1.setVgap(10);
		
		
		
		System.out.println("alamin");
		Button button=new Button("Connect");
		Label l1=new Label("CONNECT TO SERVER TO PLAY");
		Label l2=new Label("IP ADDRESS: ");
		Label l3=new Label ("PORT: ");
		Label l4=new Label ("NAME: ");
		
		TextField tf=new TextField("127.0.0.1");
		TextField tf2=new TextField("12399");
		TextField tf3=new TextField("Name");
		
		pane1.add(l1, 1	, 4);
		pane1.add(l2, 0	, 8);
		pane1.add(tf, 2	, 8);
		pane1.add(l3, 0	, 9);
		pane1.add(tf2, 2, 9);
		
		pane1.add(l4, 0	, 10);		
		pane1.add(tf3, 2	, 10);
		pane1.add(button, 1, 14);
		
		
		
		
		
		button.setOnMouseClicked(e->{
			
			Platform.runLater(()->{
				Stage stage=new Stage();
				
	//			Scene scene2=new Scene(pane,400,400);
				
				
	//			primaryStage.setScene(scene2);
				System.out.println(tf.getText());
				System.out.println(tf2.getText());
				System.out.println(tf3.getText());
			//	System.out.println(tf.getText());
				
				
			//	stage.show();
				
			
			});
			
		//	System.exit(1);
			//Platform.exit();
		});
		
		
		
		Scene scene1=new Scene(pane1,400,400);
		primaryStage.setTitle("alamin");
		primaryStage.setScene(scene1);
		primaryStage.show();
	
	 
		
		
		
	}
	

	
	public static void main(String[] args) {
		System.out.println("in main");
		launch();

	}



	

	
	

}
