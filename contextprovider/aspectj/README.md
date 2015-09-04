> This document contains documentation for the tracee contextprovider-aspectj module. Click [here](/README.md) to get an overview that TracEE is about.

# contextprovider-aspectj

> The TracEE contextprovider-aspectj project offers an AspectJ / spring-AOP aspect that will output method invocation related data if an exception is thrown during the method invocation. 

The watchdog aspect execution is triggered by the Watchdog annotation. Therefore the Watchdog annotation can be added to a method of a class or to a class to enable the watchdog aspect execution for all public methods of the class.

    // Example 1 : Set watchdog annotation at class level to use watchdog for all public methods of the class 
    @Watchdog
    public class ExampleClass1() {
    
        public void exampleMethod1(String name, Integer age, boolean active) {
            ...
        }
        
        // ...
        
        public void exampleMethodN(...) {
            ...
        }
    
    }
    
    // Example 2 : Set Watchdog annotations explicitly on methods of the class
    public class ExampleClass2() {
    
        @Watchdog
        public void exampleMethod1(...) {
            ...
        }
        
        // ...
        
        @Watchdog
        private void exampleMethodN(...) {
            ...
        }
    
    }
    
## Example output
Depending on the selected context logger profile the output consists of invoked method name, deserialized invocation parameters, deserialized target instance and thrown exception.

	2015-09-04 13:58:18,245 ERROR [AYGIYAWBNVI295OBIPXDH5HQO4Z25767] i.t.c.connector.LogConnector:[50] TRACEE_CL_ERROR[Watchdog]  : [
	  "TYPE:Object[]",
	  {
		"TYPE":"watchdog",
		"id":"String<'ExampleClass'>",
		"aspectj.proceedingJoinPoint":{
		  "TYPE":"proceedingJoinPoint",
		  "class":"String<'io.tracee.contextlogger.example.ExampleClass1'>",
		  "method":"String<'exampleMethod1'>",
		  "parameters":[
			"String<'test'>",
			"Integer<55>",
			"Boolean<false>"
		  ]
		}
	  }
	]  
     

## Maven artifacts
You have to add the following Maven dependencies to your project:

    
    <dependency>
        <groupId>io.tracee.contextlogger</groupId>
        <artifactId>contextlogger-core</artifactId>
        <version>${tracee.version}</version>
    </dependency>

    <dependency>
        <groupId>io.tracee.contextlogger.contextprovider</groupId>
        <artifactId>contextprovider-aspectj</artifactId>
        <version>${tracee.version}</version>
    </dependency>
    

   
    
## Configuring the Watchdog annotation
The behavior and output of the Watchdog aspect can be configured by defining the following annotation attributes:

| Attribute name | Type | Description | Default |
|:---------------|:-----|:------------|:--------|
| id | String | the id will be part of the contextual information output | empty String - will be ignored |
| suppressThrowsExceptions | boolean | Defines if exceptions defined in the throws block of the method signature should be ignored | false |
| isActive | boolean | Defines if the watchdog is active | true |


## Configuration of AOP frameworks
Currently the AspectJ and spring-AOP frameworks are supported by this project.

### Using AspectJ
You have to start your application server / application by using the following java options / agent to enable AspectJ runtime weaving.

    -Daj.weaving.verbose=true -javaagent:<path to your aspectj library home>\aspectjweaver.jar


### Using Spring-AOP
Using spring-AOP, aspects will only be applied at method invocations on spring proxy instances. Therefore the following spring-MVC example will not work, if you call the exampleMethod via this.

    @Controller
    public class Example {
    
        @RequestMapping(value = "/token", method = RequestMethod.GET)
        public void exampleMethodCalled (Model model) {
        
            // watchdog aspect of exampleMethod won't be executed because method is not called via a spring proxy
            this.exampleMethod(1,"a");
            
            // watchdog aspect of exampleMethod will be executed because method is called via a spring proxy
            ((Example) AopContext.currentProxy()).exampleMethod(1,"a");
        
        }
                
        @Watchdog
        public void exampleMethod (int a, String b) {
            // ...
        }
    
    }

#### Configuration of Spring-AOP

You must add the following to your dispatcher servlets configuration xml:

    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:mvc="http://www.springframework.org/schema/mvc"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd
            http://www.springframework.org/schema/aop
            http://www.springframework.org/schema/aop/spring-aop-4.0.xsd"
    >
    
        <!-- Enable AspectJ style of Spring AOP -->
        <aop:aspectj-autoproxy/>
        
        <!-- ...  -->
    
        <!-- Configure Aspect Beans, without this Aspects advices wont execute -->
        <bean id="watchdog" class="WatchdogAspect"/>
        
    </beans>
