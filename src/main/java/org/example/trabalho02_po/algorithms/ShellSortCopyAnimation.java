package org.example.trabalho02_po.algorithms;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;

public class ShellSortCopyAnimation extends SortAnimation {
    private TextArea codeArea;
    private TextArea variablesArea;

    private final String shellSortCode = """
                private void shellSort(int[] arr) {
                    int i, j, aux, dist = 1;
                    while (dist < arr.length) {
                        dist = 3 * dist + 1;
                    }
                    dist = dist / 3;
            
                    while (dist > 0) {
                        for (i = dist; i < arr.length; i++) {
                            aux = arr[i];
                            j = i;
                            while (j >= dist && arr[j - dist] > aux) {
                                arr[j] = arr[j - dist);
                                j = j - dist;
                            }
                            arr[j] = aux;
                        }
                        dist = dist / 3;
                    }
                }
            """;

    public ShellSortCopyAnimation(ButtonPane buttonPane, TextArea codeArea, TextArea variablesArea) {
        super(buttonPane);
        this.codeArea = codeArea;
        this.variablesArea = variablesArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        shellSort(arr);
        playSequentialTransition();
    }

    private void shellSort(int[] arr) {
        addVariableUpdateToSequence(0, 0, 0, 1);
        int i, j, aux, dist = 1;
        sequence.getChildren().add(createHighlightPause(2));

        while (dist < arr.length) {
            sequence.getChildren().add(createHighlightPause(3));
            addVariableUpdateToSequence(0, 0, 0, dist);

            dist = 3 * dist + 1;
            sequence.getChildren().add(createHighlightPause(4));
            addVariableUpdateToSequence(0, 0, 0, dist);
        }
        sequence.getChildren().add(createHighlightPause(5));

        dist = dist / 3;
        sequence.getChildren().add(createHighlightPause(6));
        addVariableUpdateToSequence(0, 0, 0, dist);

        while (dist > 0) {
            sequence.getChildren().add(createHighlightPause(8));
            for (i = dist; i < arr.length; i++) {
                sequence.getChildren().add(createHighlightPause(9));
                addVariableUpdateToSequence(i, 0, 0, dist);

                aux = arr[i];
                sequence.getChildren().add(createHighlightPause(10));
                addVariableUpdateToSequence(i, 0, aux, dist);
                moveButtonUp(buttonPane.getButtons().get(i));

                j = i;
                sequence.getChildren().add(createHighlightPause(11));
                addVariableUpdateToSequence(i, j, aux, dist);

                while (j >= dist && arr[j - dist] > aux) {
                    sequence.getChildren().add(createHighlightPause(12));

                    arr[j] = arr[j - dist];
                    sequence.getChildren().add(createHighlightPause(13));

                    final int fromIndex = j - dist;
                    final int toIndex = j;

                    copyButtonValue(buttonPane.getButtons().get(fromIndex), buttonPane.getButtons().get(toIndex));

                    j = j - dist;
                    sequence.getChildren().add(createHighlightPause(14));
                    addVariableUpdateToSequence(i, j, aux, dist);
                }
                sequence.getChildren().add(createHighlightPause(15));

                arr[j] = aux;
                sequence.getChildren().add(createHighlightPause(16));

                putAuxValue(buttonPane.getButtons().get(j), aux);

                moveButtonDown(buttonPane.getButtons().get(i));
            }
            sequence.getChildren().add(createHighlightPause(18));

            dist = dist / 3;
            sequence.getChildren().add(createHighlightPause(19));
            addVariableUpdateToSequence(0, 0, 0, dist);
        }
        sequence.getChildren().add(createHighlightPause(20));
    }

    protected void addVariableUpdateToSequence(int i, int j, int aux, int dist) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(e -> updateVariablesDisplay(i, j, aux, dist));
        sequence.getChildren().add(pause);
    }

    public void updateVariablesDisplay(int i, int j, int aux, int dist) {
        String variablesText = String.format("i: %d\nj: %d\naux: %d\ndist: %d\n", i, j, aux, dist);
        variablesArea.setText(variablesText);
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }

    private void moveButtonDown(Button button) {
        TranslateTransition moveDown = new TranslateTransition(Duration.millis(300), button);
        moveDown.setByY(10); // Ajuste conforme necessário.
        sequence.getChildren().add(moveDown); // Adiciona à sequência em vez de executar imediatamente.
    }

    private void moveButtonUp(Button button) {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(300), button);
        moveUp.setByY(-10); // Ajuste conforme necessário.
        sequence.getChildren().add(moveUp); // Adiciona à sequência em vez de executar imediatamente.
    }

    private void copyButtonValue(Button fromButton, Button toButton) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), toButton);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), toButton);
        fadeIn.setToValue(1.0);

        // Configura o que acontece quando o fade out termina
        fadeOut.setOnFinished(e -> toButton.setText(fromButton.getText()));

        // Cria uma sequência para fade out, atualização do texto e fade in.
        SequentialTransition fadeSequence = new SequentialTransition(fadeOut, fadeIn);

        // Adiciona a sequência de fade à sequência geral
        sequence.getChildren().add(fadeSequence);
    }

    private void putAuxValue(Button button, int auxValue) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), button);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), button);
        fadeIn.setToValue(1.0);

        // Configura o que acontece quando o fade out termina
        fadeOut.setOnFinished(e -> button.setText(String.valueOf(auxValue)));

        // Cria uma sequência para fade out, atualização do texto e fade in.
        SequentialTransition fadeSequence = new SequentialTransition(fadeOut, fadeIn);

        // Adiciona a sequência de fade à sequência geral
        sequence.getChildren().add(fadeSequence);
    }

}
