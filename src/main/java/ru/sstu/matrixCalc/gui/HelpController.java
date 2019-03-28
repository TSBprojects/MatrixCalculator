package ru.sstu.matrixCalc.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    private final Logger log = LogManager.getLogger(HelpController.class.getName());

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPain;

    @FXML
    private TextArea userGuidTextArea;

    @FXML
    private TextArea adminGuidTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tryIt(() -> {

            userGuidTextArea.setText(
                    new String(
                            Files.readAllBytes(Paths.get("guids/userGuid.txt")),
                            "UTF-8"
                    )
            );

            adminGuidTextArea.setText(
                    new String(
                            Files.readAllBytes(Paths.get("guids/adminGuid.txt")),
                            "UTF-8"
                    )
            );

        });
    }


    private void tryIt(Callback callback) {
        try {
            callback.executableCode();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MainController.showError(e.getMessage() + " \nSee logs: 'logs.log'");
        }
    }

    private interface Callback {
        void executableCode() throws Exception;
    }

}
