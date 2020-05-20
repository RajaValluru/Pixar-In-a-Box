/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixarinabox;

import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hruteesh Raja
 */
public class ParabolicArcController implements Initializable {
    int pointsPlaced=0;
    
    Circle eP1;
    Circle eP2;
    Circle cP;
    public AnchorPane CA;
    Line cP1;
    Line cP2;
    QuadCurve q;
    public CheckBox lineVisibility;
    public Label status;
    public boolean slider=false;
    public Slider lineColor;
    public Slider helpLineColor;
    public Slider lineWidth;
    public Slider helpLineWidth;
    
    public void back(ActionEvent e) throws Exception{
        Parent Square =  FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene SquareScene = new Scene(Square);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void sliderPressed(){
        slider=true;
    }
    public void sliderReleased(){
        slider=false;
    }
    
    public void lineColorSlider(){
        if(slider){

            q.setStroke(Color.web(Integer.toHexString((int)lineColor.getValue())));
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
            q.setStrokeWidth(lineWidth.getValue());
        }
    }
    public void helpLineWidthSlider(){
        if(slider){
            cP1.setStrokeWidth(helpLineWidth.getValue());
            cP2.setStrokeWidth(helpLineWidth.getValue());
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
                    x=x<12?12:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    eP1.setCenterX(x);
                    eP1.setCenterY(y);
                    cP1.setStartX(x);
                    cP1.setStartY(y);
                    if(pointsPlaced>=3){
                        updateQuad();
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
                    x=x<12?12:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    eP2.setCenterX(x);
                    eP2.setCenterY(y);
                    cP2.setStartX(x);
                    cP2.setStartY(y);
                    if(pointsPlaced>=3){
                        updateQuad();
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
                    x=x<12?12:x;
                    y=y<60?60:y;
                    x=x>990?990:x;
                    y=y>863?863:y;
                    cP.setCenterX(x);
                    cP.setCenterY(y);  
                    cP1.setEndX(x);
                    cP1.setEndY(y);
                    cP2.setEndX(x);
                    cP2.setEndY(y);
                    if(pointsPlaced>=3){
                        updateQuad();
                    }
                });
            }
            pointsPlaced++;
        }
        if(pointsPlaced==3){
            status.setText("Manipulate the three points to manipulate parabola");
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
            if(pointsPlaced>=3){
                updateQuad();
                q.toFront();
                eP1.toFront();
                eP2.toFront();
                cP.toFront();
            }
            
        }
    }
    
    public void updateQuad(){
        q.setStartX(eP1.getCenterX());
        q.setStartY(eP1.getCenterY());
        q.setEndX(eP2.getCenterX());
        q.setEndY(eP2.getCenterY());
        q.setControlX(cP.getCenterX());
        q.setControlY(cP.getCenterY());
        q.setVisible(true);
    }
    
    public void setLineVisibility(){
        if(!lineVisibility.isSelected()){
            cP1.setVisible(false);
            cP2.setVisible(false);
            
        }
        else{
            cP1.setVisible(true);
            cP2.setVisible(true);
            
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        q=new QuadCurve();
        q.setVisible(false);
        q.setFill(Color.TRANSPARENT);
        q.setStroke(Color.GREEN);
        q.setStrokeWidth(4);
        CA.getChildren().add(q);
        lineVisibility.setSelected(true);
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

