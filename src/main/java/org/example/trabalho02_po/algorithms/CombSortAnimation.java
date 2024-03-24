package org.example.trabalho02_po.algorithms;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;

public class CombSortAnimation extends SortAnimation {
    TextArea codeArea;

    private final String combSortCode = """
        private void combSort(int[] arr) {
           int n = arr.length;
           int gap = (int) (n / 1.3);
           int index;
               
           while(gap > 0) {
               index = 0;
               while((index + gap) < n) {
                   if(arr[index] > arr[index + gap]) {
                       int temp = arr[index];
                       arr[index] = arr[index + gap];
                       arr[index + gap] = temp;
                   }
                   index++;
               }
               gap = (int) (gap / 1.3);
           }
        }
        """;

    private final TextArea variablesArea;

    public CombSortAnimation(ButtonPane buttonPane, TextArea codeArea, TextArea variablesArea) {
        super(buttonPane);
        this.codeArea = codeArea;
        this.variablesArea = variablesArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        sort(arr);
        playSequentialTransition();
    }

    public void sort(int[] arr) {
        addVariableUpdateToSequence(0, 0);

        int n = arr.length;
        addVariableUpdateToSequence(n, 0);
        sequence.getChildren().add(createHighlightPause(2));

        int gap = (int) (n / 1.3);
        addVariableUpdateToSequence(gap, 0);
        sequence.getChildren().add(createHighlightPause(3));

        int index;
        sequence.getChildren().add(createHighlightPause(4));

        while(gap > 0) {
            sequence.getChildren().add(createHighlightPause(6));
            index = 0;
            addVariableUpdateToSequence(gap, 0);
            sequence.getChildren().add(createHighlightPause(7));
            while((index + gap) < n) {
                addVariableUpdateToSequence(gap, index);
                sequence.getChildren().add(createHighlightPause(8));

                if(arr[index] > arr[index + gap]) {
                    addVariableUpdateToSequence(gap, index);
                    sequence.getChildren().add(createHighlightPause(9));
                    final Button buttonToHighlight = buttonPane.getButtons().get(index+gap);
                    final Button currentButton = buttonPane.getButtons().get(index);
                    PauseTransition highlight = createHighlightTransition(buttonToHighlight, "orange");
                    PauseTransition highlightCurrent = createHighlightTransition(currentButton, "orange");

                    // Configura a duração do destaque antes de remover
                    PauseTransition delay = new PauseTransition(Duration.millis(500));

                    sequence.getChildren().addAll(highlight, highlightCurrent, delay);

                    int temp = arr[index];
                    sequence.getChildren().add(createHighlightPause(10));

                    arr[index] = arr[index + gap];
                    sequence.getChildren().add(createHighlightPause(11));

                    arr[index + gap] = temp;
                    sequence.getChildren().add(createHighlightPause(12));
                    animateIterative(index + gap, index);
                }

                final Button buttonToHighlight = buttonPane.getButtons().get(index+gap);
                final Button currentButton = buttonPane.getButtons().get(index);
                PauseTransition unhighlight = createUnhighlightTransition(buttonToHighlight);
                PauseTransition unhighlightCurrent = createUnhighlightTransition(currentButton);
                PauseTransition delay = new PauseTransition(Duration.millis(500));
                sequence.getChildren().addAll(unhighlight, unhighlightCurrent, delay);

                index++;
                addVariableUpdateToSequence(gap, index);
                sequence.getChildren().add(createHighlightPause(14));
            }

            gap = (int) (gap / 1.3);
            sequence.getChildren().add(createHighlightPause(16));
            addVariableUpdateToSequence(gap, 0);
        }
        sequence.getChildren().add(createHighlightPause(18));
    }

    protected void addVariableUpdateToSequence(int gap, int index) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(e -> updateVariablesDisplay(gap, index));
        sequence.getChildren().add(pause);
    }

    public void updateVariablesDisplay(int gap, int index) {
        String variablesText = String.format("gap: %d\nindex: %d\n(index + gap): %d\n", gap, index, index + gap);
        Platform.runLater(() -> variablesArea.setText(variablesText));
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, combSortCode, codeArea));
        return highlightPause;
    }

}
