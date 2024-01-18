package net.azure;

import io.javalin.Javalin;
import net.azure.aiDO.AiThreadDO;
import net.azure.aiDO.MessageResponseDO;
import net.azure.aiDO.MessageResponseListDO;
import net.azure.aiDO.RunResponseDO;

public class aiService {
    Javalin app;
    int port = Integer.parseInt(System.getenv().getOrDefault("HTTP_PLATFORM_PORT", "7000"));
    String openAiAPIKey;
    AiAssistant aiAssistant;
    public aiService(){
        loadConfiguration();
        createServer();
        createEndpoints();
        this.aiAssistant = new AiAssistant(openAiAPIKey);
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
        app.get("/ai/{question}", context -> {
            String question = context.pathParam("question");
            AiThreadDO conversationThread =aiAssistant.createThread();
            MessageResponseDO messageResponse =aiAssistant.sendMessage(conversationThread.id(),"user", question);
            RunResponseDO runResponse = aiAssistant.runMessage(conversationThread.id());
            MessageResponseListDO messageResponseList = aiAssistant.getMessages(conversationThread.id());
            context.json(messageResponseList
                    .data()
                    .get(0)
                    .content()
                    .get(0)
                    .text()
                    .value());
        });
    }


    public static void main(String[] args) {
        aiService service = new aiService();
        service.start();
    }
}
