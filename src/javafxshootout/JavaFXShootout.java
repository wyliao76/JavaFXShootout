/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxshootout;

import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author cocofan
 */
public class JavaFXShootout extends Application {
    Random rand;   // Random object for getting a random shot
    Canvas canvas; // a pane for drawing shapes to the window
    GraphicsContext gc; // an object for drawing shapes. You
                        // call methods from this object and
                        // it will draw shapes to the canvas pane.
    StackPane root;    // an underlying pane.  You dont need to
                       // deal with this window unless you want
                       // to add controls.  Not needed for this 
                       // project
    
    // information for drawing the target and sizing the window
    double arrowX, arrowY;  // the x and y values of where the 
                            // arrow's point fell
    
    double ringSize;         // the thickness in pixels of the rings
    double numRings;         // the number of rings 1-9 in range
    double targetSize;       // the diameter of the whole target
                             // This is calculated so no need to set.
    double centerX, centerY; // the center of the target
                             // this is calculated from the targetSize
    double paneHt, paneWt;   // Size of the window pane that the 
                             // is in. This is also calculated.
    double margin;           // the amount of space to leave between
                             // the edge of the window and the target.
    
    // information for playing the game.
    double sum;      // The sum of all the scores 
    double rounds;   // how many shots to make
    double round=0;  // the current round
    
    // add more colors if you want more rings. Right 
    // now it works with up to 10 rings
    Color[] availColors = {Color.WHITE, Color.CYAN,
                           Color.GOLD, Color.LIGHTGREEN,
                           Color.PURPLE, Color.BLUE,
                           Color.YELLOW, Color.RED, 
                           Color.GREY, Color.LIGHTBLUE,
                           Color.BLACK,Color.ALICEBLUE};
    
    // This is the default constructor.  This gets created by
    // the main method inside the Application class.
    // This is mainly to initialize fields needed later.
    
    public JavaFXShootout() {
        super();  // calls the 
        sum = 0;
        rand = new Random();
        
        // these are default values
        // you can change these here
        ringSize = 40;
        numRings = 8;
        rounds = 10;
        
        // target radius should be thickness of rings X number of rings
        targetSize = ringSize*numRings;
        
        // this calculates the space from the edge of the
        // window to the target.
        // I'm using 25% of the diameter of the target.
        // If you need more space around the target, change this
        // value.
        margin = Math.ceil(targetSize*.25);
        
        // these are the center x and y values of the
        // target. Probably dont need to change this.
        centerX = targetSize+margin;
        centerY = centerX;
        
        // this is the window that holds the target
        // I made the height of the window about 2.5
        // times the target to get some space at the
        // bottom to write the score sum.
        paneHt = Math.ceil(targetSize*2.5)+margin;
        paneWt = paneHt-margin;
        
        // this is a pane that allows for 
        // drawing on the window
        canvas = new Canvas(paneWt, paneHt);
        
        // this is an object that you use
        // to draw on the canvas window
        gc = canvas.getGraphicsContext2D();
        
        // this is an underlying window
        // you don't need to bother with this.
        // unless you want to add controls to it.
        root = new StackPane();
        
    }
     
    
    // This is where your program starts.  Note that
    // main just calls Application's launch method which
    // will eventually call this method.
    @Override
    public void start(Stage primaryStage) {
        // add canvas pane to list of panes the
        // root pane takes care of (children)
        root.getChildren().add(canvas);
        
        // this is the window that holds the root pane
        // and also the canvas pane.
        // I'm making it the same size as the canvas.
        // If your adding any controls, make this bigger.
        Scene scene = new Scene(root, paneHt, paneWt);
        
        // this is the frame of the window. 
        // Here you can set the title in the top of the window
        primaryStage.setTitle("JavaFX Shootout");
        primaryStage.setScene(scene);
        
        //show the window
        primaryStage.show();
        
        // start the game here
        // this is where the target gets drawn
        // and n rounds of shots are created.

        startGame();
    }
    
    public void startGame() {
        // draw the target
        drawTarget();
        
        // for each round of shot....
        for(round=0; round < rounds; round++) {
           shoot(); // determine random x and y values where the arrow landed
           drawArrow(); //draw the arrow at this location
           score(); // determine score and add to total
        }
        endOfGame(); // print out final score at bottom of window
    }

    private void drawRing(Color color, double centerx, double centery, double radius) {
         //TODO:  write code to draw one ring of the target
         //   you can do this by drawing a circle filled with
         //   given color. then draw another non-filled circle
         //    in black to draw the edge of the ring.
        gc.setFill(color);
        gc.fillOval(centerx-radius, centery-radius, radius*2, radius*2); 
        gc.setStroke(Color.BLACK);
        gc.strokeOval(centerx-radius, centery-radius, radius*2, radius*2);
    }
    
    private void drawArrow() {
        //TODO: write to code to draw an arrow at the arrowX
        // and arrowY position after a shot.
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
     //   gc.setLineWidth(5);
     //   gc.strokeLine(arrowX-20,arrowY-20, arrowX, arrowY);
        gc.strokeText("-->o",arrowX , arrowY);
        // I tried other colors but it will overlap with the same color.
    }

    private void drawTarget() {
        //TODO: write code to draw a target
        // use a for loop to draw each ring
        // using drawRing.  Start with the
        // outer ring a put each smaller, inner
        // ring on top of each larger, outer ring.
        for (int i =0; i<8; i++){
        drawRing(availColors[i],centerX,centerY,targetSize-5*i*8);
        }
    }
    
    // this method randomly generates the x and y
    // coordinates for one shot. 
    //  you probably dont need to change this method.
    private void shoot() {
        int rangeX = (int) (paneHt-2*margin);
        int rangeY = (int) (paneWt-2*margin);
        int x = (int) (rand.nextInt(rangeX) + margin);
        int y = (int) (rand.nextInt(rangeY) + margin);
        
        arrowX = x;
        arrowY = y;
      // sometime the shots went out of bounds? 
    }
    
    
    private void drawScore(int score) {
      //TODO: this draws the current score on the window
      // somewhere.  I put it near the arrow but you can
      // draw it anywhere that makes sense. Like maybe on
      // the left or right side in a column.
      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
      gc.fillText(""+ score, arrowX, arrowY-20);
    }
    
    // This calculates the score. Note that
    // the score increases the closer to
    // the center the shot was placed.
    // You probably dont need to change this method.
    private void score() {
        // x and y is how far off from center 
        // the shot landed
      double x = centerX-arrowX;
      double y = centerY-arrowY;
      // get radial distance from shot
      double dist = Math.sqrt(x*x + y*y);
      
      // this determines which ring it landed in
      // minus number of rings (outer rings are
      // worth less than inner rings)
      int score = (int) Math.floor(numRings - dist/ringSize)+1;
      if(score < 0) // needed for shots that fell outside all rings
          score = 0;
      //draw the score near the arrow (or wherever you want to put it)
      drawScore(score);
      // add to cummulative score
      sum += score;
      
    }
    
    
    public void endOfGame() {
      //TODO: write code to write text to the canvas
      // to indicate the final sum for the game.
      gc.setFill(Color.PURPLE);
      gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
      gc.fillText("The Total Score is: "+ sum, 200, 800); 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         // Please dont touch this method at all.
        launch(args);
    }
}
