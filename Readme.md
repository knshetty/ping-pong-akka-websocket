# ping-pong-akka-websocket
A basic websocket-server implemented using Akka HTTP stream-based websocket

This is an **echo** websocket service. Following technologies were used to bring it to life:
- 	**[Java](https://oracle.com/java/ "Java")** programming language
- 	**[Akka HTTP](https://akka.io "Akka HTTP")** stream-based websocket
- 	**[Gradle](https://gradle.org "Gradle")** for build automation including [IntelliJ](https://www.jetbrains.com/idea/ "IntelliJ") IDE

### URI of this websocket service:
`ws://localhost:8080/echo`

### Future (ToDo):
- Deploy this websocket service on [Heroku](https://www.heroku.com/home "Heroku") clould platform as a service(PaaS)
- Provide TLS (Transport Layer Security) support i.e. wss://

## Useful references:
Akka Documentation
- https://developer.lightbend.com/guides/akka-http-quickstart-java/
- https://doc.akka.io/docs/akka-http/current/server-side/websocket-support.html

Examples
- https://github.com/akka/akka-http/blob/v10.2.6/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java
- https://github.com/akka/akka-http/blob/v10.2.6/docs/src/test/java/docs/http/javadsl/server/WebSocketRoutingExample.java
- https://github.com/JakubDziworski/Akka-Streams-Websocket-Game-Server

Heroku Deployment
- https://blog.rockthejvm.com/akka-http-to-heroku-in-x-minutes/
- https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku#the-procfile
- https://github.com/heroku/gradle-getting-started
- https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku
- https://devcenter.heroku.com/articles/setting-the-http-port-for-java-applications
- https://devcenter.heroku.com/articles/websockets
- https://devcenter.heroku.com/articles/play-java-websockets-advanced
- https://devcenter.heroku.com/articles/websocket-security