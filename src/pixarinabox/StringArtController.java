/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixarinabox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hruteesh Raja
 */
public class StringArtController implements Initializable {
    public static boolean squareClicked=false;
    public Canvas C;
    public AnchorPane CA;
    public Label M;
    public int pointsPlaced=0;
    public Circle eP1;
    public Circle eP2;
    public Circle cP;
    public Line cP1;
    public Line cP2;
    public boolean eP1Pressed=false;
    public boolean eP2Pressed=false;
    public boolean cPPressed=false;
    public static Point first=null,second=null;
    public Slider pointSlider;
    public Label numOfPoints;
    public boolean slider=false;
    ArrayList<Point> ce1=new ArrayList<>();
    ArrayList<Point> ce2=new ArrayList<>();
    public Line lines[]=new Line[25];
    public Line paraLines[]=new Line[25];
    
    Rectangle current=null;
    boolean connected[];
    public int prev;
    public int numOfConnected=0;
    public Slider lineColor;
    public Slider helpLineColor;
    public Slider lineWidth;
    public Slider helpLineWidth;
    public void lineColorSlider(){
        if(slider){
            int points=Integer.parseInt(numOfPoints.getText());
            if(numOfConnected==points){    
                for(int i=0;i<points;i++){
                    paraLines[i].setStroke(Color.web(Integer.toHexString((int)lineColor.getValue())));
                }
            }
        }
    }
    public void helpLineColorSlider(){
        if(slider){
            cP1.setStroke(Color.web(Integer.toHexString((int)helpLineColor.getValue())));
            cP2.setStroke(Color.web(Integer.toHexString((int)helpLineColor.getValue())));
            
        }
    }
    public void lineWidthSlider(){
        if(slider){
            int points=Integer.parseInt(numOfPoints.getText());
            if(numOfConnected==points){    
                for(int i=0;i<points;i++){
                    paraLines[i].setStrokeWidth(lineWidth.getValue());
                }
            }

        }
    }
    public void helpLineWidthSlider(){
        if(slider){
            cP1.setStrokeWidth(helpLineWidth.getValue());
            cP2.setStrokeWidth(helpLineWidth.getValue());
        }            
    }
    public void slided(){
        if(slider){
            numOfPoints.setText(Integer.toString((int)Math.floor(pointSlider.getValue())));
            if(pointsPlaced>3){
                redoPoints();
                
            }
            removeLines();
            for(int i=0;i<25;i++){
                lines[i].setVisible(false);
            }
            numOfConnected=0;
        }
    }
    
    public void sliderPressed(){
        slider=true;
        prev=Integer.parseInt(numOfPoints.getText());
    }
    public void sliderReleased(){
        slider=false;
        
    }
    
    public void back(ActionEvent e) throws Exception{
        Parent Square =  FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene SquareScene = new Scene(Square);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    
    

    
    public void redoPoints(){
            int points=Integer.parseInt(numOfPoints.getText());
            double dx1=eP1.getCenterX()-cP.getCenterX();
            double dy1=eP1.getCenterY()-cP.getCenterY();
            double dx2=eP2.getCenterX()-cP.getCenterX();
            double dy2=eP2.getCenterY()-cP.getCenterY();
            dx1/=(points+1);
            dy1/=(points+1);
            dx2/=(points+1);
            dy2/=(points+1);
            for(int i=1;i<=points;i++){
                ce1.get(i-1).x=eP1.getCenterX()-i*dx1;
                ce1.get(i-1).y=eP1.getCenterY()-i*dy1;
                ce2.get(i-1).x=eP2.getCenterX()-i*dx2;
                ce2.get(i-1).y=eP2.getCenterY()-i*dy2;
                ce2.get(i-1).set();
                ce1.get(i-1).set();
                ce1.get(i-1).number=i;
                ce2.get(i-1).number=-points+i-1;
                if(!ce1.get(i-1).added){
                    ce1.get(i-1).added=true;
                    CA.getChildren().add(ce1.get(i-1).rect);
                    ce2.get(i-1).added=true;
                    CA.getChildren().add(ce2.get(i-1).rect);
                }
                ce1.get(i-1).rect.setVisible(true);
                ce2.get(i-1).rect.setVisible(true);
                
                lines[i-1].setStartX(ce1.get(i-1).x);
                lines[i-1].setStartY(ce1.get(i-1).y);
                lines[i-1].setEndX(ce2.get(points-i).x);
                lines[i-1].setEndY(ce2.get(points-i).y);
            
                
                
                final Point r1=ce1.get(i-1);
                final Point r2=ce2.get(i-1);
                
                ce1.get(i-1).rect.setOnMouseClicked((t)->{
                    if(StringArtController.first==null){
                        
                        StringArtController.first=r1;
                        if(lines[(int)Math.abs(first.number)-1].isVisible()){
                            lines[(int)Math.abs(first.number)-1].setVisible(false);
                            connected[(int)Math.abs(first.number)-1]=false;
                            numOfConnected--;
                        }
                    }
                    else{
                        StringArtController.second=r1;
                        connect();
                        first=null;
                        second=null;
                        currentLine.setVisible(false);
                    }
                });
                ce2.get(i-1).rect.setOnMouseClicked((t)->{
                    if(StringArtController.first==null){
                        
                        StringArtController.first=r2;
                        if(lines[(int)Math.abs(first.number)-1].isVisible()){
                            lines[(int)Math.abs(first.number)-1].setVisible(false);
                            connected[(int)Math.abs(first.number)-1]=false;
                            numOfConnected--;
                        }
                    }
                    else{
                        StringArtController.second=r2;
                        connect();
                        first=null;
                        second=null;
                        currentLine.setVisible(false);
                    }
                });
                
            }
            for(int i=points+1;i<=25;i++){
                ce1.get(i-1).rect.setVisible(false);
                ce2.get(i-1).rect.setVisible(false);
                
            }
            if(paraLines[0].isVisible()){
                setParabola();
            }
            
        
        
    }
    
    public void PlacedPoint(MouseEvent e){
        if(pointsPlaced<3){
            if(pointsPlaced==0){
                eP1=new Circle();
                eP1.setRadius(12);
                CA.getChildren().add(eP1);
                eP1.setFill(javafx.scene.paint.Color.BLUE);
                eP1.setCenterX(e.getX());
                eP1.setCenterY(e.getY());
                eP1.setOnMouseDragged((t)->{
                    double x=t.getX();
                    double y=t.getY();
                    x=x<25?25:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    eP1.setCenterX(x);
                    eP1.setCenterY(y);
                    cP1.setStartX(x);
                    cP1.setStartY(y);
                    if(pointsPlaced>3){
                        redoPoints();
                    }
                });
            }
            else if(pointsPlaced==1){
                eP2=new Circle();
                eP2.setRadius(12);
                eP2.setFill(javafx.scene.paint.Color.BLUE);
                CA.getChildren().add(eP2);
                eP2.setCenterX(e.getX());
                eP2.setCenterY(e.getY());
                eP2.setOnMouseDragged((t)->{
                    double x=t.getX();
                    double y=t.getY();
                    x=x<25?25:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    eP2.setCenterX(x);
                    eP2.setCenterY(y);
                    cP2.setStartX(x);
                    cP2.setStartY(y);
                    if(pointsPlaced>3){
                        redoPoints();
                    }
                });
            }
            else{
                cP=new Circle();
                cP.setRadius(12);
                cP.setFill(javafx.scene.paint.Color.RED);
                CA.getChildren().add(cP);
                cP.setCenterX(e.getX());
                cP.setCenterY(e.getY());
                cP.setOnMouseDragged((t)->{
                    double x=t.getX();
                    double y=t.getY();
                    x=x<25?25:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    cP.setCenterX(x);
                    cP.setCenterY(y);  
                    cP1.setEndX(x);
                    cP1.setEndY(y);
                    cP2.setEndX(x);
                    cP2.setEndY(y);
                    if(pointsPlaced>3){
                        redoPoints();
                    
                    }
                });
            }
            pointsPlaced++;
        }
        if(pointsPlaced==3){
            cP1=new Line();
            cP2=new Line();
            cP1.setStartX(eP1.getCenterX());
            cP1.setStartY(eP1.getCenterY());
            cP1.setEndX(cP.getCenterX());
            cP1.setEndY(cP.getCenterY());
            cP2.setStartX(eP2.getCenterX());
            cP2.setStartY(eP2.getCenterY());
            cP2.setEndX(cP.getCenterX());
            cP2.setEndY(cP.getCenterY());
            CA.getChildren().add(cP1);
            CA.getChildren().add(cP2);
            pointsPlaced++;
            eP1.toFront();
            eP2.toFront();
            cP.toFront();
            cP1.setStrokeWidth(3);
            cP2.setStrokeWidth(3);
            
            redoPoints();
            M.setText("Connect the opposite squares on each line to make the strings");
            
        }
    }
    public double[] findIntersection(Line a,Line b){
        double intersection[]=new double[2];
        double m1=(a.getStartY()-a.getEndY())/(a.getStartX()-a.getEndX());
        double m2=(b.getStartY()-b.getEndY())/(b.getStartX()-b.getEndX());
        double b1=-m1*a.getStartX()+a.getStartY();
        double b2=-m2*b.getStartX()+b.getStartY();
        intersection[0]=(b2-b1)/(m1-m2);
        intersection[1]=m1*(intersection[0])+b1;
        return intersection;
        
    }
    
    
    public void setParabola(){
        int points=Integer.parseInt(numOfPoints.getText());
        
        for(int i=0;i<points;i++){
            double dx=lines[i].getStartX()-lines[i].getEndX();
            double dy=lines[i].getStartY()-lines[i].getEndY();
            
            try{
                paraLines[i].setStrokeWidth(lineWidth.getValue());
                paraLines[i].setStroke(Color.web(Integer.toHexString((int)lineColor.getValue())));

            }
            catch(Exception e){
                paraLines[i].setStrokeWidth(3);
                paraLines[i].setStroke(javafx.scene.paint.Color.GREEN);
            }
            
            paraLines[i].toFront();
            double intersection1[],intersection2[];
            if(i>0)
                intersection1=findIntersection(lines[i-1],lines[i]);
            else
                intersection1=findIntersection(cP1,lines[i]);
            if(i<points-1){
                intersection2=findIntersection(lines[i],lines[i+1]);
            }
            else
                intersection2=findIntersection(lines[i],cP2);
            paraLines[i].setStartX(intersection1[0]);
            paraLines[i].setStartY(intersection1[1]);
            paraLines[i].setEndX(intersection2[0]);
            paraLines[i].setEndY(intersection2[1]);
            paraLines[i].setVisible(true);
            paraLines[i].toBack();
            lines[i].toBack();
        }
    }
    
    public void removeLines(){
        for(int i=0;i<25;i++){
            paraLines[i].setVisible(false);
        }
    }
    
    public void connect(){
        System.out.println(first.number+" "+second.number);
        if(first.number==-second.number){

            int n=(int)Math.abs(first.number)-1;
            lines[n].setVisible(true);
            connected[n]=true;
            numOfConnected++;
            System.out.println('b');
            int points=Integer.parseInt(numOfPoints.getText());
            
            if(numOfConnected==points){
                setParabola();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to that point, please try again! Try connecting opposite points.");
            alert.showAndWait();
        }
        int points=Integer.parseInt(numOfPoints.getText());
        if(numOfConnected<points){
            removeLines();
        }
        System.out.println(numOfConnected);
    }
    
    public Line currentLine=new Line();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pointSlider.setMax(25);
        pointSlider.setMin(1);
        currentLine.setVisible(false);
        CA.getChildren().add(currentLine);
        connected=new boolean[25];
        paraLines=new Line[25];
        for(int i=0;i<25;i++){
            ce1.add(new Point());
            ce2.add(new Point());
            paraLines[i]=new Line();
            CA.getChildren().add(paraLines[i]);
            paraLines[i].setVisible(false);
            lines[i]=new Line();
            lines[i].setVisible(false);
            connected[i]=false;
            
            CA.getChildren().add(lines[i]);
        }
        
        CA.setOnMouseMoved((t)->{
            if(first!=null&&second==null){
                currentLine.setVisible(true);
                currentLine.setStartX(t.getX());
                currentLine.setStartY(t.getY());
                currentLine.setEndX(first.rect.getX()+6);
                currentLine.setEndY(first.rect.getY()+6);
                
            }
        });
         lineColor.setMin(0);
        lineColor.setMax(16777215.0);
        helpLineColor.setMin(0);
        helpLineColor.setMax(16777215.0);
        lineWidth.setMin(1);
        lineWidth.setMax(15);
        helpLineWidth.setMin(1);
        helpLineWidth.setMax(15);
       
    }    
    
}
class Point{
    double x,y;
    boolean added=false;
    Rectangle rect=new Rectangle(12, 12);
    int number;
    public void set(){
        rect.setX(x-6);
        rect.setY(y-6);
        rect.toFront();
    }
    Point(double a, double b){
        x=a;
        y=b;
    }
    Point(){
    }
}




    

