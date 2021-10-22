package server.websocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.NotUsed;
import akka.http.javadsl.model.StatusCodes;
import akka.http.scaladsl.model.AttributeKeys;
import akka.japi.JavaPartialFunction;
import akka.japi.function.Function;

import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;

@SuppressWarnings({"Convert2MethodRef", "ConstantConditions"})
public class EchoWebsocketService
{
    // -----------------------------------------------------------------------------------------
    // Websocket Handlers
    // -----------------------------------------------------------------------------------------
    /**
     * A handler that treats incoming messages and responds with an echo for all message.
     * However, if the incoming message is "ping" then the response will be "pong".
     */
    public static Flow<Message, Message, NotUsed> echoStreamer() {
        return
                Flow.<Message>create()
                        .collect(new JavaPartialFunction<Message, Message>() {
                            @Override
                            public Message apply(Message msg, boolean isCheck) throws Exception {
                                if (isCheck) {
                                    if (msg.isText()) {
                                        return null;
                                    } else {
                                        throw noMatch();
                                    }
                                } else {
                                    return handleTextMessage(msg.asTextMessage());
                                }
                            }
                        });
    }
    public static TextMessage handleTextMessage(TextMessage msg) {
        if (msg.isStrict()) // optimization that directly creates a simple response...
        {
            String incomingMsg = msg.getStrictText();

            // On incoming msg is "ping" then respond "pong"
            if(incomingMsg.equals("ping"))
            {
                return TextMessage.create("pong");
            }
            else
            {
                return TextMessage.create(incomingMsg);
            }
        }
        else // default to handle all text messages in a streaming fashion
        {
            return TextMessage.create(Source.single("Server echo[Streaming]: ").concat(msg.getStreamedText()));
        }
    }

    // -----------------------------------------------------------------------------------------
    // Websocket Handling
    // -----------------------------------------------------------------------------------------
    public static HttpResponse handleRequest(HttpRequest request) {

        System.out.println("Handling request to " + request.getUri());

        if (request.getUri().path().equals("/echo"))
        {
            return request.getAttribute(AttributeKeys.webSocketUpgrade())
                    .map(upgrade -> {
                        Flow<Message, Message, NotUsed> echoFlow = echoStreamer();
                        HttpResponse response = upgrade.handleMessagesWith(echoFlow);
                        return response;
                    })
                    .orElse(
                            HttpResponse.create()
                                    .withStatus(StatusCodes.BAD_REQUEST)
                                    .withEntity("Expected WebSocket request")
                    );
        }
        else
        {
            return HttpResponse.create().withStatus(404);
        }
    }

    // -----------------------------------------------------------------------------------------
    // Websocket Service - Main Loop
    // -----------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {

        ActorSystem system = ActorSystem.create();

        try
        {
            final Function<HttpRequest, HttpResponse> handler = request -> handleRequest(request);

            CompletionStage<ServerBinding> serverBindingFuture =
                    Http.get(system)
                            .newServerAt("0.0.0.0", 8080)
                            .bindSync(handler);

            // will throw if binding fails
            serverBindingFuture.toCompletableFuture().get(1, TimeUnit.SECONDS);

            System.out.println("Websocket service uri ready >> ws://localhost:8080/echo");
            System.out.println("Press ENTER to stop.");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
        finally
        {
            system.terminate();
        }
    }
}
