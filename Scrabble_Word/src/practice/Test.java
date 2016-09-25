package practice;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Test extends Pane {
	
	
	FlowPane flowPane=new FlowPane();
	Scene scene = new Scene(flowPane,400,400);
	
	public Test()
	{
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("secondary");
		stage.show();
		
	}
	
	
	
	
	

}



