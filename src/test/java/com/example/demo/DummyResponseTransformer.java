package com.example.demo;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
public class DummyResponseTransformer extends ResponseTransformer {

    static final String DUMMY_TRANSFORMER = "post_request_to_dummy_transformer";

    @Override
    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
        String prefix = parameters.getString("prefix");
        log.info("receive request id: {}, and prefix:{}", request.getBodyAsString(), prefix);
        String newId = prefix + request.getBodyAsString();
        return Response.response()
                .headers(new com.github.tomakehurst.wiremock.http.HttpHeaders(HttpHeader.httpHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)))
                .status(202)
                .body("{\"id\":\"" + newId + "\"}")
                .build();
    }

    @Override
    public String getName() {
        return DUMMY_TRANSFORMER;
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
