package org.example.trabalho02_po.algorithms;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.trabalho02_po.view.ButtonPane;

import java.util.List;

public class CombSortAnimation extends SortAnimation {
    TextArea codeArea;

    private final String combSortCode = """
        private void sort(int[] arr) {
           int n = arr.length;
           int gap = (int) (n / 1.3);
           int index;
               
           while(gap > 0) {
               index = 0;
               while((index + gap) < n) {
                   if(arr[index] > arr[index + gap]) {
                       int temp = arr[index];
                       arr[index] = arr[index + gap];
                       arr[index + gap] = temp;
                       addTransition(index, index + gap);
                   }
                   index++;
               }
               gap = (int) (gap / 1.3);
           }
        }
        """;

    public CombSortAnimation(ButtonPane buttonPane, TextArea codeArea) {
        super(buttonPane);
        this.codeArea = codeArea;
    }

    @Override
    public void startSort() {
        List<Button> buttons = buttonPane.getButtons();
        int[] arr = buttons.stream().mapToInt(b -> Integer.parseInt(b.getText())).toArray();
        sort(arr);
        playAnimation();
    }

    public void sort(int[] arr) {
        int n = arr.length;
        int gap = (int) (n / 1.3);
        int index;

        while(gap > 0) {
            index = 0;
            while((index + gap) < n) {
                if(arr[index] > arr[index + gap]) {
                    int temp = arr[index];
                    arr[index] = arr[index + gap];
                    arr[index + gap] = temp;
                    addTransition(index + gap, index);
                }
                index++;
            }
            gap = (int) (gap / 1.3);
        }
    }

    @Override
    public void playAnimation() {
        initializeButtonOrder();
        for(int i = 0; i < transitions.size(); i++) {
            animateIterative(true, i);
        }
        playSequentialTransition();
    }
}
