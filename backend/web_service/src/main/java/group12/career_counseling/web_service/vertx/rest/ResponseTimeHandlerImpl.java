package group12.career_counseling.web_service.vertx.rest;

import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class ResponseTimeHandlerImpl implements ResponseTimeHandler {

    private static final CharSequence HEADER_NAME = HttpHeaders.createOptimized("x-response-time");

    @Override
    public void handle(RoutingContext ctx) {
        long start = System.nanoTime();
        ctx.addHeadersEndHandler(v -> {
            long duration = MILLISECONDS.convert(System.nanoTime() - start, NANOSECONDS);
            ctx.response().putHeader(HEADER_NAME, duration + "ms");
            ctx.response().putHeader("path",ctx.request().path());
        });
        ctx.next();
    }
}
