package org.example.trabalho02_po.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.trabalho02_po.algorithms.CombSortAnimation;
import org.example.trabalho02_po.algorithms.ShellSortAnimation;

public class MainWindow extends BorderPane {
    private ButtonPane buttonPane;

    private TextArea codeArea;

    private final String shellSortCode = """
            private void shellSort(int[] arr) {
                int n = arr.length;
                int i, j, aux, dist = 1;

                while (dist < n) {
                    dist = 3 * dist + 1;
                }
                dist = dist / 3;

                while (dist > 0) {
                    for (i = dist; i < n; i++) {
                        aux = arr[i];
                        j = i;
                        while (j >= dist && arr[j - dist] > aux) {
                            arr[j] = arr[j - dist];
                            addTransition(j, j - dist);
                            j = j - dist;
                        }
                        arr[j] = aux;
                    }
                    dist = dist / 3;
                }
            }
            """;
    private final String combSortCode = """
            public void combSort(int[] arr) {
                int n = arr.length;
                int gap = (int) (n / 1.3);
                int index;

                while(gap > 0) {
                    index = 0;
                    while((index + gap) < n) {
                        if(arr[index] > arr[index + gap]) {
                            int temp = arr[index];
                            vet[index] = arr[index + gap];
                            arr[index + gap] = temp;
                        }
                        index++;
                    }
                    gap = (int) (gap / 1.3);
                }
            }
            """;


    public MainWindow() {
        setupUI();
    }

    private void setupUI() {
        buttonPane = new ButtonPane();
        setupButtons();
        setupCodeArea();
    }

    private void setupButtons() {
        Button startCombSortButton = new Button("Iniciar ordenação por CombSort");
        startCombSortButton.getStyleClass().add("start-comb-button");
        startCombSortButton.setOnAction(e -> {
            codeArea.setText(combSortCode);
            startCombSort();
        });

        Button startShellSortButton = new Button("Iniciar ordenação por ShellSort");
        startShellSortButton.getStyleClass().add("start-shell-button");
        startShellSortButton.setOnAction(e -> {
            codeArea.setText(shellSortCode);
            startShellSort();
        });

        Button resetElementsButton = new Button("Resetar");
        resetElementsButton.getStyleClass().add("reset-button");
        resetElementsButton.setOnAction(e -> resetUI());

        HBox hbox = new HBox(20, startCombSortButton, startShellSortButton, resetElementsButton);
        hbox.setAlignment(Pos.TOP_LEFT);

        VBox vbox = new VBox(20, hbox, buttonPane);
        vbox.setAlignment(Pos.TOP_LEFT);
        this.setLeft(vbox);
    }

    private void setupCodeArea() {
        codeArea = new TextArea();
        codeArea.setEditable(false);
        codeArea.setFont(javafx.scene.text.Font.font("Consolas", 19));
        codeArea.setPadding(new Insets(10));
        codeArea.setPrefHeight(620);
        VBox rightPane = new VBox(codeArea);
        rightPane.setPadding(new Insets(10));
        this.setRight(rightPane);
    }

    private void startCombSort() {
        CombSortAnimation combSortAnimation = new CombSortAnimation(buttonPane, codeArea);
        combSortAnimation.startSort();
    }

    private void startShellSort() {
        ShellSortAnimation shellSortAnimation = new ShellSortAnimation(buttonPane, codeArea);
        shellSortAnimation.startSort();
    }

    private void resetUI() {
        buttonPane.resetToOriginalState();
        setupUI();
        codeArea.clear();
    }
}

