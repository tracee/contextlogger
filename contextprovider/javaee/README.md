> This document contains documentation for the contextprovider-javaee module. Click [here](/README.md) to get an overview that TracEE is about.

# contextprovider-javaee

> The TracEE contextprovider-javaee project offers support for acquiring contextual information in JMS and for EJB / CDI calls, if an exception is thrown during the execution of the invoked bean. 

Therefore the contextprovider-javaee module provides an Interceptors / message listeners for gathering contextual information of ejb/cdi calls and a jms message processing.

## Example output
Depending on the selected context logger profile the output of the ejb interceptor consists of method invocation parameters, exception data and jms message related data in case of JMS.

    TODO

## Maven artifacts
You need to add the following Dependencies to your projects pom.xml:

```xml
<!-- Binds the TracEE api -->
<dependency>
    <groupId>io.tracee</groupId>
    <artifactId>tracee-api</artifactId>
    <version>RELEASE</version>
</dependency>

<!-- Log Backend depending on your logging configuration-->
<dependency>
    <groupId>io.tracee.backend</groupId>
    <artifactId>tracee-slf4j</artifactId>
    <version>RELEASE</version>
</dependency>
    
<!-- Binds context logging -->
<dependency>
    <groupId>io.tracee.contextlogger.contextprovider</groupId>
    <artifactId>contextprovider-javaee</artifactId>
    <version>RELEASE</version>
</dependency>
```

## Using EJB/CDI Interceptors 

You can use the context logger by annotating the ejb method you want to be handled.

```java
@Stateless
public class TestEjbImpl implements TestEjb {

    // ...

    @Interceptors({ TraceeErrorContextLoggingInterceptor.class })
    public int multiply(int a, int b) {
        // ...
    }
    
    // ...
}
```

## Using JMS message listener

You can use the context logger by annotating your jms message listener class you want to be handled.

```java
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.javaee.Topic"),
		@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "exampleTopic") })
@Interceptors(TraceeJmsErrorMessageListener.class)
public class MessageTopicListener implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(MessageTopicListener.class);

	@Override
	public void onMessage(Message message) {
		LOG.info("I just received the message \"{}\" on javaee/exampleTopic", message);
	}
}
```


