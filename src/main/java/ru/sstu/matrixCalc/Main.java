package ru.sstu.matrixCalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sstu.matrixCalc.gui.MainController;

public class Main extends Application {

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
            primaryStage.setTitle("Матричный калькулятор");
            primaryStage.setScene(new Scene(root, 811, 380));
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/img/main.png"));
            primaryStage.show();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MainController.showError(e.getMessage() + " \nSee logs: 'logs.log'");
        }
    }

    public static void main(String[] args) {
        log.info("Launching an application...");
        launch(args);
    }
}
