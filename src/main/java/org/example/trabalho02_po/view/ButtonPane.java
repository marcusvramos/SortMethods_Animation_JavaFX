package org.example.trabalho02_po.view;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ButtonPane extends HBox {
    private final List<Button> buttons = new ArrayList<>();
    private final List<String> originalButtonValues = new ArrayList<>();

    public ButtonPane() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        generateButtons();
    }

    private void generateButtons() {
        int NUM_OF_BUTTONS = 10;
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            int value = (int) (Math.random() * 90) + 10;
            Button button = new Button(String.valueOf(value));
            button.setMinWidth(50);
            button.setMinHeight(50);
            buttons.add(button);
            originalButtonValues.add(String.valueOf(value));
            this.getChildren().add(button);

            Text positionText = new Text(String.valueOf(i));
            VBox buttonWithPosition = new VBox(button, positionText);
            buttonWithPosition.setAlignment(Pos.CENTER);
            buttonWithPosition.setSpacing(5);
            this.getChildren().add(buttonWithPosition);
        }
    }

    public void resetToOriginalState() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(originalButtonValues.get(i));
        }
        this.requestLayout(); // Solicita que o layout seja recalculado
    }

    public void highlightComparingButtons(int i, int j) {
        buttons.get(i).setStyle("-fx-background-color: #ff0000");
        buttons.get(j).setStyle("-fx-background-color: #ff0000");
    }

    public List<Button> getButtons() {
        return buttons;
    }
}