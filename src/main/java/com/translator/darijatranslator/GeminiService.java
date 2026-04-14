package com.translator.darijatranslator;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class GeminiService {

    private static final String API_KEY = "AIzaSyBdf0K6blkGsp4bQpW59d7DlDufx8_QKFQ";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public String translate(String text) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(API_URL);
            post.setHeader("Content-Type", "application/json");

            String body = """
                    {
                      "contents": [{
                        "parts": [{
                          "text": "Translate the following text to Moroccan Darija Arabic. Return only the translation, nothing else: %s"
                        }]
                      }]
                    }
                    """.formatted(text.replace("\"", "\\\""));

            post.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

            return client.execute(post, response -> {
                InputStream is = response.getEntity().getContent();
                String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("GEMINI RESPONSE: " + json);
                JSONObject obj = new JSONObject(json);
                if (obj.has("error")) {
                    String errorMsg = obj.getJSONObject("error").getString("message");
                    throw new RuntimeException("Gemini API error: " + errorMsg);
                }
                JSONArray candidates = obj.getJSONArray("candidates");
                return candidates.getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                        .trim();
            });
    } } }