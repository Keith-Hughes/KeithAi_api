package net.azure;

import io.javalin.http.Handler;
import net.azure.aiDO.AiThreadDO;

import java.util.HashMap;
import java.util.Map;

public class AiController {

    private static AiAssistant assistant;

    public static void setAssistant(AiAssistant assistant){
        AiController.assistant = assistant;
    }
    public static final Handler question = context -> {

        String question = context.pathParam("question");
        String threadID = context.pathParam("threadID");
        String responseText= assistant.executeMessage(threadID, question);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("response", responseText);
        context.json(jsonResponse);
    }

    public static final Handler thread = context -> {
        AiThreadDO conversationThread =assistant.createThread();
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("threadId", conversationThread.id());
        context.json(jsonResponse);
    }
}
