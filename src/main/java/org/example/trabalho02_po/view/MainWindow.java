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
import org.example.trabalho02_po.algorithms.ShellSortCopyAnimation;

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
    private final String shellSortCode02 = """
                public void shellSort(int[] arr) {
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

    private TextArea variablesArea;


    public MainWindow() {
        setupUI();
    }

    private void setupUI() {
        buttonPane = new ButtonPane();
        setupVariablesArea();
        setupCodeArea();
        HBox actionButtons = setupButtons();

        VBox leftContainer = new VBox(20);
        leftContainer.getChildren().addAll(actionButtons, buttonPane, variablesArea);
        leftContainer.setAlignment(Pos.TOP_LEFT);

        VBox.setMargin(buttonPane, new Insets(80, 0, 0, 0));
        VBox.setMargin(variablesArea, new Insets(80, 0, 0, 0));


        this.setLeft(leftContainer);
        this.setRight(codeArea);
    }



    private HBox setupButtons() {
        Button startCombSortButton = new Button("Iniciar CombSort");
        startCombSortButton.getStyleClass().add("start-comb-button");
        startCombSortButton.setOnAction(e -> {
            codeArea.setText(combSortCode);
            startCombSort();
        });

        Button startShellSortButton01 = new Button("Iniciar ShellSort 01");
        startShellSortButton01.getStyleClass().add("start-shell-button");
        startShellSortButton01.setOnAction(e -> {
            codeArea.setText(shellSortCode);
            startShellSort01();
        });

        Button startShellSortButton02 = new Button("Iniciar ShellSort 02");
        startShellSortButton02.getStyleClass().add("start-shell02-button");
        startShellSortButton02.setOnAction(e -> {
            codeArea.setText(shellSortCode02);
            startShellSort02();
        });

        Button resetElementsButton = new Button("Resetar");
        resetElementsButton.getStyleClass().add("reset-button");
        resetElementsButton.setOnAction(e -> resetUI());

        HBox hbox = new HBox(30, startCombSortButton, startShellSortButton01, startShellSortButton02, resetElementsButton);
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
                "-fx-control-inner-background: #f0f0f0;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;");


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
                "-fx-control-inner-background: #adadad;" +
                "-fx-border-radius: 20;" +
                "-fx-background-radius: 20;");

        this.setRight(rightPane);
    }

    private void startCombSort() {
        CombSortAnimation combSortAnimation = new CombSortAnimation(buttonPane, codeArea, variablesArea);
        combSortAnimation.startSort();
    }

    private void startShellSort02() {
        ShellSortCopyAnimation shellSortAnimation = new ShellSortCopyAnimation(buttonPane, codeArea, variablesArea);
        shellSortAnimation.startSort();
    }

    private void startShellSort01() {
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

