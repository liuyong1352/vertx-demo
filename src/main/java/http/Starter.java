package http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public class Starter {

    public static void main(String args[]) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {

            long l = parseContentLengthHeader(request);
            // This handler gets called for each request that arrives on the server
            if (l > 0) {
                request.handler(buff -> {
                    request.response().end(buff);
                });
            } else {
                request.response().end("hi get!");
            }

        });

        server.listen(8080);
    }

    private static long parseContentLengthHeader(HttpServerRequest request) {
        String contentLength = request.getHeader(HttpHeaders.CONTENT_LENGTH);
        if (contentLength == null || contentLength.isEmpty()) {
            return -1;
        }
        try {
            long parsedContentLength = Long.parseLong(contentLength);
            return parsedContentLength < 0 ? null : parsedContentLength;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
