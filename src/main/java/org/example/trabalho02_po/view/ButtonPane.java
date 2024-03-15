package org.example.trabalho02_po.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class ButtonPane extends HBox {
    private final List<Button> buttons = new ArrayList<>();

    public ButtonPane() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        generateButtons();
    }

    private void generateButtons() {
        int NUM_OF_BUTTONS = 10;
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            Button button = new Button(String.valueOf((int) (Math.random() * 90) + 10));
            button.setMinWidth(50);
            button.setMinHeight(50);
            buttons.add(button);
            this.getChildren().add(button);
        }
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
