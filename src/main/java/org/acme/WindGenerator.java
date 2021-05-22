package org.acme;
import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
Generates a random weather wind reading every second between 0 and 50 in km/h
 */
@ApplicationScoped
public class WindGenerator {

    private Random random = new Random();

    @Outgoing("windSpeedKph")
    public Flowable<Integer> generate() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(tick -> random.nextInt(50));
    }

    @Incoming("windSpeedManual")
    @Outgoing("windSpeedKph")
    public Integer generateManual(int windSpeedManual){
        return windSpeedManual;
    }


}
