package org.example.trabalho02_po.algorithms;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
                    sequence.getChildren().add(createHighlightPause(8));
                    addVariableUpdateToSequence(i, j, gap, aux);

                    if (aux < arr[j]) {
                        final Button buttonToHighlight = buttonPane.getButtons().get(buttonOrder[j]);
                        final Button currentButton = buttonPane.getButtons().get(buttonOrder[j]);
                        PauseTransition highlight = createHighlightTransition(buttonToHighlight, "orange");
                        PauseTransition highlightCurrent = createHighlightTransition(currentButton, "orange");
                        PauseTransition delay = new PauseTransition(Duration.millis(500));
                        sequence.getChildren().addAll(highlight, highlightCurrent, delay);

                        sequence.getChildren().add(createHighlightPause(9));
                        addVariableUpdateToSequence(i, j, gap, aux);

                        arr[j + gap] = arr[j];
                        sequence.getChildren().add(createHighlightPause(10));
                        addVariableUpdateToSequence(i, j, gap, aux);

                        arr[j] = aux;
                        sequence.getChildren().add(createHighlightPause(11));
                        addVariableUpdateToSequence(i, j, gap, aux);

                        animateIterative(j + gap, j);
                    }
                    final Button buttonToHighlight = buttonPane.getButtons().get(buttonOrder[i]);
                    final Button currentButton = buttonPane.getButtons().get(buttonOrder[j]);
                    PauseTransition unhighlight = createUnhighlightTransition(buttonToHighlight);
                    PauseTransition unhighlightCurrent = createUnhighlightTransition(currentButton);
                    PauseTransition delay = new PauseTransition(Duration.millis(500));
                    sequence.getChildren().addAll(unhighlight, unhighlightCurrent, delay);

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
        // Cria objetos Text para cada variável
        Text textI = new Text("i: " + i + "\n");
        textI.setFont(Font.font("System", 20));
        Text textJ = new Text("j: " + j + "\n");
        textJ.setFont(Font.font("System", 20));
        Text textGap = new Text("gap: " + gap + "\n");
        textGap.setFont(Font.font("System", 20));
        Text textAux = new Text("aux: " + aux + "\n");
        textAux.setFont(Font.font("System", FontWeight.BOLD, 20)); // Exemplo de fazer 'aux' em negrito

        Platform.runLater(() -> {
            variablesArea.getChildren().clear();
            variablesArea.getChildren().addAll(textI, textJ, textGap, textAux);
        });

    }


    protected PauseTransition createHighlightPause(int lineNumber) {
        PauseTransition highlightPause = new PauseTransition(Duration.millis(1000));
        highlightPause.setOnFinished(e -> highlightLine(lineNumber, shellSortCode, codeArea));
        return highlightPause;
    }
}
