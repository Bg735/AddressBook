package it.unisa.diem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
* JavaFX App
*/
public class AddressBookApplication extends Application {

   private static Scene scene;

   @Override
   public void start(Stage stage) throws IOException {
       scene = new Scene(loadFXML("ProfileSelection"));
       scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
       stage.setScene(scene);
       stage.initStyle(StageStyle.UNDECORATED);
       stage.setFullScreen(true);
       stage.show();
   }

   public static void setRoot(String fxml) throws IOException {
       scene.setRoot(loadFXML(fxml));
   }

   public static Parent loadFXML(String fxml) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(AddressBookApplication.class.getResource(fxml + "View.fxml"));
       return fxmlLoader.load();
   }

   public static void main(String[] args) {
       launch();
   }

}