package org.acme;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.SseElementType;

@Path("/windSpeed")
public class WindSpeedResource {

    @Inject
    @Channel("windSpeedMph") Publisher<Double> windSpeed;
    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<Double> stream() {
        return windSpeed;
    }

    @Inject @Channel("windSpeedManual")
    Emitter<Integer> windSpeedEmitter;
    @POST
    @Path("/generate/{speed}")
    public Response generate(@PathParam("speed") Integer speed) {
        windSpeedEmitter.send(speed);
        return Response.status(Response.Status.CREATED).entity(speed).build();
    }
}
