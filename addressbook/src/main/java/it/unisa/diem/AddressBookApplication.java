package it.unisa.diem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import it.unisa.diem.Utility.FileManager;
import it.unisa.diem.Utility.SceneManager;

/**
 * The main class of the application, which loads the assets and starts the GUI.
 * @see Application
 */
public class AddressBookApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FileManager.createPaths();
        if(!new File(FileManager.profileListPath).exists()){
            try {
                Files.createFile(Paths.get(FileManager.profileListPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{SceneManager.loadProfileSelection(stage);}catch(IOException e){System.exit(1);}
    }

    public static void main(String[] args) {
        launch();
    }

}