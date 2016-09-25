
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.sqlite.core.CoreDatabaseMetaData.PrimaryKeyFinder;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;








import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CellBox extends Application implements ScrableConstants{
	
	
	public Cell [][] arrayCellMainBoard=new Cell[7][7];
	public Cell [][] arrayCellPlayer1=new Cell[6][3];
	
	
	Label status=new Label();
	Label statusAboutClientNumber=new Label("client number");
	Label statusAboutMyturn=new Label("is my turn");
	Label statusAboutWord=new Label("Your new formed has not been checked");
//	Label statusAboutServer=new Label("server information");
	TextArea statusTextAreaAboutServer=new TextArea();
	TextField tfIp=new TextField("127.0.0.1");
	TextField tfSocket=new TextField(SERVERSOCKETNUMBER+"");
	
	
	
	private GridPane gridPaneMainBoard=new GridPane();;
	private GridPane gridPanePlayer1=new GridPane();
	private Button buttonCheckWord=new Button("Check you word");
	private Button buttonSubmit=new Button("Submit");
	Button bConnect=new Button("Connect");
	private VBox vBox;
	private Button buttonShowResult=new Button("Result");
	
	static Boolean canWriteToMainBoard=false;
	static char userSelectedLetter;
	static int userSelectedPreviousLetterNumber;
	static int staticCellNumber=0;
	Boolean canEditWord=true;
	Boolean isFirstLetterChosen=false;
	
	CoOrdinate coOrdinate=new CoOrdinate();
	
	private String userCreatedWord=new String();
	
	
	Socket socket;
//	ObjectInputStream input;
//	ObjectOutputStream output;
	Boolean isServerRunning=true;
	TextField tfName=new TextField("Name");
	
	ScarableObject forSending=new ScarableObject();
	
	public boolean myTurn = false;
	public boolean waiting = true;
	public boolean isConfirmed=true;
	public int cpNumber=-1;
	public String cpName=new String();
	public String cpFullName=new String();
	public ScarableObject fromServer=new ScarableObject();
	
	
	public boolean isMusicPlaying=true;
	MediaPlayer mediaPlayer;
	Button bMusic=new Button("Pause");
	
	//public ArrayList<Integer> playerScoreList=new ArrayList<>();
	ArrayList<PlayerInformation> playerInfoList=new ArrayList<PlayerInformation>();
	
	Random rn=new Random();
	boolean isOver=false;
	
//	ScrableClientServer serverCommunicator;
	
	public Scene logInScene()
	{
		GridPane pane1=new GridPane();
		
		pane1.setAlignment(Pos.CENTER);
		pane1.setPadding(new Insets(11.5,12.5,13.5,14.5));
		pane1.setHgap(10);
		pane1.setVgap(10);
		
		System.out.println("alamin");
		
	//	Label l1=new Label("CONNECT TO SERVER TO PLAY");
		 
        Text sceneTitle=new Text("CONNECT TO PLAY");
        sceneTitle.setId("welcome-text");
   //     grid.add(sceneTitle,0,0,2,1);
		
		Label l2=new Label("IP ADDRESS: ");
		Label l3=new Label ("PORT: ");
		Label l4=new Label ("NAME: ");
		
		
		
	//	tfName=new TextField("Name");
		pane1.add(sceneTitle, 1, 3,2,2);
	//	pane1.add(l1, 1	, 4);
		pane1.add(l2, 0	, 8);
		pane1.add(tfIp, 2	, 8);
		pane1.add(l3, 0	, 9);
		pane1.add(tfSocket, 2, 9);
		
		pane1.add(l4, 0	, 10);		
		pane1.add(tfName, 2	, 10);
		pane1.add(bConnect, 1, 14);
		
		Scene scene1=new Scene(pane1,600,650);
		scene1.getStylesheets().add(CellBox.class.getResource("scene1.css").toExternalForm());
		return scene1;
	}
	
	public void start(Stage primaryStage) throws UnknownHostException, IOException {
		
		status.setText("NO letter has been pressed");
		
		buttonCheckWord.setOnMouseClicked(e-> handleButtonCheckWordClicked());
		buttonSubmit.setOnMouseClicked(e-> handleButtonSubmit());
		vBox=new VBox(20);
	
		mainBoard();
		player1Board();	
		BorderPane borderPane=new BorderPane();
	//	borderPane.setCenter(gridPaneMainBoard);
	//	borderPane.setBottom(gridPanePlayer1);
		
		FlowPane flowPane=new FlowPane();
		flowPane.setPadding(new Insets(11,12,13,14));
		flowPane.setHgap(55);
		flowPane.setVgap(10);
		
		flowPane.getChildren().addAll(statusAboutClientNumber,statusAboutMyturn,buttonShowResult,bMusic);
		
		vBox.getChildren().addAll(flowPane,new ScrollPane(statusTextAreaAboutServer),buttonCheckWord,buttonSubmit,gridPaneMainBoard,gridPanePlayer1);
		//	vBox.getChildren().addAll(status,statusAboutServer,new ScrollPane(statusTextAreaAboutServer),buttonCheckWord,buttonSubmit,gridPaneMainBoard,gridPanePlayer1);
		
		borderPane.setCenter(vBox);
		
		
		Scene scene1=logInScene();
		
		
		mediaPlayer = createMediaPlayer("airtel.mp3");
		if (mediaPlayer != null) {
		      mediaPlayer.play();
		} 
		
		
		
		
		new Thread(()->{
			
			while (true)
			{
			//	System.out.println("isover thread: "+isOver);
				if (isOver)
				{
					System.out.println("............\n\n should work.....\n\n");
					Platform.runLater(()->{
						primaryStage.hide();
				
					});
					break;
					
				}
				try {
					Thread.sleep(200);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}).start();
		
		
		Scene scene=new Scene(borderPane,600,650);
		scene.getStylesheets().add(CellBox.class.getResource("scene.css").toExternalForm());

		bConnect.setOnMouseClicked(e->{
			cpName=tfName.getText();
			
			primaryStage.setScene(scene);
			connectToScarabbleServer();
		});
		
		buttonShowResult.setOnMouseClicked(e->{
			showResult(1);
		});
		
		
		 bMusic.setOnMouseClicked(e->{
	    	if (isMusicPlaying)
	    	{
	    		isMusicPlaying=false;
	    		mediaPlayer.pause();
	    	//	isOver=true;
	    	//	primaryStage.hide();
	    	
	    	    bMusic.setText("play");
	    	    
	    		
	    	}
	    	else{
	    		mediaPlayer.play();   	
	    		isMusicPlaying=true;
	    	
	      	      bMusic.setText("pause");
	      	  
	    	}
	    	
	    });
		 
		primaryStage.setTitle("Scrable Game");
		primaryStage.setScene(scene1);
		primaryStage.show(); 
		 
		
	}
	
	
	public void writeToServer(Socket s, ScarableObject ob) throws IOException
	{
		new ObjectOutputStream(s.getOutputStream()).writeObject(ob);
		
	}
	
	public ScarableObject receiveObjectFromserver(Socket s) throws ClassNotFoundException, IOException
	{
		ScarableObject temp=new ScarableObject();
		temp=(ScarableObject) new ObjectInputStream(s.getInputStream()).readObject();
		
		return temp;
	}
	
	
	
	public void showResult(int n)
	{
	//	Collections.sort(playerInfoList);
		
		System.out.println("..... in showResult function and pinfolist is: "+playerInfoList.toString());
		
		
		if (playerInfoList.size()==0)
		{
			playerInfoList.add(new PlayerInformation("a",22));			
			playerInfoList.add(new PlayerInformation("b",223));			
			playerInfoList.add(new PlayerInformation("c",2342));
			playerInfoList.add(new PlayerInformation("d",40));
		}
		
//		Collections.sort(playerInfoList);
		
		
		GridPane pane=new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		
		if (n==0)
		{
			pane.add(new Label("THE GAME IS OVER:\nRESULT IS:"), 0, 0);	
			Collections.sort(playerInfoList);
			
		}
		
		else{
			pane.add(new Label("RESULT IS:"), 0, 0);	
			
			
		}
		int k=0;
		for (int i=0;i<(playerInfoList.size());i++)
		{
			if (i==10)
			{
				k=4;
				continue;
			}
			Label temp1=new Label(playerInfoList.get(i).playerName);
			int t=playerInfoList.get(i).score;
			Label temp2=new Label(t+"");
			pane.add(temp1, 0, i+3+k);
			pane.add(temp2, 1, i+3+k );
			
		}
		
		
		Stage stage=new Stage();
		Scene scene3=new Scene(pane,600,600);
		scene3.getStylesheets().add(CellBox.class.getResource("scene3.css").toExternalForm());
		
		
		stage.setScene(scene3);
		stage.setTitle("      SCORE BOARD      ");
		stage.show();
		
		

		
	}
	

	public void writeToGui(String msg)
	{
		Platform.runLater(()->{
			statusTextAreaAboutServer.appendText(msg);
		});

	}
	public void handleButtonSubmit() 
	{
	//	statusAboutWord.setText("you have clicked the submit button and isserverrunning "+isServerRunning);
		waiting=false;

	
	}


	public void handleButtonCheckWordClicked()
	{
		canEditWord=false;
	}
	
	public void newlyCreatedWord()
	{
		System.out.println("in newlyCreated word function");
		System.out.println("nature of word is "+ coOrdinate+"  "+coOrdinate.natureOfWord);
		
		if (coOrdinate.natureOfWord==3)
		{
			int j=coOrdinate.endCol;
			for (int i=coOrdinate.startRow;i<=coOrdinate.endRow;i++)
			{
				userCreatedWord+=arrayCellMainBoard[i][j].getLetter();
				System.out.print(arrayCellMainBoard[i][j].getLetter());
				
			}				
			
		}
		
		if (coOrdinate.natureOfWord==4)
		{
			int j=coOrdinate.endCol;
			for (int i=coOrdinate.startRow;i>=coOrdinate.endRow;i--)
			{
				userCreatedWord+=arrayCellMainBoard[i][j].getLetter();
				System.out.print(arrayCellMainBoard[i][j].getLetter());
				
			}
		}
		
		if (coOrdinate.natureOfWord==1)
		{
			int j=coOrdinate.endRow;				
			for (int i=coOrdinate.startCol;i<=coOrdinate.endCol;i++)
			{
				userCreatedWord+=arrayCellMainBoard[j][i].getLetter();
				System.out.print(arrayCellMainBoard[j][i].getLetter());
				
			}
		}
		
		
		if (coOrdinate.natureOfWord==2)
		{
			int j=coOrdinate.endRow;
			System.out.println("....\n\n... 2 corord is "+coOrdinate);
			for (int i=coOrdinate.startCol;i>=coOrdinate.endCol;i--)
			{
				userCreatedWord+=arrayCellMainBoard[j][i].getLetter();
				System.out.print(arrayCellMainBoard[j][i].getLetter());
				
			}
		}
			
		
		System.out.println("your new word is "+ userCreatedWord);
		
		forSending.wordScore=calculateScore(userCreatedWord);
		forSending.setWord(userCreatedWord);
		forSending.setCoOrdinate(coOrdinate);
		
		userCreatedWord="";
		
//		Platform.runLater(() -> {
//			statusTextAreaAboutServer.appendText("new word "+userCreatedWord+"\n"+forSending);
//			statusAboutWord.setText("new word "+userCreatedWord);
//		
//		});
//		statusTextAreaAboutServer.appendText("new word "+userCreatedWord+"\n"+forSending);
//		statusAboutWord.setText("new word "+userCreatedWord);
	}
	
	public int calculateScore(String s)
	{
		int score=0;
		String vowel="AEIOU";
		for (int i=0;i<s.length();i++)
		{
			char ch=s.charAt(i);
			
			if (vowel.contains(ch+""))
			{
				score+=2;
			}
			else{
				score+=1;
			}
			
		}
		score=score*s.length();
		
		return score;
		
	}
	
	
	public void connectToScarabbleServer()  {
		
		try {
			String t=tfSocket.getText();
			int soc=new Integer(t);
			
			socket=new Socket(tfIp.getText(),soc);
			System.out.println(" client has successfully connected with server");
			statusTextAreaAboutServer.appendText("client has successfully connected with server\n");
		} catch (UnknownHostException e1) {
			System.out.println("int connctotsrfunction unknow host exception");			
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		new Thread(()->{	
		//	System.out.println("c Starting a new client thread");
			try {
				waitingForTheGameToStart();
				
				while (true)
				{
					System.out.println(cpNumber+" i am in client connecttoserver while function");
					if (cpNumber==PLAYER1)
					{
						whilePlayingP1();
						
					}
					
					else if (cpNumber == PLAYER2) 
					{
						whilePlayingP2();
						
			        }
					
					else if (cpNumber == PLAYER3) 
					{
						whilePlayingP3();
					    
			        }
					
					else if (cpNumber == PLAYER4) 
					{
						whilePlayingP4();
			        }
					  
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
	}
	
	public void waitingForTheGameToStart() throws ClassNotFoundException, IOException
	{
		
		
		ScarableObject temp=new ScarableObject();
		temp.word=tfName.getText();
		System.out.println("name is : "+tfName.getText());
		new ObjectOutputStream (socket.getOutputStream()).writeObject(temp);
		temp=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
		
		cpNumber=temp.getPlayerNumber();
		cpFullName=cpNumber+": "+cpName;
	//	System.out.println("c: Client has received the player number "+clientPlayerNumber);
		
		
		String msg="this is client "+cpFullName+"  connected to server\n";
		writeToGui(msg);
		msg=null;
		System.out.println("fullname: "+cpFullName);
		Platform.runLater(() -> {
			statusAboutClientNumber.setText("this is "+cpFullName);
			statusAboutMyturn.setText("my turn "+myTurn);
		});
		
		
		if (cpNumber ==PLAYER1)
		{
			//	statusAboutMyturn.setText("my turn "+myTurn);
			msg=cpFullName+" now waiting for other "+(TOTALPLAYERNUMBER-1)+ " players to connect\n";
			
			writeToGui(msg);
			msg=null;
//			Platform.runLater(() -> {
//				statusTextAreaAboutServer.appendText(clientPlayerNumber+" now waiting for client 2 to connect\n");
//			});
//			
			ScarableObject temp2=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			msg="all connected "+cpFullName+" now can start playing game\n";
			writeToGui(msg);
			msg=null;
			
//			Platform.runLater(() -> {
//				statusTextAreaAboutServer.appendText(cpNumber+" now can start playing game\n");
//			});
			myTurn=true;					
		
		}
		
		else if (cpNumber== PLAYER2){
			if (cpNumber!=TOTALPLAYERNUMBER)
			{
				msg="waiting for other "+(TOTALPLAYERNUMBER-2) + " to be connected\n";
				writeToGui(msg);
				msg=null;
			}
			
//			Platform.runLater(() -> {
//				statusTextAreaAboutServer.appendText(cpNumber+" connected now the game has started\n");
//			});
			ScarableObject temp2=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			
			myTurn=false;					
		
		}
		
		else if (cpNumber== PLAYER3){
			if (cpNumber!=TOTALPLAYERNUMBER)
			{
				msg="waiting for other "+(TOTALPLAYERNUMBER-3) + " to be connected\n";
				writeToGui(msg);
				msg=null;
			}
			
			
//			Platform.runLater(() -> {
//				statusTextAreaAboutServer.appendText(cpNumber+" connected now the game has started\n");
//			});
			ScarableObject temp2=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			
			myTurn=false;					
		
		}

		else if (cpNumber== PLAYER4){
			if (cpNumber!=TOTALPLAYERNUMBER)
			{
				msg="waiting for other "+(TOTALPLAYERNUMBER-4) + " to be connected\n";
				writeToGui(msg);
				msg=null;
			}
//			Platform.runLater(() -> {
//				statusTextAreaAboutServer.appendText(cpNumber+" connected now the game has started\n");
//			});
			ScarableObject temp2=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			
			myTurn=false;					
		
		}
		
		
		
		
	}
	
	
	public void isOver()
	{
		System.out.println("i am in is over function");
		try {
			ScarableObject temp=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			
			System.out.println("\n\n\nis over.....server send "+temp.word);
			
			if (temp.word.equals("over"))
			{
				Platform.runLater(() -> {
					showResult(0);
				//	primaryStage.hide();
					isOver=true;
				});
				
			
				
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void whilePlayingP1() throws InterruptedException, IOException, ClassNotFoundException
	{

		System.out.println(cpNumber+" is in client while loop");
		waitForPlayerAction(); // Wait for player 1 to move
        sendMove(); // Send the move to the server
        
	     for (int i=1;i<TOTALPLAYERNUMBER;i++)
	     {
	        receiveInfoFromServer();
	        updateBoard();
	     }
	     
	     anotherRound();
	     waitForConfirmation();
	     isOver();
	     
	     
	      System.out.println("client 1 board has been updated and now it's 1's turn again");
			
	}
	
	
	public void whilePlayingP2() throws ClassNotFoundException, IOException, InterruptedException
	{
		
		
		System.out.println(cpNumber+" is in client while loop");
		Platform.runLater(() -> {
			statusTextAreaAboutServer.appendText(cpNumber+" is waiting for player1 to submit his word\n");
		});
		
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
       
        waitForPlayerAction(); // Wait for player 2 to move
        sendMove(); // Send player 2's move to the server
        for (int i=2;i<TOTALPLAYERNUMBER;i++)
        {
        	receiveInfoFromServer(); // Receive info from the server
            updateBoard();			           
        	
        }
         anotherRound();
         waitForConfirmation();
         isOver();
         
		
		
		
		
	}
	

	
	public void whilePlayingP3() throws ClassNotFoundException, IOException, InterruptedException
	{
		
		
		System.out.println(cpNumber+" is in client while loop");
		Platform.runLater(() -> {
			statusTextAreaAboutServer.appendText(cpNumber+" is waiting for player1 to submit his word\n");
		});
		
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
       
       
        waitForPlayerAction(); // Wait for player 2 to move
        sendMove(); // Send player 2's move to the server
        for (int i=3;i<TOTALPLAYERNUMBER;i++)
        {
        	receiveInfoFromServer(); // Receive info from the server
            updateBoard();			           
        	
        }
        anotherRound();
        waitForConfirmation();
        
        isOver();
        
		
		
	}
	

	
	public void whilePlayingP4() throws ClassNotFoundException, IOException, InterruptedException
	{
		
		System.out.println(cpNumber+" is in client while loop");
		Platform.runLater(() -> {
			statusTextAreaAboutServer.appendText(cpNumber+" is waiting for player1 to submit his word\n");
		});
		
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
        receiveInfoFromServer(); // Receive info from the server
        updateBoard();
       
       
        waitForPlayerAction(); // Wait for player 2 to move
        sendMove(); // Send player 2's move to the server
        anotherRound();
	    waitForConfirmation();
	    
	    isOver();	
		
	}
	
	public void anotherRound()
	{
		System.out.println("in another round funtion" );
	
		Platform.runLater(()->{
			Stage stage=new Stage();
			
			
			GridPane pane2=new GridPane();
			
			pane2.setAlignment(Pos.CENTER);
			pane2.setPadding(new Insets(11.5,12.5,13.5,14.5));
			pane2.setHgap(10);
			pane2.setVgap(10);
			
			
			Label word=new Label("This round is over.\n do you want to play another round...");
			Button ok=new Button("ok");
			Button over=new Button("over");
			

			pane2.add(word, 4, 3,5,6);
			pane2.add(ok, 3, 12);
			pane2.add(over, 8, 12);
			

			
			ScarableObject obOk=new ScarableObject();
			ScarableObject obOver=new ScarableObject();
			obOk.word="ok";
			
			ok.setOnMouseClicked(e->{
				System.out.println("ok has been clicked");
				stage.hide();
				obOk.word="ok";
				try {
					new ObjectOutputStream(socket.getOutputStream()).writeObject(obOk);
					System.out.println("successfully send ok to server");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isConfirmed=false;
				
			});
			over.setOnMouseClicked(e->{
				System.out.println("over has been clicked");
				stage.hide();
				
	//			ScarableObject temp=new ScarableObject();
				obOver.word="over";
				isConfirmed=false;
				try {
					new ObjectOutputStream(socket.getOutputStream()).writeObject(obOver);
					System.out.println("successfully send over to server");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isConfirmed=false;
			});
			
			Scene scene2 =new Scene(pane2,400,200);
			scene2.getStylesheets().add(CellBox.class.getResource("scene4.css").toExternalForm());
			
			
			stage.setScene(scene2);
			stage.setTitle(cpNumber +"'s new round window");
			stage.show();
//			try {
//				new ObjectOutputStream(socket.getOutputStream()).writeObject(obOk);
//				System.out.println("successfully send  server: "+obOk.word);
//				
//			
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
			

			System.out.println("getting out of chengeword fuction "+obOk.word+" and "+obOver.word);
			
			
		});	
	}
	
	
	
	public void update(int r,int c,char ch)
	{
		arrayCellMainBoard[r][c].cellLetter=ch;
	//	System.out.println("trying to find if array changed "+arrayCellMainBoard[r][c].cellLetter);
		Platform.runLater(()->{
			arrayCellMainBoard[r][c].change();
		//	arrayCellMainBoard[a][b].change();
		
		});
		
	}

	public void updateBoard()
	{
		
		
		System.out.println("trying to update gui with "+fromServer);
		int m=0;
		
		if (fromServer.coOrdinate.natureOfWord==3)
		{
			int j=fromServer.coOrdinate.endCol;
			for (int i=fromServer.coOrdinate.startRow;i<=fromServer.coOrdinate.endRow;i++)
			{
				char ch=fromServer.word.charAt(m++);
				update(i, j, ch);
			}
		}
		
		if (fromServer.coOrdinate.natureOfWord==4)
		{
			int j=fromServer.coOrdinate.endCol;
			for (int i=fromServer.coOrdinate.startRow;i>=fromServer.coOrdinate.endRow;i--)
			{
				char ch=fromServer.word.charAt(m++);
				update(i, j, ch);
			
			}
		}
		
		if (fromServer.coOrdinate.natureOfWord==1)
		{
			int j=fromServer.coOrdinate.endRow;
			
				for (int i=fromServer.coOrdinate.startCol;i<=fromServer.coOrdinate.endCol;i++)
				{
			//		System.out.println("update gui loop loop ....calling another funcion");
					char ch=fromServer.word.charAt(m++);
					update(j, i, ch);
				}
		}
		
		
		if (fromServer.coOrdinate.natureOfWord==2)
		{
			int j=fromServer.coOrdinate.endRow;
		//	System.out.println("....\n\n... 2 corord is "+coOrdinate);
			for (int i=fromServer.coOrdinate.startCol;i>=fromServer.coOrdinate.endCol;i--)
			{
				char ch=fromServer.word.charAt(m++);
				update(j, i, ch);
			
			}
		}
		System.out.println("gui has been updated");
		
		
	}
	
	
	private void waitForPlayerAction() throws InterruptedException {
		myTurn=true;
		Platform.runLater(()->{
			statusTextAreaAboutServer.appendText(cpNumber+" is waiting for user Input click submit button\n");
			statusAboutMyturn.setText("myTurn: "+myTurn);
		});
	    while (waiting) {
	      Thread.sleep(100);
	    }

	    waiting = true;
	  }
	
	public void updatep(int i,int j,char ch)
	{
		arrayCellPlayer1[i][j].cellLetter=ch;
		//	System.out.println("trying to find if array changed "+arrayCellMainBoard[r][c].cellLetter);
			Platform.runLater(()->{
				arrayCellPlayer1[i][j].change();
			//	arrayCellMainBoard[a][b].change();
			
			});
		
	}
	private void sendMove() throws IOException
	{
		Platform.runLater(()->{
			statusTextAreaAboutServer.appendText(cpNumber+" is trying to send: "+forSending+"\n");
		});
		
		System.out.println("letter that has been used\n\n\n\n");
		for (int i=0;i<5;i++)
		{
			for (int j=0;j<2;j++)
			{
			//	k=rn.nextInt(26);
				if (arrayCellPlayer1[i][j].cellLetter=='X')
				{
					char ch='A';
					int n=rn.nextInt(26);
					ch=(char) (ch+n);
					
					updatep(i, j, ch);
					
					
				}
				System.out.println(arrayCellPlayer1[i][j].cellLetter);
			}
		}
		
		
		sendToServer(forSending);
	}
	
	public void challengeWordGui()
	{
		System.out.println("in challenge word...server send "+fromServer);
	
		Platform.runLater(()->{
			Stage stage=new Stage();
			GridPane pane2=new GridPane();
			
			pane2.setAlignment(Pos.CENTER);
			pane2.setPadding(new Insets(11.5,12.5,13.5,14.5));
			pane2.setHgap(10);
			pane2.setVgap(10);
			
			
			Label word=new Label("player send: \n"+fromServer.word+"\ndo you accept this or challege");
			Button ok=new Button("ok");
			Button challenge=new Button("challenge");
			
			
			
		//	flowPane2.getChildren().addAll(word,ok,cancel);
//			pane2.setCenter(word);
//			pane2.setLeft(ok);
//			pane2.setRight(challenge);
			pane2.add(word, 4, 3,5,6);
			pane2.add(ok, 3, 12);
			pane2.add(challenge, 8, 12);
			
			
			ScarableObject obOk=new ScarableObject();
			ScarableObject obCancel=new ScarableObject();
			obOk.word="ok";
			
			ok.setOnMouseClicked(e->{
				System.out.println("ok has been clicked");
				stage.hide();
				obOk.word="ok";
				try {
					new ObjectOutputStream(socket.getOutputStream()).writeObject(obOk);
					System.out.println("successfully send ok to server");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isConfirmed=false;
				
			});
			challenge.setOnMouseClicked(e->{
				System.out.println("challenge has been clicked");
				stage.hide();
				
	//			ScarableObject temp=new ScarableObject();
				obCancel.word="challenge";
				isConfirmed=false;
				try {
					new ObjectOutputStream(socket.getOutputStream()).writeObject(obCancel);
					System.out.println("successfully send challenge to server");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isConfirmed=false;
			});
			
			Scene scene4 =new Scene(pane2,500,600);
			scene4.getStylesheets().add(CellBox.class.getResource("scene4.css").toExternalForm());
			
			
			stage.setScene(scene4);
			stage.setTitle(cpNumber +" 's accept or reject window");
			stage.show();
//			try {
//				new ObjectOutputStream(socket.getOutputStream()).writeObject(obOk);
//				System.out.println("successfully send  server: "+obOk.word);
//				
//			
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
			

			System.out.println("getting out of chengeword fuction "+obOk.word+" and "+obCancel.word);
			
			
		});	
	}
	
	
	
	
	public void waitForConfirmation()
	{
		System.out.println("\nin waitforconformation... function waiting....\n");
		
		
		while (isConfirmed)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("in waitfor... function and client has confirmed\n");
		isConfirmed=true;
		
	}
	
	public void receiveInfoFromServer() throws ClassNotFoundException, IOException
	{
		Platform.runLater(()->{
			statusTextAreaAboutServer.appendText(cpNumber+" is trying to receive information from server\n");
		});
		System.out.println("c "+cpNumber+" is waiting to receive information from server and my turn is "+myTurn);
		fromServer=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
	//	System.out.println("goint to make a pop up window");
		
		
		challengeWordGui();
		
		System.out.println("i am waiting to be clicked ok or cancel ");
		waitForConfirmation();
		
		System.out.println("waiting for challenge score");
		ScarableObject temp=(ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
		playerInfoList.clear();
		playerInfoList.addAll(temp.playerInfoList);
		
		System.out.println("\n\nMy challenge word point is: "+temp.playerInfoList.toString()+"\n\n");
		
		System.out.println("getting out of receiveInfoFrom server function ");
	//	myTurn=true;
		
	}
	
	public void sendToServer(ScarableObject ob) throws IOException
	{		
		System.out.println(cpNumber+"clientconsole: Send to server "+ob);
		
	
		new ObjectOutputStream(socket.getOutputStream()).writeObject(ob);	
		myTurn=false;
		Platform.runLater(()->{
			statusAboutMyturn.setText("my turn: "+myTurn);
		});
		ScarableObject temp;
		try {
			temp = (ScarableObject) new ObjectInputStream(socket.getInputStream()).readObject();
			playerInfoList.clear();
			playerInfoList.addAll(temp.playerInfoList);
			
			System.out.println("My challenge word point is: "+temp.resultOfChallenge);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	output.flush();
			
	}
	
	
	

	
	
	public static void main(String[] args) {
			System.out.println("ClientScrable");
			launch(args);	
	}
	
	
	
	
	
	public void mainBoard()
	{
		char ch=' ';
		int n=6;
		for (int i=0,k=0;i<n;i++)
		{
			for (int j=0;j<n;j++,k++)
			{
				arrayCellMainBoard[i][j]=new Cell(this, (char) (ch),'m',i,j);
			}
		}
		
	//	System.out.println(Arrays.toString(arrayCellMainBoard));
		
		for (int i=0,k=0;i<n;i++)
		{
			for (int j=0;j<n;j++)
			{
				gridPaneMainBoard.add(arrayCellMainBoard[i][j], j, i);
			}
		}
	}
	
	public void player1Board()
	{
		char ch='A';
		int n=5;
		for (int i=0,k=0;i<n;i++)
		{
			for (int j=0;j<2;j++,k++)
			{
				k=rn.nextInt(26);
				arrayCellPlayer1[i][j]=new Cell(this, (char) (ch+k),'c',i,j);
			}
		}
		
		for (int i=0;i<n;i++)
		{
			for (int j=0;j<2;j++)
			{
			//	System.out.println(" player1 function");
				gridPanePlayer1.add(arrayCellPlayer1[i][j], i, j);
				
			}
		}	
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private MediaPlayer createMediaPlayer(final String filename) throws MalformedURLException {
	    File file = new File(filename);
	    if (!file.exists()) {
	      
	      System.out.println("file doesnt exit");
	
	      System.exit(1);
	      
	    }
	    
	    
	    final String mediaLocation = file.toURI().toURL().toExternalForm();
	    System.out.println(mediaLocation);
	    
	    Media media = new Media(mediaLocation);
	    MediaPlayer mediaPlayer = new MediaPlayer(media);

	    return mediaPlayer;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
