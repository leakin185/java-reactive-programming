package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06SupplierRefactoring {

    public static void main(String[] args) {

        getName();
        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());
        getName();

        Util.sleepSeconds(4);
    }

    // put business logic inside the supplier, so it gets executed lazily (does not get blocked)
    // now when we call getName(), it simply builds the pipeline, but it doesn't execute the pipeline until somebody subscribes to the publisher.
    private static Mono<String> getName(){
        System.out.println("entered getName method");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name..");
            Util.sleepSeconds(3);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase);
    }


}
