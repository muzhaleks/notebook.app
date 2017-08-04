package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Person;
import util.DateUtil;

public class PersonEditController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField cityField;
    @FXML
    TextField addressField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailField;
    @FXML
    TextField birthDayField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    public void initialize(){

    }
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    public void setPerson(Person person){
        this.person = person;

        this.firstNameField.setText(person.getFirstName());
        this.lastNameField.setText(person.getLastName());
        this.cityField.setText(person.getCity());
        this.addressField.setText(person.getAddress());
        this.phoneNumberField.setText(person.getPhoneNumber());
        this.emailField.setText(person.getEmail());
        this.birthDayField.setText(DateUtil.format(person.getBirthDay()));
        birthDayField.setPromptText("dd.mm.yyyy");
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    @FXML
    private void handleOk(){
        if (isInputValid()){
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setCity(cityField.getText());
            person.setAddress(addressField.getText());
            person.setPhoneNumber(phoneNumberField.getText());
            person.setEmail(emailField.getText());
            person.setBirthDay(DateUtil.parse(birthDayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    private boolean isInputValid(){
        String errorMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "Не введено имя!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "Не введена фамилия!\n";
        }
        if (cityField.getText() == null || cityField.getText().length() == 0){
            errorMessage += "Не введён город!\n";
        }
        if (addressField.getText() == null || addressField.getText().length() == 0){
            errorMessage += "Не введён адрес!\n";
        }
        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0){
            errorMessage += "Не введён номер телефона!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "Не введён электоронный адрес!\n";
        }
        if (birthDayField.getText() == null || birthDayField.getText().length() == 0){
            errorMessage += "Не введена дата рождения!\n";
        }else {
            if (!DateUtil.validDate(birthDayField.getText())){
                errorMessage += "Некорректная дата! Используйте формат дд.мм.гггг\n";
            }
        }if (errorMessage.length() == 0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполненные поля!");
            alert.setHeaderText("Пожалуста, заполните поля корректно!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
