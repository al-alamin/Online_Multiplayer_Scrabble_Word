import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;





import com.sun.corba.se.impl.interceptors.PINoOpHandlerImpl;

import Database.DataBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ScarabbleWordServer extends Application implements ScrableConstants {
	private BorderPane borderPane=new BorderPane();; 
	private TextArea ta=new TextArea();
	private Button buttonCancel=new Button("CANCEL");
	
	DataBase db=new DataBase();
	
	
	
	public void start(Stage primaryStage) throws Exception {
		
		borderPane.setCenter(new ScrollPane(ta));
		borderPane.setTop(buttonCancel);
		
		ta.appendText("Scarabble Server Started at "+ new Date()+"\n");
		
		buttonCancel.setOnMouseClicked(e->{
			System.out.println("closing donw server by user choice");
			ta.appendText("button has been pressed");
			System.exit(1);
			
			});
		
		 handlingMultipleGame();
		
		Scene scene =new Scene(borderPane,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scarabble Word Server");
		primaryStage.show();
		
	//	new ScrableWordServerProcess().runScrableWordServer();
		
		
	}
	
	public void writeToGui(String msg)
	{
		Platform.runLater(()->{
			ta.appendText(msg);
		});

	}
	
	public void writeToClient(Socket s, ScarableObject ob) throws IOException
	{
		new ObjectOutputStream(s.getOutputStream()).writeObject(ob);
		
	}
	
	public ScarableObject receiveObjectFromserver(Socket s) throws ClassNotFoundException, IOException
	{
		ScarableObject temp=new ScarableObject();
		temp=(ScarableObject) new ObjectInputStream(s.getInputStream()).readObject();
		
		return temp;
	}
	
	
	public void handlingMultipleGame()
	{
		
		new Thread(()->{
			try{
			   ServerSocket	server=new ServerSocket(SERVERSOCKETNUMBER,50);
			   	
				Platform.runLater(()->
				ta.appendText("server created at " +new Date()+" \n"));
				System.out.println("server socket has been created");
				
			
				while (true)
				{
					Socket[] arraySocketTemp=new Socket[7];
					ArrayList <PlayerInformation> pInfo=new ArrayList<>();
					
					pInfo.add(new PlayerInformation("noone",0));
					
					int pnum=0;
					Platform.runLater(()->
					ta.appendText("\nServer waiting for connection\n"));
					
					
					try 
					{
						String msg="\nwaiting for "+TOTALPLAYERNUMBER+" to connect to start the game\n";
						writeToGui(msg);
						msg=null;
						for (int i=1;i<=TOTALPLAYERNUMBER;i++)
						{
						
							Socket ss=server.accept();
							
							System.out.println(i+" Player has been accepted by server");
							arraySocketTemp[i]=ss;
							
							msg=" player "+i+" has been accepted by server\n";
							if (i!=TOTALPLAYERNUMBER){
								msg+= "waiting for player"+(i+1)+"\n";
							}
							writeToGui(msg);
							msg=null;
							
							ScarableObject temp=new ScarableObject();
							temp=(ScarableObject) new ObjectInputStream(ss.getInputStream()).readObject();
							
							pInfo.add(new PlayerInformation(temp.word,0));							
							temp.setPlayerNumber(i);
							
						//	receiveObjectFromserver(ss);
							writeToClient(ss,temp);
					//		new ObjectOutputStream(ss.getOutputStream()).writeObject(temp);			
							
							msg="s: server has send player number to client that is "+i+" \n";
							writeToGui(msg);
							msg=null;
						
						}
					
						System.out.println("new game is about to begin pInfo is: "+pInfo.toString());
						System.out.println("before starting the gaem pinfo is: "+pInfo.toString());
						new Thread(new HandleMultipleScrableClient(arraySocketTemp,pInfo)).start();
							
						
						
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
		
		ArrayList<PlayerInformation> playerInfoList=new ArrayList<PlayerInformation>();
		Socket[] arraySocketlist=new Socket[7];
	//	public ArrayList<Integer> playerScoreList=new ArrayList<>();
		
		ArrayList<PlayerInformation> highScores=new ArrayList<PlayerInformation>();
		
		void loadTopResults() throws FileNotFoundException
		{
			
				Scanner sc=new Scanner(new File("highestPlayerScores.txt"));
				
				while (sc.hasNext())
				{
					String s=sc.next();
					int n=sc.nextInt();
					
					System.out.println(s+"  ");
					PlayerInformation pi=new PlayerInformation(s, n);
					highScores.add(pi);
				}
				
				Collections.sort(highScores);
				
				while (highScores.size()>10)
				{
					highScores.remove(10);
				}
				
				System.out.println(highScores);
				
				
			} 
		
		
		
		public HandleMultipleScrableClient(Socket[] list,ArrayList<PlayerInformation> pInfo)
		{
			System.out.println("in constructor of handle new game: \npinfo is: "+pInfo.toString());
			
			for (int i=1;i<=TOTALPLAYERNUMBER;i++)
			{
				arraySocketlist[i]=list[i];
				
				
			}
			
			playerInfoList.clear();
			playerInfoList.addAll(pInfo);
			System.out.println("\n...after coping the playerinfolist is: "+pInfo.toString());
			
		
		}
		
		public void notifyingToStartGame() throws IOException
		{
			for (int i=1;i<=TOTALPLAYERNUMBER;i++)
			{
				Socket socTemp=arraySocketlist[i];
				
				ScarableObject temp5=new ScarableObject();
				temp5.setPlayerInfoList(playerInfoList,highScores);
				writeToClient(socTemp,temp5);
		//		new ObjectOutputStream(arraySocketlist[i].getOutputStream()).writeObject(temp5);
				
				String msg="just notified client "+i+" about the game \n";
				writeToGui(msg);
				msg=null;

			}
		}
		
		public Boolean willPlayAnotherRound() throws ClassNotFoundException, IOException
		{
			System.out.println("notifying all if they want to play another round or not");		    	
	    	Boolean willPlay=true;
			
	    	for (int j=1;j<=TOTALPLAYERNUMBER;j++)
		    {
		    	System.out.println("waiting for p "+j+" to send ok or over ");
		    	
		    	ScarableObject temp1=(ScarableObject) new ObjectInputStream(arraySocketlist[j].getInputStream()).readObject();
		    	
		    	System.out.println(j +" player sends : "+temp1.word);
		    	if (temp1.word.equals("over"))
		    	{
		    		willPlay=false;
		    		break;
		    	}
		    	
		    }
	    	String msg="willplay "+willPlay+"\n";
	    	writeToGui(msg);
	    	msg=null;
	    	
	    	System.out.println("a new cycle of game to be started "+willPlay);
	    	 
	    	return willPlay;
		
		}
		
		
		
		
		public void updatePlayerInfoList(int p,int cPoint, ArrayList<Integer> aChallengers)
		{
			 int challengedPoint=cPoint;
			 System.out.println("challenged point "+challengedPoint);
			    
			for (int j=1;j<=TOTALPLAYERNUMBER;j++)
		    {
		    	System.out.println("in for loop to update scoreboard");
		    	if (j==p)
	    		{
	    	
	    			int tt=playerInfoList.get(j).score-2*challengedPoint;
	    			System.out.println("updating j: "+j+" and tt : "+tt);
	    			playerInfoList.get(j).score=tt;
	    	
	    		}
		    	
		    	else if (aChallengers.contains(j))
	    		{
		    		System.out.println("p:"+j+" has challenged");
	    			int tt=playerInfoList.get(j).score+challengedPoint;
	    			System.out.println("updating j: "+j+" and tt : "+tt);
	    			playerInfoList.get(j).score=tt;
		    	}
	    		
		    }
			
			
			
			
			
		}
		
		
		public void findChallengers( ArrayList<Integer> aOpponents,ArrayList<Integer> aChallengers,int p) throws ClassNotFoundException, IOException
		{
			
			System.out.println("in find challengers function oppnents are: "+aOpponents.toString()+" and challengers are "+aChallengers.toString());
			for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			{
				System.out.println("waiting for p "+j+" to send ok or cancel ");
				if (j==p){
					continue;
				}
				ScarableObject temp1=(ScarableObject) new ObjectInputStream(arraySocketlist[aOpponents.get(0)].getInputStream()).readObject();


				aOpponents.remove(0);
				//  	objectArray.add(temp1);
				if (temp1.word.equals("challenge"))
				{
					aChallengers.add(j);
				}
				System.out.println(j +" player sends : "+temp1.word);

			}
			
			System.out.println("\n\nabout to return from find challengers challengers are "+aChallengers.toString());
			
			
			
			
		}
		
		
		public void handlingWordValidity(int p,ScarableObject ob) throws ClassNotFoundException, IOException
		{
			 ArrayList<Integer> aChallengers=new ArrayList<>();
			 ArrayList<Integer> aOpponents=new ArrayList<Integer>();
			 
			
			 System.out.println("server is about to find out whether players accept the game or not");
			    
			    
			 for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			 {
				 if (j==p){
					 continue;
				 }
				 aOpponents.add(j);
			 }

			 findChallengers(aOpponents,aChallengers,p);
			 
			 if (aChallengers.size()==0)
			 {
				 System.out.println("no one hass challenged");

			 }
			 else{
				 System.out.println(aChallengers.size()+" people has challenged now i will have to process them\n"+aChallengers.toString());

			 }

			 System.out.println("about to send challenge score to clients");

	//		 int challengedPoint=ob.wordScore;
			 int challengedPoint=0;
			 
			 if (aChallengers.size()>0)
			 {
				 if (db.isItAWord(ob.word)){
				 challengedPoint=-1*ob.wordScore;
				 System.out.println("\n\n valid word ");
				 }
				 else{
					 challengedPoint=ob.wordScore;
					 System.out.println("\n\nnot a valid word so now points will be deducted");
						
				 }
				 
			 }
			 
			
			 System.out.println("challenged point "+challengedPoint);


			 updatePlayerInfoList(p,challengedPoint,aChallengers);
		}
		
		
		public void changePlayerScore(int p,int add)
		{
//			System.out.println("in change playerScorefunc infolist is "+playerInfoList.toString());
//			System.out.println("p is: "+p +" and add is "+add);
			int t=playerInfoList.get(p).score+add;
		//	System.out.println("about to update playerscore: ");
		//	playerInforList.set(i, t);
			playerInfoList.get(p).score=t;
			
		}
		
		public void processARoundOfGame() throws IOException, ClassNotFoundException
		{
			
			for (int p=1;p<=TOTALPLAYERNUMBER;p++)
			{
				Socket socTemp=arraySocketlist[p];
				System.out.println("waiting for p "+p+" to send word");
				
				ScarableObject ob=(ScarableObject) new ObjectInputStream(arraySocketlist[p].getInputStream()).readObject();
			//	ScarableObject ob=receiveObjectFromserver(socTemp);
				System.out.println(p+" player has send "+ob);
		
				changePlayerScore(p, ob.wordScore);
				
				String msg="player1 has send ob "+ ob+" \n";
				writeToGui(msg);
				msg=null;
				
				// notifying other players about this players move
				for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			    {
			    	if (j==p){
			    		continue;
			    	}
			//    	new ObjectOutputStream(arraySocketlist[j].getOutputStream()).writeObject(ob);
					writeToClient(arraySocketlist[j], ob);
			    	System.out.println("server send player "+j +"  "+ob);
					
			    }
			    
				
				
			    handlingWordValidity(p,ob);
			    
			    
			    System.out.println("new score board is "+ playerInfoList.toString());
			    for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			    {
			    	ScarableObject temp5=new ScarableObject();
			    	temp5.setPlayerInfoList(playerInfoList,highScores);
			    //	temp5.setPlayerInfoList(highScores);
			    //	temp5.addPlayerInfoList(playerInfoList);
			    	new ObjectOutputStream(arraySocketlist[j].getOutputStream()).writeObject(temp5);
			    }
			    
			    System.out.println("....,....over sending client score is over.........");
			
		    	System.out.println("accepting from client is completed");
//			    	
		    	
			}
		}
		
		
		public void writeScoreToFile()
		{
			try {
				BufferedWriter bw=new BufferedWriter(new FileWriter(new File("highestPlayerScores.txt"),true));
				for (int p=1;p<TOTALPLAYERNUMBER;p++)
				{
					PlayerInformation ob=playerInfoList.get(p);
					bw.write(ob.playerName);
					bw.newLine();
					bw.write(ob.score+"");
					bw.newLine();
					
					
				}
				
				bw.flush();
				bw.close();
			
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		public void terninateOrContinue(int n)
		{
			if (n==1)
			{
				System.out.println(".... the game has been terminated....\n");
				for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			    {
			    	ScarableObject temp5=new ScarableObject();
			    	temp5.word="over";
			    	try {
						new ObjectOutputStream(arraySocketlist[j].getOutputStream()).writeObject(temp5);
					
			    	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	writeScoreToFile();
			    }
				
				System.out.println("....\n\n sent over to all\n\n\n");
			    
			
			}
			if (n==0)
			{
				System.out.println("the game will continue\n");
				for (int j=1;j<=TOTALPLAYERNUMBER;j++)
			    {
			    	ScarableObject temp5=new ScarableObject();
			    	temp5.word="continue";
			    	try {
						new ObjectOutputStream(arraySocketlist[j].getOutputStream()).writeObject(temp5);
					
			    	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
				
				System.out.println("the game will continue");
			    
			
			}
			
			
		}
		
		
		public void run() {
			try {
					loadTopResults();
					notifyingToStartGame();

					System.out.println("the game is about to begin");
					
					while (true)
					{
						processARoundOfGame();						
						
						
						if (willPlayAnotherRound()==false)
						{
							terninateOrContinue(1);
							
						}
						
						else{
							terninateOrContinue(0);
						}
						
						
						
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
