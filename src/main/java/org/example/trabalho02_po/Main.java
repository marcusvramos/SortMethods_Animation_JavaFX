package org.example.trabalho02_po;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.trabalho02_po.view.MainWindow;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();

        Scene scene = new Scene(mainWindow, 900, 600);
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Ordenação de Botões com Animação");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
