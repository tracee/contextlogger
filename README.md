# TracEE Context-Logger

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.tracee.contextlogger/contextlogger-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.tracee.contextlogger/contextlogger-parent)
[![Build Status](https://api.travis-ci.org/tracee/contextlogger.svg)](https://travis-ci.org/tracee/contextlogger)
[![Coverage Status](https://coveralls.io/repos/tracee/contextlogger/badge.svg?branch=master&service=github)](https://coveralls.io/github/tracee/contextlogger?branch=master)

> The TracEE Context-Logger is a java framework for providing contextual information in log files (or other kind of targets - for example by sending the data via HTTP). The contextual information is provided in a human and machine readable format (JSON).

It does the following things:
- offers handlers for most popular java frameworks that are used to detect errors (uncaught exceptions) automatically  and to provide information about the invocation context that lead to the error.
- offers a fluent API that is helping you to create human and machine readable string representations for any kind of instances. This is very handy if you want to provide complex contextual information in manual log statements. 
- offers a plugin mechanism that allows you to use customized output builders for specific types
- offers a plugin mechanism to allow writing of contextual information to other kinds of targets than log files (for example: to send invocation context via HTTP to another server)
- offers predefined and customized profiles to configure the output
- offers a static output format that eases automatic detection of errors ( no more customer driven bug detection ;) ). 
- integration is very easy with low impact on production code
- uses slf4j as a log abstraction. Therefore most log frameworks are supported.
 
At the moment the following Java technologies / frameworks are supported:

* servlet 2.5+
* jax-ws
* jax-rs2
* cdi / ejb
* jms
* AspectJ / Spring-AOP
* Spring


# Motivation
Think about getting a call from a customer who informs you about an error, that has happened in your application.
In most cases the only information you get is the name of the user, the time of the error and in which part of the application the error has occurred.
All other information must be gathered from the log files of the applications servers or the connected databases.
Success and speed of analyzing the error hardly depends on what has been written to the logs. 
Most of the useful output is provided manually by log statements implemented by the developer or in worst case not even at all.
And databases aren't a reliable source of information, because data could have changed since the error has occurred.

That is where the TracEE Context Logger is going to help you. It automatically detects errors and writes the invocation context to the logs. It offers handlers for the most commonly used Java Frameworks (Java EE, Spring and others).
For example it is possible to output requests parameters in Servlet container or to output soap request of JAX-WS webservices.

# Example
The following output was generated by the tracee-examples applications after an uncaught exception in a JAX-WS service was thrown. The output was written by a JAX-WS service handler and a servlet filter provided by the TracEE Context-Logger. 

	// JAX-WS SERVICE - BACKEND APPLICATION
	13:51:36.929 [http-bio-9080-exec-5] ERROR i.t.c.connector.LogConnector - [08f0d3700af5309b0ee3d140dd740cf4|6P22I7Z28VWEFMBV6U1HH5GITO8PTZ3M] - TRACEE_CL_ERROR[JAX-WS]  : [
	  "TYPE:Object[]",
	  {
		"TYPE":"jaxWs",
		"soapResponse":"String['<soap:Envelope xmlns:soap=\"http:\/\/schemas.xmlsoap.org\/soap\/envelope\/\"><SOAP-ENV:Header xmlns:SOAP-ENV=\"http:\/\/schemas.xmlsoap.org\/soap\/envelope\/\"\/><soap:Body><soap:Fault><faultcode>soap:Server<\/faultcode><faultstring>JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].<\/faultstring><\/soap:Fault><\/soap:Body><\/soap:Envelope>']",
		"soapRequest":"String['<soap:Envelope xmlns:soap=\"http:\/\/schemas.xmlsoap.org\/soap\/envelope\/\"><SOAP-ENV:Header xmlns:SOAP-ENV=\"http:\/\/schemas.xmlsoap.org\/soap\/envelope\/\"><TPIC xmlns=\"http:\/\/tracee.io\/tpic\/1.0\"><entry key=\"TPIC.invocationId\">6P22I7Z28VWEFMBV6U1HH5GITO8PTZ3M<\/entry><entry key=\"TPIC.sessionId\">08f0d3700af5309b0ee3d140dd740cf4<\/entry><\/TPIC><\/SOAP-ENV:Header><soap:Body><ns2:error xmlns:ns2=\"https:\/\/github.com\/tracee\/tracee\/examples\/jaxws\/service\/wsdl\"><arg0>3121<\/arg0><arg1>0<\/arg1><\/ns2:error><\/soap:Body><\/soap:Envelope>']"
	  }
	]
	
	// SERVLET BASED FRONTEND APPLICATION
	13:51:37.368 [http-bio-9080-exec-3] ERROR i.t.c.connector.LogConnector - [08f0d3700af5309b0ee3d140dd740cf4|6P22I7Z28VWEFMBV6U1HH5GITO8PTZ3M] - TRACEE_CL_ERROR[TraceeErrorLoggingFilter]  : [
	  "TYPE:Object[]",
	  {
		"TYPE":"servletRequest",
		"http-method":"String['POST']",
		"http-parameters":[
		  "TYPE:ArrayList",
		  {
			"name":"String['j_idt13']",
			"value":"String['j_idt13']"
		  },
		  {
			"name":"String['j_idt13:j_idt23']",
			"value":"String['3121']"
		  },
		  {
			"name":"String['j_idt13:j_idt25']",
			"value":"String[]"
		  },
		  {
			"name":"String['j_idt13:j_idt43']",
			"value":"String['Trigger uncaught Exception']"
		  },
		  {
			"name":"String['javax.faces.ViewState']",
			"value":"String['-7205603806442056524:-6891029251268203353']"
		  }
		],
		"url":"String['http:\/\/localhost:9080\/traceeTestWebapp\/index.jsf']"
	  },
	  {
		"TYPE":"servletResponse",
		"http-status-code":"Integer['500']"
	  },
	  {
		"TYPE":"throwable",
		"stacktrace":"String['javax.servlet.ServletException: javax.xml.ws.soap.SOAPFaultException: JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].\r\n\tat javax.faces.webapp.FacesServlet.service(FacesServlet.java:606)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:303)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n\tat io.tracee.binding.servlet.TraceeFilter.doFilterHttp(TraceeFilter.java:63)\r\n\tat io.tracee.binding.servlet.TraceeFilter.doFilter(TraceeFilter.java:48)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n\tat io.tracee.contextlogger.contextprovider.servlet.TraceeErrorLoggingFilter.doFilter(TraceeErrorLoggingFilter.java:30)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n\tat io.tracee.examples.webapp.TraceeExampleFilter.doFilter(TraceeExampleFilter.java:27)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\r\n\tat org.apache.tomee.catalina.OpenEJBValve.invoke(OpenEJBValve.java:44)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:103)\r\n\tat org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\r\n\tat org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1070)\r\n\tat org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:611)\r\n\tat org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:316)\r\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)\r\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:745)\r\nCaused by: javax.faces.el.EvaluationException: javax.xml.ws.soap.SOAPFaultException: JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].\r\n\tat javax.faces.component.MethodBindingMethodExpressionAdapter.invoke(MethodBindingMethodExpressionAdapter.java:102)\r\n\tat com.sun.faces.application.ActionListenerImpl.processAction(ActionListenerImpl.java:102)\r\n\tat javax.faces.component.UICommand.broadcast(UICommand.java:315)\r\n\tat javax.faces.component.UIViewRoot.broadcastEvents(UIViewRoot.java:794)\r\n\tat javax.faces.component.UIViewRoot.processApplication(UIViewRoot.java:1259)\r\n\tat com.sun.faces.lifecycle.InvokeApplicationPhase.execute(InvokeApplicationPhase.java:81)\r\n\tat com.sun.faces.lifecycle.Phase.doPhase(Phase.java:101)\r\n\tat com.sun.faces.lifecycle.LifecycleImpl.execute(LifecycleImpl.java:118)\r\n\tat javax.faces.webapp.FacesServlet.service(FacesServlet.java:593)\r\n\t... 31 more\r\nCaused by: javax.xml.ws.soap.SOAPFaultException: JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].\r\n\tat org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:158)\r\n\tat com.sun.proxy.$Proxy224.error(Unknown Source)\r\n\tat io.tracee.examples.webapp.TestWebappController.triggerJaxWsRemoteError(TestWebappController.java:80)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:497)\r\n\tat org.apache.el.parser.AstValue.invoke(AstValue.java:278)\r\n\tat org.apache.el.MethodExpressionImpl.invoke(MethodExpressionImpl.java:273)\r\n\tat com.sun.faces.facelets.el.TagMethodExpression.invoke(TagMethodExpression.java:105)\r\n\tat javax.faces.component.MethodBindingMethodExpressionAdapter.invoke(MethodBindingMethodExpressionAdapter.java:88)\r\n\t... 39 more\r\nCaused by: org.apache.cxf.binding.soap.SoapFault: JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].\r\n\tat org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor.unmarshalFault(Soap11FaultInInterceptor.java:84)\r\n\tat org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor.handleMessage(Soap11FaultInInterceptor.java:51)\r\n\tat org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor.handleMessage(Soap11FaultInInterceptor.java:40)\r\n\tat org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:263)\r\n\tat org.apache.cxf.interceptor.AbstractFaultChainInitiatorObserver.onMessage(AbstractFaultChainInitiatorObserver.java:113)\r\n\tat org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor.handleMessage(SOAPHandlerInterceptor.java:140)\r\n\tat org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor.handleMessage(SOAPHandlerInterceptor.java:71)\r\n\tat org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:263)\r\n\tat org.apache.cxf.endpoint.ClientImpl.onMessage(ClientImpl.java:845)\r\n\tat org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1705)\r\n\tat org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1538)\r\n\tat org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1445)\r\n\tat org.apache.cxf.transport.AbstractConduit.close(AbstractConduit.java:56)\r\n\tat org.apache.cxf.transport.http.HTTPConduit.close(HTTPConduit.java:660)\r\n\tat org.apache.cxf.interceptor.MessageSenderInterceptor$MessageSenderEndingInterceptor.handleMessage(MessageSenderInterceptor.java:62)\r\n\tat org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:263)\r\n\tat org.apache.cxf.endpoint.ClientImpl.doInvoke(ClientImpl.java:570)\r\n\tat org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:479)\r\n\tat org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:382)\r\n\tat org.apache.cxf.endpoint.ClientImpl.invoke(ClientImpl.java:335)\r\n\tat org.apache.cxf.frontend.ClientProxy.invokeSync(ClientProxy.java:96)\r\n\tat org.apache.cxf.jaxws.JaxWsClientProxy.invoke(JaxWsClientProxy.java:136)\r\n\t... 49 more\r\n']",
		"message":"String['javax.xml.ws.soap.SOAPFaultException: JAXWS Tracee Example : Triggered exception with passed parameters '3121' and '0' while invoking public int io.tracee.examples.jaxws.service.TraceeJaxWsTestService.error(int,int) with params [3121, 0].']"
	  }
	]

The example consists of two log statements generated by the context logger of two related applications (Servlet based frontend and JAX-WS service). The Log statements were aggregated by using the TracEE Framework.

Both statements will help you to analyze the error and to determine if the reason of the error is located in the frontend or the JAX-WS service.

The output can be configured easily via predefined or customized profiles.

# Integrating TracEE Context-Logger into your application 

There are two possibilties on how to use the TracEE Context-Logger:
* manually driven : by using the fluent API to generate string representations for instances
* automatically driven : by using handlers and inteceptors provided by the technolgy dependent context provider artifacts

Basically you just have to add the following dependency to your pom.xml to get started

        <dependency>
            <groupId>io.tracee.contextlogger</groupId>
            <artifactId>contextlogger-core</artifactId>
        </dependency>

Additionally - depending on your technolgy stack - you have to add some context provider artifacts. Please take a look at the modules section for further information.

## Write log statements manually by using the Fluent API
String representaions of contextual information can be added manually to the logs by using the Fluent API.
Building of the string representation is done via reflection. 
Multiple referenced instances and loops are detected and handled gently.

The TraceeToStringBuilder is very easy to use:

	// generates a string representation for passed instance using default profiles
	TraceeToStringBuilder.createDefault().toString(instance1,instance2,...);
	
	// Prepares preconfigured TraceeToStringBuilder instance. Note: output will be generated on first toString() method call. Should be used with log frameworks.
	TraceeToStringBuilder.createDefault().wrap(instance1,instance2,...);
	
	// overrides default config
	TraceeToStringBuilder.create()
		// override output style
		.enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INTENDED)  
		// override Tracee context provider profile settings
		.enforceProfile(Profile.FULL)	
		// disabled processing of passed types
		.disableTypes(TypeX.class)
		// closes configuration
		.apply()
		.toString(instance1,instance2,...);
    		



## Detect errors and provide log statements automatically by using technology dependent context providers.

| Framework    | Adapter |
| ----------:  |:------:|
| Servlet      | Use [contextprovider-servlet](contextprovider/servlet) as a servlet filter. |
| EJB3/CDI/JMS | Use [contextprovider-javaee](contextprovider/javaee) as an interceptor |
| JAX-RS       | Use [contextprovider-servlet](contextprovider/servlet) as a servlet filter. |
| JAX-RS2      | TODO |
| JAX-WS       | Use [contextprovider-jaxws](contextprovider/jaxws) as a message listener. |
| SPRING-MVC   | Use [contextprovider-springmvc](contextprovider/springmvc) as a handler interceptor.
| SPRING-AOP   | Use [contextprovider-aspectj] (contextprovider/aspectj) by offering an aspect triggered by a simple java annotation|
| ASPECTJ      | Use [contextprovider-aspectj] (contextprovider/aspectj) by offering an aspect triggered by a simple java annotation|

### Automatic detection of errors
Automatic detection of errors in logfiles is a difficult task. A common approach is to search for known errors like NullPointerExceptions, TimeoutExceptions or other error types you have encountered in the past.  

The TracEE Context-Logger project is helping you to detect errors easily by using a fix log statement structure:

	<LOG STATEMENT HEADER PROVIDED BY LOG FRAMEWORK> TRACEE_CL_ERROR[<SOURCE>]  : <CONTEXUAL DATA> 

The TracEE Context-Logger uses a static log statement prefix. So it's easy to detect all errors just by searching for the log statement prefix string  "TRACEE_CL_ERROR" on your log server. 

## Modules

TracEE context logger is built highly modular. The modules you need depend on your application and the underlying frameworks and containers.
The following table describes all available TracEE-contextlogger modules and their usage scenarios.

| Module                                | Usage |
|--------------------------------------:|:-----:|
| [contextlogger-connectors](connectors)              | Provides support for writing contextual data to other target as log files (f.e. send error via Http) |
| [contextlogger-core](core)                          | The core of the context logger |
| [contextlogger-integration-test](integration-test)  | Does some integration test for custom data providers |
| [contextprovider-javaee](contextprovider/javaee)                      | Provides support for EJB / CDI /JMS by offering interceptors |
| [contextprovider-jaxws](contextprovider/jaxws)                        | Provides support for JAXWS via Message handlers. |
| [contextprovider-provider-api](contextprovider/api)          | Provides support for Servlets via ServletFilter |
| [contextprovider-servlet](contextprovider/servlet)                    | Provides support for Servlets via ServletFilter |
| [contextprovider-aspectj](contextprovider/aspectj)                  | AspectJ / Spring-AOP powered contextual logging |
| [contextprovider-springmvc](contextprovider/springmvc)                  | Spring-MVC powered contextual logging |

## Configuration

### Output Profile Selection
The output produced by the TracEE contextual logger can be configured very flexible by allowing you to choose between predefined and custom profiles or by offering you the possibility to overwrite profile settings by system properties.
You can choose between those profiles by setting the **io.tracee.contextlogger.profile** system property in your application server or by adding a **ProfileSelector.properties** file to your application. The property file must define the **io.tracee.contextlogger.profile** property. 
Possible values are: 

| Value    | Description |
| --------:|:-----------:|
| BASIC    | Default profile, provides most important and secure contextual information.  |
| ENHANCED | Provides additional contextual information not included in basic profile     |
| FULL     | Outputs almost everything available to the handlers - even security related data, therefore this profile shouldn't be used in production environments  |

You can combine those configurations in any way you want. The settings will be applied in a specific order. Application server wide profile selection will be overwritten by application based profile selection. If no profile selection is configured, basic profile will be used as default.

### TracEE common context information
You can add the following optional system properties to your application server configuration to configure the common context information:

| Property name | Description | Default |
| -------------:|:-----------:|:-------:|
| io.tracee.contextlogger.tracee-standard-stage | Defines the stage of the system - for example DEV, INT, TEST,... | won't be used if not set explicitly |
| io.tracee.contextlogger.tracee-standard-system | Defines the name of the system | system name will be determined automatically by calling InetAddress.getLocalHost().getHostName() |




