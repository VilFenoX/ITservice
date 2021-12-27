package com.itservice.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MagicSquare {

    int[][] square;
    boolean[] used;
    int n=3;
    int magicSum;
    int total=0;
    int totalSum=Integer.MAX_VALUE;
    int[][] finalMatrix = {{1,2,3},{4,5,6},{7,8,9}};
    StringForm stringForm = new StringForm();

    public MagicSquare() {
        square = new int[n][n];
        for (int i = 0; i < n; i++) square[i] = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) square[i][j] = ++k;
        used = new boolean[n * n + 1];
        for (int i = 0; i < n * n + 1; i++) used[i] = false;
       // this.n = n;
       // this.total = 0;
        magicSum = n * (n * n + 1) / 2;
    }

/*    public MagicSquare() {
    }*/

    boolean isValid() {
        for (int i = 0; i < n; i++) {
            int sumR = 0;
            int sumC = 0;
            for (int j = 0; j < n; j++) {
                sumR += square[i][j];
                sumC += square[j][i];
            }
            if (sumR != magicSum || sumC != magicSum) {
                return false;
            }
        }
        return true;
    }

   public void count(int step, int[] fromView) { //подсчитать с выводом
        if (step == n * n) {
            if (isValid()) {
                total++;
               // outputSolution();
               // System.out.println();
                sum(n, fromView);
            }
            return;
        }
        for (int val = 1; val <= n * n; val++) {
            if (used[val]) {
                continue;
            }
            used[val] = true;
            square[step / n][step % n] = val;
            count(step + 1, fromView);
            square[step / n][step % n] = 0;
            used[val] = false;
        }
    }

    void outputSolution() {
        System.out.println();
        System.out.println("# " + total);
        ;
        for (int r = 0; r < n; r++) {
            System.out.println();
            for (int c = 0; c < n; c++) {
                System.out.print(square[r][c]);
                ;
            }
        }
    }

    public int[] split(String valueFromView){
        return Stream.of(valueFromView.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public int sum(int n, int[] fromView){
        int count = 0;
        int[] array = fromView;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = array[count++];
            }
        }

        int sum = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
             sum +=  Math.abs( square[r][c]- matrix[r][c]) ;
                
            }
        }
        if(sum<totalSum){
            totalSum=sum;
            finalMatrix = square;
            returnStringForm();
        }

        return sum;
    }

   public void returnStringForm(){
       // StringBuilder res = new StringBuilder();
       List<String>  res = new ArrayList<>();
        stringForm.setValue("");
        for (int r = 0; r < n; r++) {
           for (int c = 0; c < n; c++) {
               res.add(String.valueOf(finalMatrix[r][c]));
             //  System.out.print(finalMatrix[r][c]);
           }
       }
        stringForm.setResult(res);
       // stringForm.setRes2(finalMatrix);
        stringForm.setSum(totalSum);

    }

    public void returnString(){
        System.out.println();
        System.out.println(totalSum);
        for (int r = 0; r < n; r++) {
            System.out.println();
            for (int c = 0; c < n; c++) {
                System.out.print(finalMatrix[r][c]);
            }
        }
    }
    public StringForm start(String fromView){
        count(0, split(fromView));
        return stringForm;
    }


/*
   public static void main(String[] args) {
       MagicSquare m = new MagicSquare(3);
       int[] DimArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
       //m.count(0, DimArray);
        m.start(DimArray);
   }
*/

}




