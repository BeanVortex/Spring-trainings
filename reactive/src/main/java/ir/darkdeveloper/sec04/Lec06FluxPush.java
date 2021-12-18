package ir.darkdeveloper.sec04;

import ir.darkdeveloper.sec04.helper.NameProducerCreate;
import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec06FluxPush {
    
    public static void main(String[] args) {

        var nameProducerCreate = new NameProducerCreate();

        // create is thread safe but push is not
        Flux.push(nameProducerCreate)
                .subscribe(Util.subscriber());

        Runnable run = nameProducerCreate::produce;

        // Flux Sink is thread safe
        for (int i = 0; i < 10; i++) {
            new Thread(run).start();
        }

        Util.sleep(2);        
    }

}
