> This document contains documentation for the contextprovider-javaee module. Click [here](/README.md) to get an overview about the TracEE Context-Logger.

# contextprovider-javaee

> The TracEE contextprovider-javaee project offers support for acquiring contextual information in environments that are based on JMS and  EJB / CDI. Contextual information will be provided, if an exception is thrown during the execution of the invoked bean. 

Therefore the contextprovider-javaee module provides interceptors and message listeners for gathering contextual information during ejb/cdi calls and jms message processing.

## Example output
Depending on the selected context logger profile the output of the ejb interceptor consists of method invocation parameters, exception data and jms message related data in case of JMS.

    TODO

## Maven artifacts
You need to add the following Dependencies to your projects pom.xml:

```xml
<!-- Binds the core  -->
<dependency>
    <groupId>io.tracee.contextlogger</groupId>
    <artifactId>tracee-core</artifactId>
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


