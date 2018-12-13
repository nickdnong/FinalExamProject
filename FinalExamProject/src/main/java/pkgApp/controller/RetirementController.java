package pkgApp.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {


    private RetirementApp mainApp = null;

    @FXML
    private TextField txtYearsToWork;

    @FXML
    private TextField txtAnnualReturnWorking;

    @FXML
    private TextField txtYearsRetired;

    @FXML
    private TextField txtAnnualReturnRetired;

    @FXML
    private TextField txtRequiredIncome;

    @FXML
    private TextField txtMonthlySSI;

    @FXML
    private TextField txtTotalAmountSaved;

    @FXML
    private TextField txtAmountToSave;


    enum FieldType {fieldPercentage, fieldDouble, fieldInt}

    private TextField[] allFields;
    private String[] allFieldNames;
    private FieldType[] allFieldtypes;

    public RetirementApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(RetirementApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFields = new TextField[]{txtYearsToWork, txtAnnualReturnWorking, txtYearsRetired, txtAnnualReturnRetired, txtRequiredIncome, txtMonthlySSI};
        allFieldNames = new String[]{"years to work", "Working Annual Return", "Years Retired", "Retired Annual Return", "Required Income", "Monthly SSI"};
        allFieldtypes = new FieldType[]{FieldType.fieldInt, FieldType.fieldPercentage, FieldType.fieldInt, FieldType.fieldPercentage, FieldType.fieldDouble, FieldType.fieldDouble};
        txtAmountToSave.setEditable(false);
        txtTotalAmountSaved.setEditable(false);
//        setupTestData();
    }

    @FXML
    public void btnClear(ActionEvent event) {
        System.out.println("Clear pressed");
        txtAmountToSave.clear();
        txtTotalAmountSaved.clear();

        //	TODO: Clear all the text inputs
        for (TextField textField : allFields) {
            setFieldEmpty(textField);
        }
    }


    @FXML
    public void btnCalculate(ActionEvent event) {
        //	TODO: Call AmountToSave and TotalAmountSaved and populate
        if (isInputValid()) {
            try {
                Retirement retirement = new Retirement(
                        parseInt(txtYearsToWork.getText()),
                        parsePercentage(txtAnnualReturnWorking.getText()),
                        parseInt(txtYearsRetired.getText()),
                        parsePercentage(txtAnnualReturnRetired.getText()),
                        parseDouble(txtRequiredIncome.getText()),
                        parseDouble(txtMonthlySSI.getText()));

                double dTotalAmountSaved = retirement.TotalAmountSaved();
                double dAmountToSave = retirement.AmountToSave();

                txtTotalAmountSaved.setText(formatDouble(dTotalAmountSaved));
                txtAmountToSave.setText(formatDouble(dAmountToSave));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void setupTestData() {
        txtYearsToWork.setText("40");
        txtAnnualReturnWorking.setText("7%");

        txtYearsRetired.setText("20");
        txtAnnualReturnRetired.setText("2%");
        txtRequiredIncome.setText("10000");
        txtMonthlySSI.setText("2642.00");
    }

    private String formatDouble(Double d) {
        return String.format("%1$,.2f", d);
    }

    private void setFieldEmpty(TextField field) {
        field.clear();
    }

    private boolean isInputValid() {
        String errorMsg = "";
        for (int fieldIndex = 0; fieldIndex < allFields.length; fieldIndex++) {
            System.out.println(fieldIndex);
            String emptyError = validateEmpty(fieldIndex);
            if (!emptyError.equals("")) {
                errorMsg += emptyError;
            } else {
                errorMsg += validateParse(fieldIndex);
            }
        }

        if (errorMsg.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false;
        }
    }

    private String validateEmpty(int fieldIndex) {
        TextField field = allFields[fieldIndex];
        if (field.getText() == null || field.getText().length() == 0) {
            return allFieldNames[fieldIndex] + " is empty\n";
        } else {
            return "";
        }
    }

    private String validateParse(int fieldIndex) {
        TextField field = allFields[fieldIndex];
        String content = field.getText();
        String errorMsg = "no valid " + allFieldNames[fieldIndex] + " (must be an ";
        if (allFieldtypes[fieldIndex] == FieldType.fieldDouble) {
            try {
                parseDouble(content);
            } catch (NumberFormatException e) {
                return errorMsg + " double)!\n";
            }
        } else if (allFieldtypes[fieldIndex] == FieldType.fieldInt) {
            try {
                parseInt(content);
            } catch (NumberFormatException e) {
                return errorMsg + " integer)!\n";
            }
        } else if (allFieldtypes[fieldIndex] == FieldType.fieldPercentage) {
            try {
                parsePercentage(content);
            } catch (ParseException e) {
                return errorMsg + " percentage)!\n";
            }
        }
        return "";
    }


    private double parsePercentage(String percentage) throws ParseException {
        return new DecimalFormat("0.0#%").parse(percentage).doubleValue();
    }

    private double parseDouble(String str) {
        return Double.parseDouble(str);
    }

    private int parseInt(String str) {
        return Integer.parseInt(str);
    }

}

