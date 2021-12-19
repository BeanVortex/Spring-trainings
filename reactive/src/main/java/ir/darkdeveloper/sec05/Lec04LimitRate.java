package ir.darkdeveloper.sec05;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec04LimitRate {

    public static void main(String[] args) {
        
        // if subscriber want a huge data, we can do something like paging and break request to smaller requests
        // for more read javadoc
        Flux.range(1, 100)
        .log()
        .limitRate(10) // 75% 
        // .limitRate(10, 5) // 
        .subscribe(Util.subscriber());
        
    }
    
}