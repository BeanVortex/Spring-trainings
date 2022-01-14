package ir.darkdeveloper.Chap04Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Lec02ListSetExtraFeatures {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);

        // throws ConcurrentModificationException 
        assertThrows(ConcurrentModificationException.class, () -> {
            for (Integer integer : list)
                if (integer % 2 == 0)
                    list.remove(integer);
        });
        // because under the hood the code is:
        // in this case collection is modified but iterator is the same one
        assertThrows(ConcurrentModificationException.class, () -> {
            for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
                var integer = i.next();
                if (integer % 2 == 0)
                    list.remove(integer);
            }
        });
        //solution 
        for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
            var integer = i.next();
            if (integer % 2 == 0)
                i.remove();
        }
        System.out.println(list);
        list.add(2);
        list.add(4);
        // concise solution
        list.removeIf(i -> i % 2 == 0);
        System.out.println(list);

        // replacing all 
        // previous solution
        for (var i = list.listIterator(); i.hasNext();) {
            var integer = i.next();
            i.set(integer * 2 + 5);
        }
        System.out.println(list);

        // with stream
        // note: this one creates a ne list
        System.out.println(
                list.stream().map(i -> i * 2 + 5)
                        .collect(Collectors.toList()));

        // with replace all
        list.replaceAll(i -> i * 2 + 5);
        System.out.println(list);

    }

}
