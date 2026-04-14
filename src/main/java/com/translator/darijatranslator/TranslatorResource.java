package com.translator.darijatranslator;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/translate")
public class TranslatorResource {

    @Inject
    private GeminiService geminiService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response translate(TranslationRequest request) {
        if (request == null || request.getText() == null || request.getText().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("{\"error\": \"Text cannot be empty\"}")
                    .build();
        }
        try {
            String translation = geminiService.translate(request.getText());
            org.json.JSONObject result = new org.json.JSONObject();
            result.put("translation", translation);
            return Response.ok(result.toString())
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            org.json.JSONObject error = new org.json.JSONObject();
            error.put("error", "Translation failed: " + e.getMessage());
            return Response.serverError()
                    .header("Access-Control-Allow-Origin", "*")
                    .entity(error.toString())
                    .build();
        }
    }

    @OPTIONS
    public Response handleOptions() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .build();
    }
}