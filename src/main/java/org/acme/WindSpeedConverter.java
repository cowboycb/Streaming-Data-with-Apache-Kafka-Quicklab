package org.acme;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

public class WindSpeedConverter {

    private static final double KphToMph = 0.621371;

    @Incoming("windSpeed")
    @Outgoing("windSpeedMph")
    @Broadcast
    public double process(int windSpeedinKph) {
        return windSpeedinKph * KphToMph;
    }

    @Incoming("windSpeedMph")
    public void showProcessedData(double windSpeedinMph) {
        System.out.println("processed data: " + windSpeedinMph);
    }
}
