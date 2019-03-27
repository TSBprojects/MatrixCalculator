package ru.sstu.matrixCalc.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sstu.matrixCalc.core.Matrix;
import ru.sstu.matrixCalc.utils.MatrixConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Logger log = LogManager.getLogger(MainController.class.getName());


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea matrixA;

    @FXML
    private TextArea matrixB;

    @FXML
    private TextArea resultWindow;

    @FXML
    private Button multiplyABy;

    @FXML
    private TextField multAByValue;

    @FXML
    private Button multiplyBBy;

    @FXML
    private TextField multBByValue;

    @FXML
    private Button switchMatrixAB;

    @FXML
    private Button multMatrix;

    @FXML
    private Button addMatrix;

    @FXML
    private Button subMatrix;

    @FXML
    private Button powerA;

    @FXML
    private TextField powerAByValue;

    @FXML
    private Button powerB;

    @FXML
    private TextField powerBByValue;

    @FXML
    private Button transposeA;

    @FXML
    private Button transposeB;

    @FXML
    private Button switchMatrixBC;

    @FXML
    private Button helpBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        initHelpBtn();

        matrixA.textProperty().addListener(getFieldChecker(matrixA));
        matrixB.textProperty().addListener(getFieldChecker(matrixB));
        resultWindow.textProperty().addListener(getFieldChecker(resultWindow));
        multAByValue.textProperty().addListener(getFieldChecker(multAByValue));
        multBByValue.textProperty().addListener(getFieldChecker(multBByValue));
        powerAByValue.textProperty().addListener(getFieldChecker(powerAByValue));
        powerBByValue.textProperty().addListener(getFieldChecker(powerBByValue));


        switchMatrixAB.setOnAction(event -> {
            tryIt(() -> {
                switchMatrix(matrixA, matrixB);
            });
        });

        switchMatrixBC.setOnAction(event -> {
            tryIt(() -> {
                switchMatrix(matrixB, resultWindow);
            });
        });

        multMatrix.setOnAction(event -> {
            tryIt(() -> {
                log.info("Multiply matrix...");
                String result = Matrix.multiply(
                        new Matrix(MatrixConverter.convert(matrixA.getText())),
                        new Matrix(MatrixConverter.convert(matrixB.getText()))
                ).toString();
                resultWindow.setText(result);
            });
        });

        addMatrix.setOnAction(event -> {
            log.info("Add matrix...");
            tryIt(() -> {
                String result = Matrix.add(
                        new Matrix(MatrixConverter.convert(matrixA.getText())),
                        new Matrix(MatrixConverter.convert(matrixB.getText()))
                ).toString();
                resultWindow.setText(result);
            });
        });

        subMatrix.setOnAction(event -> {
            log.info("Subtract matrix...");
            tryIt(() -> {
                String result = Matrix.subtract(
                        new Matrix(MatrixConverter.convert(matrixA.getText())),
                        new Matrix(MatrixConverter.convert(matrixB.getText()))
                ).toString();
                resultWindow.setText(result);
            });
        });

        multiplyABy.setOnAction(event -> {
            tryIt(() -> {
                multiplyMatrixBy(matrixA, multAByValue);
            });
        });

        powerA.setOnAction(event -> {
            tryIt(() -> {
                powerMatrix(matrixA, powerAByValue);
            });
        });

        multiplyBBy.setOnAction(event -> {
            tryIt(() -> {
                multiplyMatrixBy(matrixB, multBByValue);
            });
        });

        powerB.setOnAction(event -> {
            tryIt(() -> {
                powerMatrix(matrixB, powerBByValue);
            });
        });

        transposeA.setOnAction(event -> {
            tryIt(() -> {
                transposeMatrix(matrixA);
            });
        });

        transposeB.setOnAction(event -> {
            tryIt(() -> {
                transposeMatrix(matrixB);
            });
        });
    }


    public static void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(message);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/img/main.png")); // To add an icon
            alert.showAndWait();
        });
    }

    public static void showMessage(String message, String title) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


    private void switchMatrix(TextArea mA, TextArea mB) {
        log.info("Switching matrix...");
        String tmp = mB.getText();
        mB.setText(mA.getText());
        mA.setText(tmp);
    }

    private void transposeMatrix(TextArea m) {
        log.info("Transpose matrix...");
        String result = Matrix.transpose(
                new Matrix(MatrixConverter.convert(m.getText()))
        ).toString();
        m.setText(result);
    }

    private void powerMatrix(TextArea m, TextField num) {
        log.info("Pow matrix by number...");
        String result = Matrix.pow(
                new Matrix(MatrixConverter.convert(m.getText())),
                Integer.parseInt(num.getText())
        ).toString();
        m.setText(result);
    }

    private void multiplyMatrixBy(TextArea m, TextField num) {
        log.info("Multiply matrix by number...");
        String result = Matrix.multiply(
                new Matrix(MatrixConverter.convert(m.getText())),
                Double.parseDouble(num.getText())
        ).toString();
        m.setText(result);
    }


    private void initHelpBtn(){
        ImageView imageView = new ImageView(new Image("/img/sign-question.png"));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        helpBtn.setGraphic(imageView);
        helpBtn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/help.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Руководства");
            stage.setScene(new Scene(root, 550, 340));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/text.png")));
            stage.showAndWait();
        });
    }

    private ChangeListener<String> getFieldChecker(TextArea textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d \n\\.\\,-]", ""));
            }
        };
    }

    private ChangeListener<String> getFieldChecker(TextField textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d\\.\\,-]", ""));
            }
        };
    }


    private void tryIt(Callback callback) {
        try {
            callback.executableCode();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            showError(e.getMessage() + " \nSee logs: 'logs.log'");
        }
    }

    private interface Callback {
        void executableCode() throws Exception;
    }

}
