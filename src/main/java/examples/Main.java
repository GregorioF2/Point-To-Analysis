package examples;

import tp.utils.Is;

import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        Object o = new Object();
        Is.sensible(o);
        System.out.println(o);

        Is.sanitize(o);
        System.out.println(o);

        Is.sensible(o);
        System.out.println(o);
    }

}
