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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hruteesh Raja
 */
public class SubdivisionController implements Initializable {
    public AnchorPane CA;
    public ArrayList<PairingHeap> points=new ArrayList<>();
    public int subdivisions=0;
    public Label num;
    public Label status;
    public boolean slider=false;
    public Slider lineColor;
    public Slider helpLineColor;
    public Slider lineWidth;
    public Slider helpLineWidth;
    public CheckBox helpLineVisible;
    public void visibleHelpLines(){
        boolean set=false;
        if(helpLineVisible.isSelected()){
            set=true;
            
        }
        for(int i=0;i<points.size();i++){
            points.get(i).base.setVisible(set);
        }
    }
    public void sliderPressed(){
        slider=true;
    }
    public void sliderReleased(){
        slider=false;
    }
    
    public void lineColorSlider(){
        if(slider){
            for(int i=0;i<points.size();i++){
                points.get(i).subLines.setStroke(Color.web(Integer.toHexString((int)lineColor.getValue())));

            }
        }
    }
    public void helpLineColorSlider(){
        if(slider){
            for(int i=0;i<points.size();i++){
                points.get(i).base.setStroke(Color.web(Integer.toHexString((int)helpLineColor.getValue())));

            }
        }
    }
    public void lineWidthSlider(){
        if(slider){
            for(int i=0;i<points.size();i++)
                points.get(i).subLines.setStrokeWidth(lineWidth.getValue());

        }
    }
    public void helpLineWidthSlider(){
        if(slider){
            for(int i=0;i<points.size();i++)
                points.get(i).base.setStrokeWidth(helpLineWidth.getValue());
            
        }
    }
    public void back(ActionEvent e) throws Exception{
        Parent Square =  FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene SquareScene = new Scene(Square);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void increase(){
        subdivisions++;
        num.setText(Integer.toString(subdivisions));
        redoSubdivisions();
        redoLines();
        
    }
    public void decrease(){
        subdivisions-=subdivisions==0?0:1;
        
        num.setText(Integer.toString(subdivisions));
        redoSubdivisions();
        redoLines();
    }
    
    public Double[] getNext(int i,int j){
        Double p[]=new Double[2];
        if(j==points.get(i).subPoints.size()-1){
            p[0]=points.get((i+1)%points.size()).subPoints.get(0).x;
            p[1]=points.get((i+1)%points.size()).subPoints.get(0).y;
            
        }
        else{
            p[0]=points.get((i)).subPoints.get(j+1).x;
            p[1]=points.get((i)).subPoints.get(j+1).y;
            
        }
        return p;
    }
    public void redoLines(){
        
        for(int i=0;i<points.size();i++){
            points.get(i).subLines.setVisible(true);
            points.get(i).subLines.getPoints().clear();
            points.get(i).subLines.setStrokeWidth(lineWidth.getValue());
            try{
                points.get(i).subLines.setStroke(Color.web(Integer.toHexString((int)lineColor.getValue())));
            }
            catch(Exception e){
                points.get(i).subLines.setStroke(Color.RED);
                
            }
            
            for(int j=0;j<points.get(i).subPoints.size();j++){
                Double first[]=new Double[2];
                Double second[]=getNext(i,j);
                first[0]=points.get(i).subPoints.get(j).x;
                first[1]=points.get(i).subPoints.get(j).y;
                points.get(i).subLines.getPoints().addAll(first);
                points.get(i).subLines.getPoints().addAll(second);
            }
        }
        for(int i=0;i<points.size();i++){
            points.get(i).base.setStartX(points.get(i).control.x);
            points.get(i).base.setStartY(points.get(i).control.y);
            points.get(i).base.setEndX(points.get((i+1)%points.size()).control.x);
            points.get(i).base.setEndY(points.get((i+1)%points.size()).control.y);
            points.get(i).base.setStrokeWidth(helpLineWidth.getValue());
            try{
                points.get(i).base.setStroke(Color.web(Integer.toHexString((int)helpLineColor.getValue())));
            }
            catch(Exception e){
                points.get(i).base.setStroke(Color.BLACK);
            }

            

            
            final int I=i;
            points.get(i).base.setOnMouseClicked((t)->{
                points.add(I+1,new PairingHeap(t.getX(),t.getY()));
                CA.getChildren().add(points.get(I+1).vertex);
                
                redoVerticesSet();
                redoSubdivisions();
                redoLines();
            });
            
        }
    }
    public void split(){

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.get(i).subPoints.size();j+=2){
                Double first[]=new Double[2];
                Double second[];
                first[0]=points.get(i).subPoints.get(j).x;
                first[1]=points.get(i).subPoints.get(j).y;
                second=getNext(i,j);
                Double x,y;
                x=first[0]/2+second[0]/2;
                y=first[1]/2+second[1]/2;
                points.get(i).subPoints.add(j+1,new PointSub(x,y));
 
            }
        }
    }
    public void average(){

        double prevFirst[]=new double[2];
        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.get(i).subPoints.size();j++){
                
                Double first[]=new Double[2];
                Double second[];
                first[0]=points.get(i).subPoints.get(j).x;
                first[1]=points.get(i).subPoints.get(j).y;
                if(i==0&&j==0){
                    prevFirst[0]=first[0];
                    prevFirst[1]=first[1];
                }
                
                second=getNext(i,j);
                if(i==points.size()-1&& j==points.get(i).subPoints.size()-1){
                    second[0]=prevFirst[0];
                    second[1]=prevFirst[1];
                }
                Double x,y;
                x=first[0]/2+second[0]/2;
                y=first[1]/2+second[1]/2;
                points.get(i).subPoints.get(j).x=x;
                points.get(i).subPoints.get(j).y=y;
                points.get(i).subPoints.get(j).setPoints();
              
                
            }
        }
    }
    public void clean(){
        
        for(int i=0;i<points.size();i++){
            CA.getChildren().removeAll(points.get(i).base);
            CA.getChildren().add(points.get(i).base);
            
            points.get(i).base.toFront();
            
            
        }
        
        for(int i=0;i<points.size();i++){
            CA.getChildren().removeAll(points.get(i).subLines);
            
            CA.getChildren().add(points.get(i).subLines);
            points.get(i).subLines.toFront();
        }
        for(int i=0;i<points.size();i++){
            points.get(i).vertex.toFront();
            
            
        }
        
    }
    public void redoSubdivisions(){
        for(int i=0;i<points.size();i++){
            points.get(i).subPoints=new ArrayList();
            points.get(i).subPoints.add(new PointSub(points.get(i).control.x,points.get(i).control.y));
        }
        for(int i=0;i<subdivisions;i++){
            split();
            average();
        }
        clean();
        
    }
    
    public void redoVerticesSet(){
        for(int i=0;i<points.size();i++){
            final int I=i;
            
            points.get(i).vertex.setOnMouseDragged((t)->{ 
                double x=t.getX();
                double y=t.getY();
                x=x<12?12:x;
                y=y<60?60:y;
                x=x>990?990:x;
                y=y>863?863:y;
                points.get(I).vertex.setCenterX(x);
                points.get(I).vertex.setCenterY(y);
                points.get(I).control.x=x;
                points.get(I).control.y=y;
                
                redoSubdivisions();
                redoLines();
                
                
            });
        }
    }
    public void redoBase(){
        for(int i=0;i<points.size();i++){
            final int I=i;
            points.get(i).base.setOnMouseClicked((t)->{
                points.add(I+1,new PairingHeap(t.getX(),t.getY()));
                CA.getChildren().add(points.get(I+1).vertex);
                
                redoVerticesSet();
                redoSubdivisions();
                redoLines();
            });
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helpLineVisible.setSelected(true);
        lineColor.setMin(0);
        lineColor.setMax(16777215.0);
        helpLineColor.setMin(0);
        helpLineColor.setMax(16777215.0);
        lineWidth.setMin(1);
        lineWidth.setMax(15);
        helpLineWidth.setMin(1);
        helpLineWidth.setMax(15);
        points.add(new PairingHeap(500,150));
        CA.getChildren().add(points.get(0).vertex);
        points.add(new PairingHeap(250,450));
        CA.getChildren().add(points.get(1).vertex);
        points.add(new PairingHeap(750,450));
        CA.getChildren().add(points.get(2).vertex);
        for(int i=0;i<3;i++){
          //  points.get(i).next=points.get((i+1)%3);
            redoSubdivisions();
            redoLines();
            points.get(i).subLines.setVisible(false);
            redoVerticesSet();
            final int I=i;
            points.get(i).base.setOnMouseClicked((t)->{
                points.add(I+1,new PairingHeap(t.getX(),t.getY()));
                CA.getChildren().add(points.get(I+1).vertex);
                
                redoVerticesSet();
                redoSubdivisions();
                redoLines();
            });
            redoBase();
        }
        redoSubdivisions();
            redoLines();
        
    
    
    
    
    }    
    
}
class PairingHeap{
    public PointSub control;
    Circle vertex=new Circle();
    Line base=new Line();
    Polyline subLines=new Polyline();
    ArrayList<PointSub> subPoints;
    PairingHeap(double x1,double y1){
        control=new PointSub(x1,y1);
        subPoints=new ArrayList();
        subPoints.add(new PointSub(x1,y1));
        subLines.setVisible(true);
        vertex.setVisible(true);
        vertex.setRadius(15);
        base.setVisible(true);
        setPoints();
        base.setStrokeWidth(8);
        subLines.setStrokeWidth(4);
       // subLines.setFill(Color.RED);
        
    }
    void setPoints(){
        vertex.setCenterX(control.x);
        vertex.setCenterY(control.y);
    }
}
class PointSub{
   
    double x,y;
    Circle subVertex=new Circle();
    PointSub(double xx,double yy){
        x=xx;
        y=yy;
        subVertex.setVisible(true);
        subVertex.setRadius(10);
        setPoints();
        
    }
    void setPoints(){
        subVertex.setCenterX(x);
        subVertex.setCenterY(y);
    }
}
