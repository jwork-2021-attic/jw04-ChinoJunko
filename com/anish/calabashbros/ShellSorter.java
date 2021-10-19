package com.anish.calabashbros;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ShellSorter<T extends Comparable<T>> implements Sorter<T> {

    private ArrayList<T> a;

    @Override
    public void load(T[][] m){
        a = new ArrayList<T>();
        for (T[] arr: m) {
            a.addAll(Arrays.asList(arr));
        }
    }

    private void swap(int i, int j) {
        T temp = a.get(i);
        a.set(i,a.get(j));
        a.set(j,temp);
        plan += "" + a.get(i) + "<->" + a.get(j) + "\n";
    }

    private String plan = "";

    @Override
    public void sort(){
        for(int gap=a.size()/2;gap>0;gap/=2){
            for(int i=gap;i<a.size();i++){
                int j = i;
                while(j-gap>=0 && a.get(j).compareTo(a.get(j-gap))<0){
                    swap(j,j-gap);
                    j-=gap;
                }
            }
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}