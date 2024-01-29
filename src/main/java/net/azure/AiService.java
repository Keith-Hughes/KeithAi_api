package net.azure;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
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
        this.aiAssistant = new AiAssistant(System.getenv("OPENAI_API_KEY"));
        AiController.setAssistant(aiAssistant);
        EndPoints.configure(this);
    }
    public void routes(EndpointGroup group) {
        app.routes(group);
    }


    private void start(){
        app.start(port);
    }


    private void createServer(){
        app = Javalin.create();
    }


    public static void main(String[] args) {
        AiService service = new AiService();
        service.start();
    }
}
