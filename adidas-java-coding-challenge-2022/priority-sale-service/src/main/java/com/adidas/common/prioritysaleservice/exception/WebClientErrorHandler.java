package com.adidas.common.prioritysaleservice.exception;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


/**
 * A web client error handler
 *
 * @author omar.bakhtaoui
 */
public class WebClientErrorHandler {
    /**
     * Handle web error with more detail and without custom messages
     *
     * @param clientResponse The Web-client response as {@link ClientResponse}
     * @return A {@link Mono} publisher with the exception processed info as {@link ResponseStatusException}
     */
    public static Mono<ResponseStatusException> manageError(ClientResponse clientResponse) {

        return clientResponse
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new ServiceClientException(
                                clientResponse.statusCode(), clientResponse.toString()))))
                .flatMap((String response) -> {
                    return Mono.error(new ServiceClientException(clientResponse.statusCode(), response));
                });
    }
}
