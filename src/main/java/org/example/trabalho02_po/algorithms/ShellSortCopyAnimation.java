package org.example.trabalho02_po.algorithms;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ShellSortCopyAnimation extends SortAnimation {
    private TextArea codeArea;
    private TextFlow variablesArea;

    private int[] buttonIndices;


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

    public ShellSortCopyAnimation(ButtonPane buttonPane, TextArea codeArea, TextFlow variablesArea) {
        super(buttonPane);
        this.codeArea = codeArea;
        this.variablesArea = variablesArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        buttonIndices = IntStream.range(0, buttons.size()).toArray();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        shellSort(arr);
        initializeButtonOrder();
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
        System.out.println(Arrays.toString(buttonOrder));

        while (dist > 0) {
            sequence.getChildren().add(createHighlightPause(8));
            for (i = dist; i < arr.length; i++) {
                sequence.getChildren().add(createHighlightPause(9));
                addVariableUpdateToSequence(i, 0, 0, dist);

                aux = arr[i];
                sequence.getChildren().add(createHighlightPause(10));
                addVariableUpdateToSequence(i, 0, aux, dist);
                Button auxButton = buttonPane.getButtons().get(buttonIndices[i]);
                moveButtonAuxUp(auxButton);

                j = i;
                sequence.getChildren().add(createHighlightPause(11));
                addVariableUpdateToSequence(i, j, aux, dist);

                while (j >= dist && arr[j - dist] > aux) {
                    Button higherButton = buttonPane.getButtons().get(buttonIndices[j - dist]);
                    sequence.getChildren().add(createHighlightPause(12));

                    arr[j] = arr[j - dist];
                    buttonIndices[j] = buttonIndices[j - dist];

                    sequence.getChildren().add(createHighlightPause(13));
                    moveButtonToEmptySpace(higherButton,  j - dist, j);

                    j = j - dist;
                    sequence.getChildren().add(createHighlightPause(14));
                    addVariableUpdateToSequence(i, j, aux, dist);
                }
                sequence.getChildren().add(createHighlightPause(15));

                arr[j] = aux;
                buttonIndices[j] = i;

                sequence.getChildren().add(createHighlightPause(16));
                moveAuxButtonToPosition(i, j);
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

    private void updateVariablesDisplay(int i, int j, int aux, int dist) {
        Text textI = new Text("i: " + i + "\n");
        textI.setStyle("-fx-font-size: 20px;");
        Text textJ = new Text("j: " + j + "\n");
        textJ.setStyle("-fx-font-size: 20px;");
        Text textAux = new Text("aux: " + aux + "\n");
        textAux.setFont(Font.font("System", FontWeight.BOLD, 20));
        textAux.setStyle("-fx-font-size: 20px;");
        Text textDist = new Text("dist: " + dist + "\n");
        textDist.setStyle("-fx-font-size: 20px;");
        Text textJDist = new Text("(j-dist): " + (j - dist) + "\n");
        textJDist.setStyle("-fx-font-size: 20px;");

        variablesArea.getChildren().clear();
        variablesArea.getChildren().addAll(textI, textJ, textAux, textDist, textJDist);
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }

    private void moveButtonAuxUp(Button button) {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(300), button);
        moveUp.setByY(-80);
        sequence.getChildren().add(moveUp);
    }

    private void moveButtonToEmptySpace(Button button, int originalIndex, int targetIndex) {
        double spacing = buttonPane.getSpacing();
        double deltaX = calculateDeltaX(originalIndex, targetIndex, spacing);

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), button);
        transition.setByX(deltaX);
        transition.setOnFinished(event -> {
            updateButtonOrderAfterAnimation(originalIndex, targetIndex);
            System.out.println(Arrays.toString(buttonOrder));
        });
        sequence.getChildren().add(transition);
    }

    private double calculateDeltaX(int originalIndex, int targetIndex, double spacing) {
        int moveDistance = Math.abs(targetIndex - originalIndex);
        double buttonWidth = buttonPane.getButtons().get(0).getWidth();
        return (buttonWidth + spacing) * moveDistance * (targetIndex > originalIndex ? 1 : -1);
    }

    private void moveAuxButtonToPosition(int originalIndex, int finalIndex) {
        Button auxButton = buttonPane.getButtons().get(buttonOrder[originalIndex]);
        double spacing = buttonPane.getSpacing();
        double deltaX = calculateDeltaX(originalIndex, finalIndex, spacing);

        TranslateTransition moveHorizontal = new TranslateTransition(Duration.millis(500), auxButton);
        moveHorizontal.setByX(deltaX);

        TranslateTransition moveDown = new TranslateTransition(Duration.millis(300), auxButton);
        moveDown.setByY(80);

        SequentialTransition sequential = new SequentialTransition(moveHorizontal, moveDown);
        sequence.getChildren().add(sequential);
    }

    private Button getButtonByOriginalIndex(int originalIndex) {
        for (Button button : buttonPane.getButtons()) {
            if (button.getUserData().equals(originalIndex)) {
                return button;
            }
        }
        return null;
    }


}