package net.azure;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
public class EndPoints {

        public static final String ASK_QUESTION = "/ai/{question}/{threadID}";
        public static final String GET_THREAD = "/ai/thread";
        public static final String SCHEDULE_ACTION = "/schedule.action";
        public static final String FETCH_TOWNS = "/fetchTowns";

        public static void configure(AiService server) {
            server.routes(() -> {
                get(ASK_QUESTION, AiController.question);
                get(GET_THREAD, AiController.thread);
            });
        }
    }


