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