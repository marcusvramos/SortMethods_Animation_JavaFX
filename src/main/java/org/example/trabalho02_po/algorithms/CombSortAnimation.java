package org.example.trabalho02_po.algorithms;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

    private final TextFlow variablesArea;

    public CombSortAnimation(ButtonPane buttonPane, TextArea codeArea, TextFlow variablesArea) {
        super(buttonPane);
        this.codeArea = codeArea;
        this.variablesArea = variablesArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        sort(arr);
        initializeButtonOrder();
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
                final Button buttonCurrent = buttonPane.getButtons().get(buttonOrder[index]);
                final Button buttonGap = buttonPane.getButtons().get(buttonOrder[index + gap]);
                sequence.getChildren().add(createHighlightPause(9));

                sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonGap, "orange"));
                if(arr[index] > arr[index + gap]) {
                    sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonGap, "green"));

                    addVariableUpdateToSequence(gap, index);
                    sequence.getChildren().add(createHighlightPause(9));

                    int temp = arr[index];
                    sequence.getChildren().add(createHighlightPause(10));

                    arr[index] = arr[index + gap];
                    sequence.getChildren().add(createHighlightPause(11));

                    arr[index + gap] = temp;
                    sequence.getChildren().add(createHighlightPause(12));
                    animateIterative(index + gap, index);
                } else {
                    sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonGap, "red"));
                }

                sequence.getChildren().add(createUnhighlightTransition(buttonCurrent, buttonGap));

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
        Text textGap = new Text("gap: " + gap + "\n");
        textGap.setFont(Font.font("System", 20));

        Text textIndex = new Text("index: " + index + "\n");
        textIndex.setFont(Font.font("System", 20));

        Text textIndexGap = new Text("(index + gap): " + (index + gap) + "\n");
        textIndexGap.setFont(Font.font("System", 20));

        Text textObs = new Text("\n\nObs: \n");
        textObs.setFont(Font.font("System", 20));

        Text textRed = new Text("Vermelho: ");
        textRed.setFill(Color.RED);
        textRed.setFont(Font.font("System", 20));

        Text textFalse = new Text("Resultado da comparação é falso\n");
        textFalse.setFill(Color.RED);
        textFalse.setFont(Font.font("System", 20));

        Text textGreen = new Text("Verde: ");
        textGreen.setFill(Color.GREEN);
        textGreen.setFont(Font.font("System", 20));

        Text textTrue = new Text("Resultado da comparação é verdadeiro\n");
        textTrue.setFill(Color.GREEN);
        textTrue.setFont(Font.font("System", 20));

        Platform.runLater(() -> {
            variablesArea.getChildren().clear();
            variablesArea.getChildren().addAll(textGap, textIndex, textIndexGap, textObs, textRed, textFalse, textGreen, textTrue);
        });
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, combSortCode, codeArea));
        return highlightPause;
    }

}
