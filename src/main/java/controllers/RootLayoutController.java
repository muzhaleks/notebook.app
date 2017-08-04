package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import run.Main;

import java.io.File;

public class RootLayoutController {

    private Main main;

    public void setMain(Main main){
        this.main = main;
    }
    @FXML
    private void handleNew(){
        main.getPersonData().clear();
        main.setPersonFilePath(null);
    }
    @FXML
    private void handleOpen(){
        FileChooser fileChooser  = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null){
            main.loadPersonDataFromFile(file);
        }
    }
    @FXML
    private void handleSave(){
        File personFile = main.getPersonFilePath();
        if (personFile != null){
            main.savePersonDataToFile(personFile);
        }else {
            handleSaveAs();
        }
    }
    @FXML
    private void handleSaveAs(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml","*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(main.getPrimaryStage());
        if (file != null){
            if (!file.getPath().equals(".xml")){
                file = new File(file.getPath()+ ".xml");
            }
        }
        main.savePersonDataToFile(file);
    }
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NoteBook");
        alert.setHeaderText("About");
        alert.setContentText("Author: Muzhykov Aleksandr.");

        alert.showAndWait();
    }
    @FXML
    private void handleExit(){
        System.exit(0);
    }
    @FXML
    private void handleShowStat(){
        main.showBirthdayStatistics();
    }
}
