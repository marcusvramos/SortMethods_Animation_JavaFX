package org.example.trabalho02_po.algorithms;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

public abstract class SortAnimation {
    protected SequentialTransition sequence = new SequentialTransition();
    protected int[] buttonOrder;
    protected ButtonPane buttonPane;

    public SortAnimation(ButtonPane buttonPane) {
        this.buttonPane = buttonPane;
        initializeButtonOrder();
    }

    protected final void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    protected void playSequentialTransition() {
        sequence.play();
    }

    protected void initializeButtonOrder() {
        int size = buttonPane.getButtons().size();
        buttonOrder = new int[size];
        for (int i = 0; i < size; i++) {
            buttonOrder[i] = i;
        }
    }

    protected void animateIterative(int fromIndex, int toIndex) {
        Button fromButton = buttonPane.getButtons().get(buttonOrder[fromIndex]);
        Button toButton = buttonPane.getButtons().get(buttonOrder[toIndex]);

        double spacing = 30;
        double buttonWidth = fromButton.getWidth();
        double deltaX = (toIndex - fromIndex) * (buttonWidth + spacing);

        TranslateTransition ttFrom = new TranslateTransition(Duration.millis(1000), fromButton);
        ttFrom.setByX(deltaX);
        ttFrom.setOnFinished(e -> fromButton.setLayoutX(fromButton.getLayoutX() + deltaX));

        TranslateTransition ttTo = new TranslateTransition(Duration.millis(1000), toButton);
        ttTo.setByX(-deltaX);
        ttTo.setOnFinished(e -> toButton.setLayoutX(toButton.getLayoutX() - deltaX));

        sequence.getChildren().addAll(ttFrom, ttTo);

        swap(buttonOrder, fromIndex, toIndex);
    }

    protected void highlightLine(int lineNumber, String code, TextArea codeArea) {
        String[] lines = code.split("\n");

        StringBuilder highlightedText = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i == lineNumber - 1) {
                highlightedText.append(">> ").append(lines[i]).append("\n");
            } else {
                highlightedText.append(lines[i]).append("\n");
            }
        }

        javafx.application.Platform.runLater(() -> {
            codeArea.setText(highlightedText.toString());
            codeArea.selectRange(highlightedText.indexOf(">> "), highlightedText.indexOf("\n", highlightedText.indexOf(">>"))); // Opcional: destaca o texto no TextArea
        });
    }

    protected PauseTransition createSimpleHighlightTransition(Button button, String color) {
        PauseTransition highlight = new PauseTransition(Duration.millis(1));
        highlight.setOnFinished(e -> button.setStyle("-fx-background-color: " + color));
        return highlight;
    }

    protected PauseTransition createHighlightTransition(Button button1, Button button2, String color) {
        PauseTransition highlight = new PauseTransition(Duration.millis(1500));
        highlight.setOnFinished(e -> {
            button1.setStyle("-fx-background-color: " + color + ";");
            button2.setStyle("-fx-background-color: " + color + ";");
        });
        return highlight;
    }

    protected PauseTransition createUnhighlightTransition(Button button1, Button button2) {
        PauseTransition unhighlight = new PauseTransition(Duration.millis(500));
        unhighlight.setOnFinished(e -> {
            button1.setStyle("");
            button2.setStyle("");
        });
        return unhighlight;
    }

    public abstract void startSort();
}
