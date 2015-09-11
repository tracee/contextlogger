> This document contains documentation for the connector-http module. Click [here](/README.md) to get an overview that TracEE is about.

# connector-http

The TracEE connector-http supports writing contextual data to remote systems via HTTP(async). 

## Maven artifacts
You need to add the following Dependencies to your projects pom.xml:

```xml
<!-- Add this to enable the HTTP connector -->
<dependency>
    <groupId>io.tracee.contextlogger.connector</groupId>
    <artifactId>connector-http</artifactId>
    <version>RELEASE</version>
</dependency>
```

## Configuration of the HTTP connector
The HTTP connector can be configured by using system properties. 

| Property                                                 | Description | Default |
|---------------------------------------------------------:|:------|:-------|
| url                       | the url to call | |
| basicAuth.user            | user used for basic authentication                | not used |
| basicAuth.password        | password used for basic authentication            | not used |
| proxy.host                | proxy hostname to use                             | not used |
| proxy.port                | proxy port to use                                 | not used |
| proxy.user                | proxy user, if proxy requires authentication      | not used |
| proxy.password            | proxy password, if proxy requires authentication  | not used |
| request.timoutInMs        | the timeout in milliseconds                       | 10000 ms |


It's possible to configure multiple http connectors by changing the name of the in the property configuration prefix. 

### Configuration example - HTTP Connector
Here is a small example that configures two Http connectors:

	// example 1 : with basic authentication
    io.tracee.contextlogger.connector.httpConnector1.class=io.tracee.contextlogger.connector.http.HttpConnector
    io.tracee.contextlogger.connector.httpConnector1.url=http://localhost:8080/target
    io.tracee.contextlogger.connector.httpConnector1.basicAuth.user=user
    io.tracee.contextlogger.connector.httpConnector1.basicAuth.password=passwd
    
    // example 2 : without authentication
    io.tracee.contextlogger.connector.httpConnector2.class=io.tracee.contextlogger.connector.http.HttpConnector
    io.tracee.contextlogger.connector.httpConnector2.url=http://localhost:8090/anotherTarget 
