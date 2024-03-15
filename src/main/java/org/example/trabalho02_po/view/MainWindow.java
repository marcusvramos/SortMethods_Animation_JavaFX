package org.example.trabalho02_po.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.trabalho02_po.algorithms.ShellSortAnimation;

public class MainWindow extends BorderPane {
    private ButtonPane buttonPane;

    public MainWindow() {
        buttonPane = new ButtonPane();
        Button startSortButton = new Button("Iniciar Ordenação");
        startSortButton.getStyleClass().add("start-button");
        startSortButton.setOnAction(e -> startSort());

        VBox vbox = new VBox(20);
        vbox.getChildren().add(startSortButton);
        vbox.getChildren().add(buttonPane);
        vbox.setAlignment(Pos.TOP_LEFT);

        this.setLeft(vbox); // Posiciona o VBox no lado esquerdo do BorderPane
    }

    private void startSort() {
        ShellSortAnimation shellSortAnimation = new ShellSortAnimation(buttonPane);
        shellSortAnimation.startSort();
    }
}
