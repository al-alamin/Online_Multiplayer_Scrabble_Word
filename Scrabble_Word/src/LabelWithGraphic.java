import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LabelWithGraphic extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
//    ImageView us = new ImageView(new Image("image/us.gif"));
//    Label lb1 = new Label("US\n50 States", us);
//    lb1.setStyle("-fx-border-color: green; -fx-border-width: 2");
//    lb1.setContentDisplay(ContentDisplay.BOTTOM);
//    lb1.setTextFill(Color.RED);
	  
	 VBox pane=new VBox(50);
	  
	  Label label1=new Label("this is alamin \n ans who the \n fuck are you\n",new Circle(20,20,20));
	  Label label2=new Label("\n this is a test\nlets see\n",new Circle(30,30,30));
	  
	  pane.getChildren().addAll(label1,label2);
	  
	  
	  Scene scene=new Scene(pane,500,500);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	  
	  
  }  
  public static void main(String[] args) {
    launch(args);
  }
}
