
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class Shapes extends Application
{
	
	@Override
	public void start(Stage stage)     
	{ 
				//tell user other choices
				Text actionNote = new Text(68, 240, "Choose a shape.\nAlso you can change the colour of your shape.");         
				actionNote.setFill(Color.BLUE);         
				actionNote.setFont(Font.font ("Verdana", 15));
				
				
				//create a container for texts
				HBox textContainer = new HBox(10);
				textContainer.setAlignment(Pos.CENTER);
				textContainer.getChildren().addAll(actionNote);
				
				
				//create the shape of circle
				Circle circle = new Circle(150, 150, 80);      
				circle.setFill(Color.BLACK);
				
				
				//create the shape of rectangle
				Rectangle rectangle = new Rectangle(100, 125, 200, 130);
				rectangle.setFill(Color.BLACK);
				
				
				//create the shape of octagon
				Polygon octagon = new Polygon();
				octagon.getPoints().addAll(new Double[]{
						100.0, 60.0,
						200.0, 60.0,
						250.0, 120.0,
						250.0, 180.0,
						200.0, 240.0,
						100.0, 240.0,
						50.0, 180.0,
						50.0, 120.0});
				octagon.setFill(Color.BLACK);
				
				//create an empty container for the shape shown
				VBox shapeContainer = new VBox(10);
				shapeContainer.setAlignment(Pos.CENTER);
				
				//create a button for ending this game
				Button endButton = new Button("Quit this game.");
				//executions of this button£¬ exit the game
				endButton.setOnAction(e -> {stage.close();});
				endButton.setAlignment(Pos.BASELINE_CENTER);
				
				
				//create a button for the shape choice of circle
				Button circleButton = new Button("Circle"); 
				//create buttons for other choices of colors or a circle
				Button bluecircleButton = new Button("Blue"); 
				Button greencircleButton = new Button("Green"); 
				Button yellowcircleButton = new Button("Yellow"); 
				
				
				
				//create a button to change to rectangle
				Button rectangleButton = new Button("Rectangle"); 
				
				Button bluerectangleButton = new Button("Blue"); 
				Button greenrectangleButton = new Button("Green"); 
				Button yellowrectangleButton = new Button("Yellow"); 
				
				
				//create a button for the shape choice of octagon
				Button octagonButton = new Button("Octagon"); 
				
				Button blueoctagonButton = new Button("Blue"); 
				Button greenoctagonButton = new Button("Green"); 
				Button yellowoctagonButton = new Button("Yellow");
				
				//create an horizontal container for multiple shape choices
				HBox multiContainer = new HBox(18);
				multiContainer.setAlignment(Pos.CENTER);
				multiContainer.getChildren().addAll(circleButton, rectangleButton, octagonButton);
				
				//create an empty container for buttons of colour choices
				HBox colorBox = new HBox(18);
				colorBox.setAlignment(Pos.CENTER);
				
				
				//action after clicking the button of circle
				circleButton.setOnAction(e -> {
						colorBox.getChildren().clear();
						shapeContainer.getChildren().clear();
						colorBox.getChildren().addAll(bluecircleButton, greencircleButton, yellowcircleButton);
						shapeContainer.getChildren().addAll(circle);});
				
				//action after clicking the button of rectangle
				rectangleButton.setOnAction(e -> {
						colorBox.getChildren().clear();
						shapeContainer.getChildren().clear();
						colorBox.getChildren().addAll(bluerectangleButton, greenrectangleButton, yellowrectangleButton);
						shapeContainer.getChildren().addAll(rectangle);});
				
				//action after clicking the button of Octagon
				octagonButton.setOnAction(e -> {
					colorBox.getChildren().clear();
					shapeContainer.getChildren().clear();
					colorBox.getChildren().addAll(blueoctagonButton, greenoctagonButton, yellowoctagonButton);
					shapeContainer.getChildren().addAll(octagon);});
				
				
				//actions after executing color buttons
				bluecircleButton.setOnAction(e ->{circle.setFill(Color.BLUE);});
				greencircleButton.setOnAction(e ->{circle.setFill(Color.GREEN);});
				yellowcircleButton.setOnAction(e ->{circle.setFill(Color.YELLOW);});
				
				bluerectangleButton.setOnAction(e ->{rectangle.setFill(Color.BLUE);});
				greenrectangleButton.setOnAction(e ->{rectangle.setFill(Color.GREEN);});
				yellowrectangleButton.setOnAction(e ->{rectangle.setFill(Color.YELLOW);});
				
				blueoctagonButton.setOnAction(e ->{octagon.setFill(Color.BLUE);});
				greenoctagonButton.setOnAction(e ->{octagon.setFill(Color.GREEN);});
				yellowoctagonButton.setOnAction(e ->{octagon.setFill(Color.YELLOW);});
				
				
				
				// create a vertical container to hold action note, shape button container and quit button container      
				VBox root = new VBox(25); 
				root.setBackground(Background.EMPTY);
				root.setAlignment(Pos.CENTER); 
				
				//add action note and two containers to the vertical container         
				root.getChildren().addAll(textContainer, multiContainer, shapeContainer, colorBox, endButton);
				
				//create and configure a new scene         
				Scene scene = new Scene(root, 400, 400, Color.ORANGE);
				
				//give user their first choice 
				String shapename = JOptionPane.showInputDialog("What shape do you want?\nEnter 1 - Circle\nEnter 2 - Rectangle\nEnter 3 - Octagon\nEnter 5 - End this game.");

				//evaluate the user input and give corresponding response
				if (shapename == null || shapename.equals(""))
				{
					JOptionPane.showMessageDialog(null, "You didn't enter any shape name.\nYou can choose one on the next page by simply clicking a button.");
				}
				else if ((shapename.equals("1"))||(shapename.equals("2"))||(shapename.equals("3"))||(shapename.equals("5")))
				{
					//show the image according to the user keyboard input
					switch (shapename) {
						case "1":
							shapeContainer.getChildren().addAll(circle);
							colorBox.getChildren().addAll(bluecircleButton, greencircleButton, yellowcircleButton);
							break;
							
						case "2":
							shapeContainer.getChildren().addAll(rectangle);
							colorBox.getChildren().addAll(bluerectangleButton, greenrectangleButton, yellowrectangleButton);
							break;
							
						case "3":
							shapeContainer.getChildren().addAll(octagon);
							colorBox.getChildren().addAll(blueoctagonButton, greenoctagonButton, yellowoctagonButton);
							break;
						
						case "5":
							stage.close();
							System.exit(0);         }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Your choice is out of range.\nYou can choose one on the next page by simply clicking a button.");
				}
				
				// add the scene to the stage
				stage.setScene(scene);  
				//set the title of the scene
				stage.setTitle("Shape game"); 
				//show the stage
				stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);  
		 
	}
	
	
	
}
