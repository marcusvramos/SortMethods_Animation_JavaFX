package org.example.trabalho02_po.algorithms;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;


public class ShellSortAnimation extends SortAnimation {
    TextArea codeArea;

    private final String shellSortCode = """
        private void sort(int[] arr) {
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

    public ShellSortAnimation(ButtonPane buttonPane, TextArea codeArea) {
        super(buttonPane);
        this.codeArea = codeArea; // Adicione esta linha
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        sort(arr);
        playAnimation();
    }

    private void sort(int[] arr) {
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

    @Override
    public void playAnimation() {
        initializeButtonOrder();
        //go through transitions stored in the sort method and add a Translate Transition for each entry
        for(int i = 0; i < transitions.size(); i++) {
            animateIterative(true, i);
        }
        playSequentialTransition();
    }
}
