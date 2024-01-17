package net.azure;

import io.javalin.Javalin;

public class aiService {
    Javalin app;
    int port = Integer.parseInt(System.getenv().getOrDefault("HTTP_PLATFORM_PORT", "7000"));
    String openAiAPIKey;
    public aiService(){
        loadConfiguration();
        createServer();
        createEndpoints();
    }

    private void loadConfiguration() {
        // Load OpenAI API key from environment variables
        openAiAPIKey = System.getenv("OPENAI_API_KEY");


    }

    private String checkKey(){
        if (openAiAPIKey == null || openAiAPIKey.isEmpty()) {
            return "false";
        }
        return "true";
    }

    private void start(){
        app.start(port);
    }


    private void createServer(){
        app = Javalin.create();
    }

    private void createEndpoints(){
        app.get("/key", context -> {context.result(checkKey());});
    }


    public static void main(String[] args) {
        aiService service = new aiService();
        service.start();
    }
}
