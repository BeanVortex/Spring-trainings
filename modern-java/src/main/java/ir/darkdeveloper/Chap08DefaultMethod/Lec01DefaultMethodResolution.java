package ir.darkdeveloper.Chap08DefaultMethod;

public class Lec01DefaultMethodResolution extends C implements A, B {

    public static void main(String[] args) {
        new Lec01DefaultMethodResolution().hello();

        // three resolution rules
        /* 
            1. Classes always win: A method declaration in the class or
                 a superclass takes prior-ity over any default method declaration
        
            2. Otherwise, subinterfaces win: the method with the same signature in 
                the most specific default-providing interface is selected. 
                (If B extends A, B is more specific than A.)
        
            3. Finally, if the choice is still ambiguous, the class inheriting 
                from multiple interfaces has to explicitly select which default method 
                implementation to use by overriding it and calling the desired method explicitly
        */

    }

}

interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}

interface B {
    default void hello() {
        System.out.println("Hello from B");
    }
}

class C implements A, B {
    @Override
    public void hello() {
        B.super.hello();
    }
}