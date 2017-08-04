package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import models.Person;


import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BirthdayStatisticsController {
    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));

        xAxis.setCategories(monthNames);

    }

    public void setPersonsData(List<Person> persons){
        int[] monthCounter = new int[12];
        for (Person p: persons) {
            int month = p.getBirthDay().getMonthValue()-1;
            monthCounter[month]++;
        }
        XYChart.Series series = new XYChart.Series<>();
        for (int i = 0; i < monthCounter.length ; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i),monthCounter[i]));
        }
        barChart.getData().add(series);
    }

}
