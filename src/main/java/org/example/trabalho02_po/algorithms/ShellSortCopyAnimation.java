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

import java.util.List;

public class ShellSortCopyAnimation extends SortAnimation {
    private TextArea codeArea;
    private TextFlow variablesArea;

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
                Button ButtonAux = buttonPane.getButtons().get(i);
                moveButtonUp(ButtonAux);

                j = i;
                sequence.getChildren().add(createHighlightPause(11));
                addVariableUpdateToSequence(i, j, aux, dist);

                while (j >= dist && arr[j - dist] > aux) {
                    Button compareButton01 = buttonPane.getButtons().get(j - dist);
                    Button compareButton02 = buttonPane.getButtons().get(j);

                    PauseTransition highlight01 = createHighlightTransition(compareButton01, "green");
                    PauseTransition highlight02 = createHighlightTransition(compareButton02, "green");
                    sequence.getChildren().addAll(highlight01, highlight02);

                    sequence.getChildren().add(createHighlightPause(12));
                    arr[j] = arr[j - dist];
                    addDescriptionSequence(j, arr[j-dist], false);

                    sequence.getChildren().add(createHighlightPause(13));
                    copyButtonValue(buttonPane.getButtons().get(j - dist), buttonPane.getButtons().get(j));

                    PauseTransition unhighlight01 = createUnhighlightTransition(compareButton01);
                    PauseTransition unhighlight02 = createUnhighlightTransition(compareButton02);
                    sequence.getChildren().addAll(unhighlight01, unhighlight02);

                    j = j - dist;
                    sequence.getChildren().add(createHighlightPause(14));
                    addVariableUpdateToSequence(i, j, aux, dist);
                }
                sequence.getChildren().add(createHighlightPause(15));

                arr[j] = aux;
                moveButtonDown(buttonPane.getButtons().get(i));
                sequence.getChildren().add(createHighlightPause(16));

                Button buttonToReceive = buttonPane.getButtons().get(j);
                PauseTransition highlight = createHighlightTransition(buttonToReceive, "gray");
                sequence.getChildren().addAll(highlight);

                addDescriptionSequence(j, aux, true);
                putAuxValue(buttonPane.getButtons().get(j), aux);

                PauseTransition delay = new PauseTransition(Duration.millis(3000));

                PauseTransition unhighlight = createUnhighlightTransition(buttonToReceive);
                sequence.getChildren().addAll(unhighlight, delay);
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

    private void addDescriptionSequence(int j, int aux, boolean isAux) {
        PauseTransition pause = new PauseTransition(Duration.millis(3000));
        pause.setOnFinished(e -> updateDescription(j, aux, isAux));
        sequence.getChildren().add(pause);
    }

    private void updateDescription(int j, int aux, boolean isAux) {
        Text text = new Text("Descrição:\n\n");
        text.setStyle("-fx-font-size: 20px;");
        Text description = new Text();
        if (isAux) {
            description.setText("Botão na posição j (" + j + ") recebe o valor de aux (" + aux + ").\n");
        } else {
            description.setText("Botão na posição j (" + j + ") recebe o valor de arr[j-dist] (" + aux + ").\n");
        }
        description.setStyle("-fx-font-size: 20px;");
        variablesArea.getChildren().addAll(text, description);
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }

    private void moveButtonDown(Button button) {
        TranslateTransition moveDown = new TranslateTransition(Duration.millis(300), button);
        moveDown.setByY(60);
        sequence.getChildren().add(moveDown);
    }

    private void moveButtonUp(Button button) {
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(300), button);
        moveUp.setByY(-60);
        sequence.getChildren().add(moveUp);
    }

    private void copyButtonValue(Button fromButton, Button toButton) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), toButton);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), toButton);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> toButton.setText(fromButton.getText()));

        SequentialTransition fadeSequence = new SequentialTransition(fadeOut, fadeIn);

        sequence.getChildren().add(fadeSequence);
    }

    private void putAuxValue(Button button, int auxValue) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), button);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), button);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> button.setText(String.valueOf(auxValue)));

        SequentialTransition fadeSequence = new SequentialTransition(fadeOut, fadeIn);

        sequence.getChildren().add(fadeSequence);
    }

}
