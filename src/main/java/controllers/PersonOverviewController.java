package controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Person;
import run.Main;
import util.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label birthDayLabel;

    private Main main;

    public PersonOverviewController() {

    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    public void setMain(Main main) {
        this.main = main;
        personTable.setItems(main.getPersonData());

    }

    public void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            cityLabel.setText(person.getCity());
            addressLabel.setText(person.getAddress());
            phoneNumberLabel.setText(person.getPhoneNumber());
            emailLabel.setText(person.getEmail());
            birthDayLabel.setText(DateUtil.format(person.getBirthDay()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            cityLabel.setText("");
            addressLabel.setText("");
            phoneNumberLabel.setText("");
            emailLabel.setText("");
            birthDayLabel.setText("");
        }

    }
    @FXML
    private void handleDeletePerson () {
        int selectIndex = personTable.getSelectionModel().getSelectedIndex();

       if(selectIndex >= 0){ personTable.getItems().remove(selectIndex);}
       else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.initOwner(main.getPrimaryStage());
           alert.setTitle("Ничего не выбрано!");
           alert.setHeaderText("Не выбран контакт.");
           alert.setContentText("Пожалуйста, выберите контакт!");

           alert.showAndWait();
       }
    }
    @FXML
    public void handleNewPerson(){
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked){
            main.getPersonData().add(tempPerson);
        }
    }
    @FXML
    public void handleEditPerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null){
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked){
                showPersonDetails(selectedPerson);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ниего не выбрано!");
            alert.setHeaderText("Не выбран контакт!");
            alert.setContentText("Пожалуйста, выберите редактируемый контакт в таблице.");

            alert.showAndWait();

        }
    }
}