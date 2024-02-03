package net.azure;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import net.azure.aiDO.*;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AiAssistant {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    String openAiKey;
    String assistantID;
    public AiAssistant(String apiKey, String AssistantID) {
        this.assistantID = AssistantID;
        this.openAiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private String post(String url, Object body) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + openAiKey)
                    .header("Content-Type", "application/json")
                    .header("OpenAI-Beta", "assistants=v1")  // Add this line
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Unable to map string to json: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to send request: " + e.getMessage());

        }
    }

    public AiThreadDO createThread() {
        String url = "https://api.openai.com/v1/threads";
        String response = post(url, "");
        try {
            return objectMapper.readValue(response, AiThreadDO.class);
        } catch (IOException e) {
            throw new RuntimeException("unable to map to Thread class: " + e.getMessage());
        }
    }

    public MessageResponseDO sendMessage(String threadId, String role, String content) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";
        MessageDO dto = new MessageDO(role, content);
        String response = post(url, dto);
        return objectMapper.readValue(response, MessageResponseDO.class);
    }

    public RunResponseDO runMessage(String threadId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/runs";
        RunRequestDO dto = new RunRequestDO(assistantID);

        String response = post(url, dto);
        System.out.println("run response: "+response);
        RunResponseDO runResponse = objectMapper.readValue(response, RunResponseDO.class);
        System.out.println("run id: "+runResponse.id());

        while (!runResponse.status().equalsIgnoreCase("completed")){

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url+"/"+runResponse.id()))
                    .header("Authorization", "Bearer " + openAiKey)
                    .header("OpenAI-Beta", "assistants=v1")
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            Thread.sleep(3000);
            HttpResponse<String> response2 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("new response: "+response2.body());
            runResponse = objectMapper.readValue(response2.body(), RunResponseDO.class);

        }
        return runResponse;
    }

    public MessageResponseListDO getMessages(String threadId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + openAiKey)
                .header("OpenAI-Beta", "assistants=v1")
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), MessageResponseListDO.class);
    }

    public String executeMessage(String threadID, String question) throws Exception {
        sendMessage(threadID,"user", question);
        runMessage(threadID);
        System.out.println("Thread ID: "+threadID);
        return getMessages(threadID)
                .data()
                .get(0)
                .content()
                .get(0)
                .text()
                .value();
    }
}
