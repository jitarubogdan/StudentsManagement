package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grila {
    public static void main(String[] args) {
        List<Integer> all = new ArrayList<>();
        ListOf<Integer> l = new ListOf<>();
        l.add(2,0);
        l.add(3,1);

        //for(int i=0;i < 2;i++)
         //   System.out.println(l.getAll()[i]);
        System.out.println(l.getAll());
    }
}
