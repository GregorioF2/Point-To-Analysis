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

        Object o2 = returnSensibleFunc();
        System.out.println(o2);

        Is.sanitize(o2);

        System.out.println(o2);
    }

    public static Object returnSensibleFunc() {
        Object o = new Object();
        Is.sensible(o);
        return o;
    }

}
