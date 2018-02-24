package com.astimefades.beatsyncservice.controller;

import com.astimefades.beatsyncservice.model.error.BeatSyncError;
import com.astimefades.beatsyncservice.model.error.BeatSyncException;
import com.astimefades.beatsyncservice.model.request.Request;
import com.astimefades.beatsyncservice.model.response.Response;
import com.astimefades.beatsyncservice.util.CheckedFunction;
import org.apache.log4j.Logger;

import java.util.function.Function;

public class Controller {
    private Logger log = Logger.getLogger(Controller.class);

    public <R> Response<R> getErrorResponse(Exception e) {
        log.error("Error while processing request", e);

        Response<R> response = new Response<>();

        BeatSyncException exception = (e instanceof BeatSyncException)
                ? (BeatSyncException) e
                : new BeatSyncException(BeatSyncError.UNHANDLED);
        if(exception != null) {
            response.setErrorNumber(exception.getErrorNumber());
            response.setErrorDescription(exception.getErrorDescription());
        }

        return response;
    }

    public <T, R> Response<R> process(CheckedFunction<T, R> func, Request<T> request) {
        R responsePayload = null;
        try {
            responsePayload = func.apply(request.getPayload());
        } catch(Exception e) {
            return getErrorResponse(e);
        }

        return new Response<>(responsePayload);
    }

    public <T, R> Response<R> processNoRequest(CheckedFunction<T, R> func, T requestPayload) {
        Request<T> request = new Request<>();
        request.setPayload(requestPayload);

        return process(func, request);
    }
}