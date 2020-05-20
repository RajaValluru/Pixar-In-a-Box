/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixarinabox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Hruteesh Raja
 */
public class StartController implements Initializable {
    
    public Button stringArtBtn;
    public Button subdivisionBtn;
    public Button parabolicArcBtn;
    
    public Label title;
    
    public void stringArtClicked(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("StringArt.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void subdivisionClicked(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("Subdivision.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void parabolicArcClicked(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
    }    
    
}
