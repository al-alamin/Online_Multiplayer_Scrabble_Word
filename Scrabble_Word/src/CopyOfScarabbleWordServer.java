

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class CopyOfScarabbleWordServer extends Application implements ScrableConstants {
	private BorderPane borderPane=new BorderPane();; 
	private TextArea ta=new TextArea();
	private Button buttonCancel=new Button("CANCEL");
	
	
	
	public void start(Stage primaryStage) throws Exception {
		
		borderPane.setCenter(new ScrollPane(ta));
		borderPane.setTop(buttonCancel);
		
		ta.appendText("Scarabble Server Started at "+ new Date()+"\n");
		
		buttonCancel.setOnMouseClicked(e->{
			System.out.println("closing donw server by user choice");
			ta.appendText("button has been pressed");
			System.exit(1);
			
			});
		
		 
		
		Scene scene =new Scene(borderPane,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scarabble Word Server");
		primaryStage.show();
		
	//	new ScrableWordServerProcess().runScrableWordServer();
		
		new Thread(()->{
			try{
			   ServerSocket	server=new ServerSocket(SERVERSOCKETNUMBER,50);
			
				Platform.runLater(()->
				ta.appendText("server created at " +new Date()+" \n"));
				System.out.println("server socket has been created");
				
			
				while (true)
				{
					Platform.runLater(()->
					ta.appendText("\nServer waiting for connection\n"));
					try {
						Platform.runLater(()->
						ta.appendText("\nwaiting for player1\n"));
					
						Socket player1=server.accept();
						
						System.out.println("Player1 has been accepted by server");
						
						Platform.runLater(()->
							ta.appendText("player1 has been accepted by server\nwaiting for player2\n")									);
						
						ScarableObject temp=new ScarableObject();
						temp.setPlayerNumber(PLAYER1);
						
						temp.setPlayerNumber(-1);
						new ObjectOutputStream(player1.getOutputStream()).writeObject(temp);
						
						
						Platform.runLater(()->
						ta.appendText("s: server has send player number to client that is 1\n")									);
						
						
						Socket player2=server.accept();
						Platform.runLater(()->
						ta.appendText("player2 has been accepted by server\n\n starting a thread and game\n\n")									);
						
						temp.setPlayerNumber(PLAYER2);						
						new ObjectOutputStream(player2.getOutputStream()).writeObject(temp);
						
						 
						Platform.runLater(()->
						ta.appendText("s: server has send player number to client that is 2\n")									);
//					
//						
						new Thread(new HandleMultipleScrableClient(player1,player2)).start();
						
						
						
					} catch (Exception e) {
						System.out.println("player1 server accept failed");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}catch(IOException ex) {
	        ex.printStackTrace();
	      }
			
		}).start();
		
	}
	









	
class HandleMultipleScrableClient implements Runnable,ScrableConstants{
		
		private Socket player1;
		private Socket player2;
		
//		ObjectInputStream input1;
//		ObjectOutputStream output1;
//		ObjectInputStream input2;
//		ObjectOutputStream output2;
		
	//	ScarableObject ob2=new ScarableObject();
		
		public HandleMultipleScrableClient(Socket s1,Socket s2)
		{
			player1=s1;
			player2=s2;
		}
		
		
		public void run() {
			
			try {
				new ObjectOutputStream(player1.getOutputStream()).writeObject(new ScarableObject());
				
				
//				ScarableObject temp5=new ScarableObject();
//				output1.writeObject(temp5);
//				output1.flush();
				
				Platform.runLater(()->
				ta.appendText("just notified client 1 about the game \n")	);
			
				
				while (true)
				{
					ObjectOutputStream	output1=new ObjectOutputStream(player1.getOutputStream());
					ObjectInputStream input1=new ObjectInputStream(player1.getInputStream());
					ObjectOutputStream output2=new ObjectOutputStream(player2.getOutputStream());
					ObjectInputStream input2=new ObjectInputStream(player2.getInputStream());
	
					
					
				//	String s=input.readLine();
					
					ScarableObject ob=(ScarableObject) input1.readObject();
					
					System.out.println("server console: client has object "+ob);
					Platform.runLater(()->
						ta.appendText("client has send ob "+ ob+" \n")	);
					
					
					ScarableObject ob2=new ScarableObject();
					ob2.setCoOrdinate(ob.getCoOrdinate());
					ob2.setWord("thanx allah");
					
					
					output2.writeObject(ob);
					output2.flush();
					
					Platform.runLater(()->
					ta.appendText("server has send client2 this "+ ob+" \n")	);
				
					
					
					output1.writeObject(ob2);
					output1.flush();
					
					Platform.runLater(()->
					ta.appendText("server has send client1 this "+ ob2+" \n")	);
				
					
					
					
					
					System.out.println("server console: server send  "+ob2);
					
					Platform.runLater(()->
					ta.appendText("server has send number "+ ob2+" \n")	);
				
					
				}
				
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("SC input output stream getting failed. .... something wrong\n");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				System.out.println("SC OK.....client has closed the connection");
				Platform.runLater(()->
				ta.appendText("client has closed the connection\n"));
				
			}
			
			
			
			
		}
		
		
	}


	
	
	
	 
	 public static void main(String[] args)
	 {
		 System.out.println("Starting Scarabble word server");
		 launch(args);
	 }

}
