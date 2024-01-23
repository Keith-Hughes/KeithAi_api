package net.azure;

import io.javalin.Javalin;
import net.azure.aiDO.AiThreadDO;
import net.azure.aiDO.MessageResponseDO;
import net.azure.aiDO.MessageResponseListDO;
import net.azure.aiDO.RunResponseDO;

import java.util.HashMap;
import java.util.Map;

public class AiService {
    Javalin app;
    int port = Integer.parseInt(System.getenv().getOrDefault("HTTP_PLATFORM_PORT", "8080"));
    AiAssistant aiAssistant;
    public AiService(){
        createServer();
        createEndpoints();
        this.aiAssistant = new AiAssistant(System.getenv("OPENAI_API_KEY"));
    }


    private void start(){
        app.start(port);
    }


    private void createServer(){
        app = Javalin.create();
    }

    private void createEndpoints(){
        app
            .get("/ai/{question}", context -> {
                String question = context.pathParam("question");
                AiThreadDO conversationThread =aiAssistant.createThread();
                String responseText= aiAssistant.executeMessage(conversationThread.id(), question);

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("response", responseText);
                jsonResponse.put("threadId", conversationThread.id());
                context.json(jsonResponse);
            })
            .get("/ai/{question}/{threadID}", context -> {
                String question = context.pathParam("question");
                String threadID = context.pathParam("threadID");
                String responseText= aiAssistant.executeMessage(threadID, question);
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("response", responseText);
                context.json(jsonResponse);


            });
    }


    public static void main(String[] args) {
        AiService service = new AiService();
        service.start();
    }
}
