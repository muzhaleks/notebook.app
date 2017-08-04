package run;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Person;
import models.PersonListWrapper;
import controllers.BirthdayStatisticsController;
import controllers.PersonEditController;
import controllers.PersonOverviewController;
import controllers.RootLayoutController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("NoteBook");
        this.primaryStage.getIcons().add(new Image("file:target/classes/view/notebook.png"));

        initRootLayout();
        showPersonOverview();

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayout_1.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getPersonFilePath();
        if (file != null){
            loadPersonDataFromFile(file);
        }
    }

    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PersonOverview_1.fxml"));

            AnchorPane personOverview = loader.load();
            rootLayout.setCenter(personOverview);

            PersonOverviewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PersonEditDialog_1.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать контакт:");
            dialogStage.getIcons().add(new Image("file:target/classes/view/notebook.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            PersonEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public File getPersonFilePath() {
        Preferences pref = Preferences.userNodeForPackage(Main.class);
        String filePath = pref.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    public void setPersonFilePath(File file){
        Preferences pref = Preferences.userNodeForPackage(Main.class);
        if (file != null){
            pref.put("filePath",file.getPath());

            primaryStage.setTitle("NoteBook");
        }else {
            pref.remove("filePath");

            primaryStage.setTitle("NoteBook");
        }
    }

    /**
     * Load info from file, info replaced;
     *
     */
    public void loadPersonDataFromFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            PersonListWrapper wrapper = (PersonListWrapper)um.unmarshal(file);
            personData.clear();
            personData.addAll(wrapper.getPersons());

            setPersonFilePath(file);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Невозможно загрузить данные!");
            alert.setContentText("Невозможно загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Load info into file
     */
    public void savePersonDataToFile(File file){
         try{
             JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
             Marshaller marshaller = context.createMarshaller();
             marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

             PersonListWrapper wrapper = new PersonListWrapper();
             wrapper.setPersons(personData);

             marshaller.marshal(wrapper, file);

             setPersonFilePath(file);
         }catch (Exception e){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ошибка!");
             alert.setHeaderText("Невозможно сохранить данные!");
             alert.setContentText("Невозможно сохранить данные в файл :\n" + file.getPath());

             alert.showAndWait();
         }
    }
    public void showBirthdayStatistics(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/BirthdayStatistics.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Статистика Дней Рождения :");
            dialogStage.getIcons().add(new Image("file:target/classes/view/notebook.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BirthdayStatisticsController bsc = loader.getController();
            bsc.setPersonsData(personData);

            dialogStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
