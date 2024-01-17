package net.azure;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class aiService {
    Javalin app;
    int port = Integer.parseInt(System.getenv().getOrDefault("HTTP_PLATFORM_PORT", "7000"));

    public aiService(){
        createServer();
        createEndpoints();
    }

    private void start(){
        app.start(port);
    }
    private void createServer(){
        app = Javalin.create();
    }

    private void createEndpoints(){
        app.get("/hello", context -> {context.result("Hello");});
    }


    public static void main(String[] args) {
        aiService service = new aiService();
        service.start();
    }
}
