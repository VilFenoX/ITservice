package com.itservice.model;

import java.util.Set;
import java.util.TreeSet;

public class SubStringContain {
    public static void main(String args[]){
        String[] a1={"tarp", "mice", "bull"};
        String[] a2={"lively", "alive", "harp", "sharp", "armstrong"};

        String a3[] = inArray(a1,a2);
        for(int i=0; i<a3.length; i++){
            System.out.println(a3[i]);
        }
    }

    public static String[] inArray(String[] array1, String[] array2) {
        Set<String> res = new TreeSet<>();
        for(int i=0;i<array1.length;i++){
            if(isContain(array2, array1[i])){
                res.add(array1[i]);
            }
        }
        return res.toArray(new String[res.size()]);
    }

    public static boolean isContain(String[] arr, String wrd){
        for (int i=0; i<arr.length; i++){
            if(arr[i].contains(wrd)) {
                return true;
            }
        }
        return false;
    }
}
