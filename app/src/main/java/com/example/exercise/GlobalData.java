package com.example.exercise;

import java.util.ArrayList;

public class GlobalData {
    private static GlobalData instance;
    private ArrayList<String> globalArray;
    private ArrayList<ArrayList<Integer>> nestedGlobalArray;

    private GlobalData() {
        globalArray = new ArrayList<>();
        nestedGlobalArray = new ArrayList<>(); // Initialize the nested array
    }

    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public ArrayList<String> getGlobalArray() {
        return globalArray;
    }

    public ArrayList<ArrayList<Integer>> getNestedGlobalArray() {
        return nestedGlobalArray;
    }
}
