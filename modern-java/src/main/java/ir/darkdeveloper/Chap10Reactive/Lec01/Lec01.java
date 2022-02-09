package ir.darkdeveloper.Chap10Reactive.Lec01;


public class Lec01 {

    public static void main(String[] args) {
        // var c1 = new SimpleCell("c1");
        // var c2 = new SimpleCell("c2");
        // var c3 = new SimpleCell("c3");

        // c1.subscribe(c3);
        // c1.onNext(10);
        // c2.onNext(20);

        var c3 = new ArithmeticCell("C3");
        var c5 = new ArithmeticCell("C5");
        var c4 = new SimpleCell("C4");
        var c2 = new SimpleCell("C2");
        var c1 = new SimpleCell("C1");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c3.subscribe(c5::setLeft);
        c4.subscribe(c5::setRight);

        c1.onNext(10);
        c2.onNext(20);
        c1.onNext(15);
        c2.onNext(5);
        c4.onNext(6);
        c4.onNext(9);

    }

}
