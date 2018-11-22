package com.cdiving.test;

public class myClass {
    public static void main(String[] args){
       /* int[] a =  {130, 21, 25, 28, 99, 38, 102, 1};
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if(a[j] > a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ",");
        }*/
       Integer a = 123;
       Integer b = 123;
        System.out.println(a == b);
        Integer c = Integer.valueOf(123);
        Integer d = Integer.valueOf(123);
        System.out.println(c == d);
        System.out.println(a == c);
    }
}
