package org.example.trabalho02_po.algorithms;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.ArrayList;
import java.util.List;

public abstract class SortAnimation {
    protected List<Integer[]> transitions = new ArrayList<>();
    protected SequentialTransition sequence = new SequentialTransition();
    protected int[] buttonOrder;
    protected ButtonPane buttonPane;

    public SortAnimation(ButtonPane buttonPane) {
        this.buttonPane = buttonPane;
        initializeButtonOrder(); // Garantindo a inicialização imediata
    }

    protected final void addTransition(int fromIndex, int toIndex) {
        transitions.add(new Integer[]{toIndex, fromIndex});
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

    protected void animateIterative(boolean isVariance, int transitionIdx) {
        Integer[] currentTransition = transitions.get(transitionIdx);
        int fromIndex = currentTransition[0];
        int toIndex = currentTransition[1];

        Button fromButton = buttonPane.getButtons().get(buttonOrder[fromIndex]);
        Button toButton = buttonPane.getButtons().get(buttonOrder[toIndex]);

        int variance = isVariance ? Math.abs(fromIndex - toIndex) : 1;

        applyTranslateTransition(variance, fromButton, 1);
        applyTranslateTransition(variance, toButton, -1);

        swap(buttonOrder, fromIndex, toIndex);
    }

    private void applyTranslateTransition(int variance, Button button, int direction) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), button);
        tt.setByX(80 * (variance) * direction);
        sequence.getChildren().add(tt);
    }

    public abstract void playAnimation();

    public abstract void startSort();
}
