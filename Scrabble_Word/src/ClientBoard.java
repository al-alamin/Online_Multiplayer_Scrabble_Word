import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class ClientBoard extends Application {
	private Label[] arrayLabel=new Label[50];
	
	public void loadArrayLabel(char c,int n)
	{
		
		for (int i=0;i<30;i++)
		{
			int m=n*i;
			char ch=(char) (c+m);
			
			Label a=new Label(" "+ch+" ");
			a.setStyle("-fx-border-color: red;-fx-border-width:3;");
			a.setOnMouseClicked(e->{
				System.out.println("label a has been clicked");
				
			});
			
			arrayLabel[i]=a;
			
		}
	}
	public void start(Stage primaryStage) throws Exception {
		loadArrayLabel(' ',0);
		
		
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(20,10,10,10));
		grid.setVgap(25);
		grid.setHgap(25);
		
		
		
		
		
		
		
		
		Label a=new Label("X");
		a.setStyle("-fx-border-color: red;-fx-border-width:3;");
		a.setOnMouseClicked(e->{
			System.out.println("label a has been clicked");
			
		});
		
		int n=5;
		for (int i=0,k=0;i<n;i++)
		{
			for (int j=0;j<n;j++,k++)
			{
				grid.add(arrayLabel[k],i,j);
			}
		}


//		grid.add(arrayLabel[0], 0, 0);
//		grid.add(arrayLabel[1], 1, 0);
//		grid.add(arrayLabel[2], 2, 0);
//		grid.add(arrayLabel[3], 3, 1);
		
		
		GridPane grid2=new GridPane();
		grid2.setPadding(new Insets(20,10,10,10));
		grid2.setVgap(25);
		grid2.setHgap(25);
		
		loadArrayLabel('A', 1);
		
		for (int i=0,k=0;i<n;i++)
		{
			for (int j=0;j<n;j++,k++)
			{
				grid2.add(arrayLabel[k],i,j);
			}
		}
		
		BorderPane borderPane=new BorderPane();
		borderPane.setCenter(grid);
		borderPane.setRight(grid2);
		
		
		
		
		
		
		
		
		Scene scene=new Scene(borderPane,600,400);
		primaryStage.setTitle("copy server");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
		
		
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		launch(args);

	}

}
