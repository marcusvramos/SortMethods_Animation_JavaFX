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
        private void sort(int[] arr) {
            int j, newElement;
            int gap = arr.length / 2;
            
            while(gap > 0) {
                for (int i = gap; i < arr.length; i++) {
                    newElement = arr[i];
                    for(j = i - gap; j >= 0; j = j - gap) {
                        if (newElement < arr[j]) {
                            arr[j + gap] = arr[j];
                            arr[j] = newElement;
                        }
                    }
                }
                gap = gap / 2;
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

    private TextArea variablesArea;


    public MainWindow() {
        setupUI();
    }

    private void setupUI() {
        buttonPane = new ButtonPane();
        setupVariablesArea();
        setupCodeArea();
        HBox actionButtons = setupButtons();

        VBox leftContainer = new VBox(10);
        leftContainer.getChildren().addAll(actionButtons, buttonPane, variablesArea);
        leftContainer.setAlignment(Pos.TOP_LEFT);

        VBox.setMargin(buttonPane, new Insets(20, 0, 0, 0));

        VBox.setMargin(variablesArea, new Insets(20, 0, 0, 0));

        this.setLeft(leftContainer);
        this.setRight(codeArea);
    }



    private HBox setupButtons() {
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
        return hbox;
    }

    private void setupVariablesArea() {
        variablesArea = new TextArea();
        variablesArea.setEditable(false);
        variablesArea.setFont(javafx.scene.text.Font.font("Consolas", 20));
        variablesArea.setPadding(new Insets(10));
        variablesArea.setPrefHeight(135);
        variablesArea.setStyle("-fx-padding: 5;" +
                "-fx-control-inner-background: #f0f0f0;");
    }


    private void setupCodeArea() {
        codeArea = new TextArea();
        codeArea.setEditable(false);
        codeArea.setFont(javafx.scene.text.Font.font("Consolas", 19));
        codeArea.setPadding(new Insets(10));
        codeArea.setPrefHeight(500);
        VBox rightPane = new VBox(codeArea);
        rightPane.setPadding(new Insets(10));
        codeArea.setStyle("-fx-padding: 8;" +
                "-fx-control-inner-background: #adadad;");
        this.setRight(rightPane);
    }

    private void startCombSort() {
        CombSortAnimation combSortAnimation = new CombSortAnimation(buttonPane, codeArea, variablesArea);
        combSortAnimation.startSort();
    }

    private void startShellSort() {
        ShellSortAnimation shellSortAnimation = new ShellSortAnimation(buttonPane, codeArea, variablesArea);
        shellSortAnimation.startSort();
    }

    private void resetUI() {
        buttonPane.resetToOriginalState();
        setupUI();
        codeArea.clear();
        variablesArea.clear();
    }
}

