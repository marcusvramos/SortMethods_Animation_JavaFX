package org.example.trabalho02_po.algorithms;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;

public class ShellSortCopyAnimation extends SortAnimation {
    private final TextArea codeArea;
    private final TextFlow variablesArea;
    private final TextFlow descriptionArea;
    private final HBox auxButtonContainer;
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
                                arr[j] = arr[j - dist];
                                j = j - dist;
                            }
                            arr[j] = aux;
                        }
                        dist = dist / 3;
                    }
                }
            """;

    public ShellSortCopyAnimation(
        ButtonPane buttonPane,
        TextArea codeArea,
        TextFlow variablesArea,
        HBox auxButtonContainer,
        TextFlow descriptionArea
    ) {
        super(buttonPane);
        this.codeArea = codeArea;
        this.variablesArea = variablesArea;
        this.auxButtonContainer = auxButtonContainer;
        this.descriptionArea = descriptionArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        setupAuxButton();
        shellSort(arr);
        playSequentialTransition();
    }

    private void setupAuxButton() {
        auxButtonContainer.getChildren().clear();

        Text label = new Text("aux = ");
        label.setFont(Font.font("System", FontWeight.BOLD, 20));

        Button auxButton = new Button(String.valueOf(0));
        auxButton.setFont(Font.font("System", FontWeight.BOLD, 20));
        auxButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
        auxButtonContainer.getChildren().addAll(label, auxButton);
    }

    private void shellSort(int[] arr) {
        addVariableUpdateToSequence(0, 0, 1);
        sequence.getChildren().add(createHighlightPause(2));
        int i, j, aux, dist = 1;

        sequence.getChildren().add(createHighlightPause(3));
        while (dist < arr.length) {
            addVariableUpdateToSequence(0, 0, dist);
            dist = 3 * dist + 1;
            sequence.getChildren().add(createHighlightPause(4));
            addVariableUpdateToSequence(0, 0, dist);
        }
        sequence.getChildren().add(createHighlightPause(5));

        dist = dist / 3;
        sequence.getChildren().add(createHighlightPause(6));
        addVariableUpdateToSequence(0, 0, dist);

        sequence.getChildren().add(createHighlightPause(7));
        while (dist > 0) {
            sequence.getChildren().add(createHighlightPause(8));
            for (i = dist; i < arr.length; i++) {
                sequence.getChildren().add(createHighlightPause(9));
                addVariableUpdateToSequence(i, 0, dist);


                aux = arr[i];
                sequence.getChildren().add(createHighlightPause(10));
                sequence.getChildren().add(updateAuxButton(aux));
                Button auxButton = (Button) auxButtonContainer.getChildren().getLast();
                addVariableUpdateToSequence(i, 0, dist);

                j = i;
                sequence.getChildren().add(createHighlightPause(11));
                addVariableUpdateToSequence(i, j, dist);

                sequence.getChildren().add(createHighlightPause(12));
                Button compareButton01 = buttonPane.getButtons().get(j - dist);
                sequence.getChildren().add(createHighlightTransition(compareButton01, auxButton, "orange"));

                boolean entrou = false;
                int index = 0;
                while (j >= dist && arr[j - dist] > aux) {
                    Button buttonJ = buttonPane.getButtons().get(j);
                    entrou = true;

                    if (arr[j - dist] > aux) {
                        sequence.getChildren().add(createHighlightTransition(compareButton01, auxButton, "green"));

                        sequence.getChildren().add(createUnhighlightTransition(compareButton01, buttonJ));

                        sequence.getChildren().add(createSimpleUnhighlightTransition(auxButton, true));

                        sequence.getChildren().add(createHighlightTransition(compareButton01, buttonJ, "orange"));
                    }

                    sequence.getChildren().add(createHighlightPause(13));
                    arr[j] = arr[j - dist];
                    addDescriptionSequence(j, arr[j-dist], false);

                    sequence.getChildren().add(createHighlightPause(14));
                    copyButtonValue(buttonPane.getButtons().get(j - dist), buttonPane.getButtons().get(j));

                    j = j - dist;
                    sequence.getChildren().add(createHighlightPause(14));
                    addVariableUpdateToSequence(i, j, dist);

                    sequence.getChildren().add(createUnhighlightTransition(compareButton01, buttonJ));
                    sequence.getChildren().add(createSimpleUnhighlightTransition(auxButton, true));

                    if (j - dist >= 0){
                        sequence.getChildren().add(createHighlightPause(12));
                        compareButton01 = buttonPane.getButtons().get(j - dist);
                        sequence.getChildren().add(createHighlightTransition(compareButton01, auxButton, "orange"));
                        sequence.getChildren().add(createSimpleUnhighlightTransition(buttonJ, false));
                        index++;
                    }
                    sequence.getChildren().add(createSimpleUnhighlightTransition(buttonJ, false));
                }

                if (!entrou || index >= 1) {
                    sequence.getChildren().add(createHighlightTransition(compareButton01, auxButton, "red"));
                }
                sequence.getChildren().add(createUnhighlightTransition(compareButton01, buttonPane.getButtons().get(j)));
                sequence.getChildren().add(createSimpleUnhighlightTransition(auxButton, true));
                sequence.getChildren().add(createHighlightPause(15));

                arr[j] = aux;

                sequence.getChildren().add(createHighlightPause(16));
                addDescriptionSequence(j, aux, true);

                Button buttonToReceive = buttonPane.getButtons().get(j);
                PauseTransition highlight = createSimpleHighlightTransition(buttonToReceive, "gray");
                sequence.getChildren().addAll(highlight);

                putAuxValue(buttonPane.getButtons().get(j), aux);

                PauseTransition unhighlight = createSimpleUnhighlightTransition(buttonToReceive, false);
                PauseTransition delay = new PauseTransition(Duration.millis(1000));
                sequence.getChildren().addAll(unhighlight, delay);
            }
            sequence.getChildren().add(createHighlightPause(18));

            dist = dist / 3;
            sequence.getChildren().add(createHighlightPause(19));
            addVariableUpdateToSequence(0, 0, dist);
        }
        sequence.getChildren().add(updateAuxButton(0));
        sequence.getChildren().add(createHighlightPause(20));
    }

    private PauseTransition createSimpleUnhighlightTransition(Button button, Boolean isAux) {
        PauseTransition unhighlight;
        if (isAux) {
            unhighlight = new PauseTransition(Duration.millis(1));
            unhighlight.setOnFinished(e -> button.setStyle(
                    "-fx-background-color: lightblue; " +
                            "-fx-text-fill: black;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 20px;"
            ));
        } else {
            unhighlight = new PauseTransition(Duration.millis(1));
            unhighlight.setOnFinished(e -> button.setStyle(""));
        }

        return unhighlight;
    }

    private void addDescriptionSequence(int j, int aux, boolean isAux) {
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        sequence.getChildren().add(pause);
        pause.setOnFinished(e -> updateDescription(j, aux, isAux));
    }

    private void updateDescription(int j, int aux, boolean isAux) {
        Text description = new Text();
        Text descriptionTitle = new Text("Descrição das Movimentações: \n");
        descriptionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        if (isAux) {
            description.setText("Botão na posição j [" + j + "] recebe o valor de aux (" + aux + ").\n");
        } else {
            description.setText("Botão na posição j [" + j + "] recebe o valor de arr[j-dist] (" + aux + ").\n");
        }
        description.setStyle("-fx-font-size: 20px;");
        descriptionArea.getChildren().clear();
        descriptionArea.getChildren().addAll(descriptionTitle, description);
    }

    private PauseTransition updateAuxButton(int newValue) {
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(e -> {
            Button auxButton = (Button) auxButtonContainer.getChildren().get(1);
            auxButton.setText(String.valueOf(newValue));
            auxButton.setFont(Font.font("System", FontWeight.BOLD, 20));
            auxButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
        });
        return pause;
    }

    private void copyButtonValue(Button fromButton, Button toButton) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), toButton);
        fadeOut.setToValue(0.0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), toButton);
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

    private void updateVariablesDisplay(int i, int j, int dist) {
        boolean isMaiorOuIgual = j >= dist;
        Text isMaiorOuIgualStr = isMaiorOuIgual ? new Text("Verdade\n") : new Text("Falso\n");

        isMaiorOuIgualStr.setFill(isMaiorOuIgual ? Color.GREEN : Color.RED);
        isMaiorOuIgualStr.setFont(Font.font("System", 20));

        int jDist = Math.max((j - dist), 0);
        Text textI = new Text("i: " + i + "\n");
        textI.setStyle("-fx-font-size: 20px;");
        Text textJ = new Text("j: " + j + "\n");
        textJ.setStyle("-fx-font-size: 20px;");
        Text textDist = new Text("dist: " + dist + "\n");
        textDist.setStyle("-fx-font-size: 20px;");
        Text textJDist = new Text("(j-dist): " + jDist + "\n");
        textJDist.setStyle("-fx-font-size: 20px;");
        Text textJmenorOuIgualADist = new Text("j >= dist: ");
        textJmenorOuIgualADist.setStyle("-fx-font-size: 20px;");

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

        variablesArea.getChildren().clear();
        variablesArea.getChildren().addAll(
            textI,
            textJ,
            textDist,
            textJDist,
            textJmenorOuIgualADist,
            isMaiorOuIgualStr,
            textObs,
            textRed,
            textFalse,
            textGreen,
            textTrue
        );
    }

    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }

    protected void addVariableUpdateToSequence(int i, int j, int dist) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(e -> updateVariablesDisplay(i, j, dist));
        sequence.getChildren().add(pause);
    }
}
