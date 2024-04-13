package org.example.trabalho02_po.algorithms;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;


public class ShellSortAnimation extends SortAnimation {
    TextArea codeArea;

    ButtonPane buttonPane;

    TextFlow variablesArea;

    private final String shellSortCode = """
        private void shellSort(int[] arr) {
            int j, aux;
            int gap = arr.length / 2;
            
            while(gap > 0) {
                for (int i = gap; i < arr.length; i++) {
                    aux = arr[i];
                    for(j = i - gap; j >= 0; j = j - gap) {
                        if (aux < arr[j]) {
                            arr[j + gap] = arr[j];
                            arr[j] = aux;
                        }
                    }
                }
                gap = gap / 2;
            }
        }
        """;

    public ShellSortAnimation(ButtonPane buttonPane, TextArea codeArea, TextFlow variablesArea) {
        super(buttonPane);
        this.buttonPane = buttonPane;
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

    private void sort(int[] arr) {
        addVariableUpdateToSequence(0, 0, 0, 0);
        int j, aux;
        sequence.getChildren().add(createHighlightPause(2));

        int gap = arr.length / 2;
        sequence.getChildren().add(createHighlightPause(3));
        addVariableUpdateToSequence(0, 0, arr.length / 2, 0);

        while(gap > 0) {
            sequence.getChildren().add(createHighlightPause(5));
            for (int i = gap; i < arr.length; i++) {
                sequence.getChildren().add(createHighlightPause(6));
                addVariableUpdateToSequence(i, 0, gap, 0);

                aux = arr[i];
                sequence.getChildren().add(createHighlightPause(7));
                addVariableUpdateToSequence(i, 0, gap, aux);

                for(j = i - gap; j >= 0; j = j - gap) {
                    Button buttonCurrent = buttonPane.getButtons().get(buttonOrder[j]);
                    Button buttonAux = buttonPane.getButtons().get(buttonOrder[i]);

                    sequence.getChildren().add(createHighlightPause(8));
                    addVariableUpdateToSequence(i, j, gap, aux);

                    sequence.getChildren().add(createHighlightPause(9));
                    sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonAux, "orange"));
                    if (aux < arr[j]) {
                        sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonAux, "green"));
                        sequence.getChildren().add(createUnhighlightTransition(buttonCurrent, buttonAux));

                        addVariableUpdateToSequence(i, j, gap, aux);
                        final Button buttonJ = buttonPane.getButtons().get(buttonOrder[j + gap]);
                        final Button buttonAux2 = buttonPane.getButtons().get(buttonOrder[j]);

                        sequence.getChildren().add(createHighlightTransition(buttonJ, buttonAux2, "orange"));

                        arr[j + gap] = arr[j];
                        sequence.getChildren().add(createHighlightPause(10));
                        addVariableUpdateToSequence(i, j, gap, aux);

                        arr[j] = aux;
                        sequence.getChildren().add(createHighlightPause(11));
                        addVariableUpdateToSequence(i, j, gap, aux);

                        animateIterative(j + gap, j);
                        sequence.getChildren().add(createUnhighlightTransition(buttonCurrent, buttonAux));
                        sequence.getChildren().add(createUnhighlightTransition(buttonJ, buttonAux2));
                    } else {
                        sequence.getChildren().add(createHighlightTransition(buttonCurrent, buttonAux, "red"));
                    }
                    sequence.getChildren().add(createUnhighlightTransition(buttonCurrent, buttonAux));
                    updateVariablesDisplay(i, j, gap, aux);
                }
                sequence.getChildren().add(createHighlightPause(13));
            }
            sequence.getChildren().add(createHighlightPause(15));
            gap = gap / 2;

        }
        sequence.getChildren().add(createHighlightPause(17));
    }


    protected void addVariableUpdateToSequence(int i, int j, int gap, int aux) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(e -> updateVariablesDisplay(i, j, gap, aux));
        sequence.getChildren().add(pause);
    }

    public void updateVariablesDisplay(int i, int j, int gap, int aux) {
        Text textI = new Text("i: " + i + "\n");
        textI.setFont(Font.font("System", 20));
        Text textJ = new Text("j: " + j + "\n");
        textJ.setFont(Font.font("System", 20));
        Text textGap = new Text("gap: " + gap + "\n");
        textGap.setFont(Font.font("System", 20));
        Text textJGap = new Text("j + gap: " + (j + gap) + "\n");
        textJGap.setFont(Font.font("System", 20));
        Text textAux = new Text("aux: " + aux + "\n");
        textAux.setFont(Font.font("System", FontWeight.BOLD, 20));

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
            variablesArea.getChildren().addAll(
                textI,
                textJ,
                textGap,
                textJGap,
                textAux,
                textObs,
                textRed,
                textFalse,
                textGreen,
                textTrue
            );
        });
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }
}
