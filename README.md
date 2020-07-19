# **Guide**

This REST API based project is built with spring boot version 2.3.1. Java Runtime Environment 8 is required to run the application as well as to build the application JDK8 is the minimum requirement.

The purpose of the project is fairly simple, there is only one API which serves response based on the filter criteria.

![Product GET API](docs/image/Product-Get-API.png)

The expected response is a JSON array with the products in a 'data' wrapper. 

Example: 
```
GET /product?type=subscription&max_price=1000&city=Stockholm
{
	data: [ 
		{
		    type: 'subscription',
		    properties: 'gb_limit:10',
		    price: '704.00',
		    store_address: 'Dana gï¿½rdet, Stockholm'
	  	},
	  	{
		    type: 'subscription',
		    properties: 'gb_limit:10',
		    price: '200.00',
		    store_address: 'Octavia grï¿½nden, Stockholm'
	  	}
	]
}
```

As persistence layer in memory H2 is being used and to manage persistence Spring Data JPA 2.2 is being used.

Request and Response has been validated by HandlerInterceptorAdapter and ResponseBodyAdvice available under validator package, response has been delivered with an immutable DTO.

Several auto converter has been introduced to map request parameter with Enum instance which can be found under helper package and the configuration is available at config package

To handle exception gracefully with ResponseEntityExceptionHandler 

The class diagram of the database entity is followed - 

![Product GET API](docs/image/Product-Entity-Class-Diagram.jpg)

To achieve polymorphic object query JPA inheritance singe table strategy has come in picture.

TTD approach is being following during the development, Including unit testing and integration testing 4 different types style is available at test folder.
> 1. Unit testing with mockito jupiter
> 2. Unit testing Spring Test Framework
> 3. JPA testing with @DataJpaTest
> 4. Integration testing with TestRestTemplate

**Build the application with Maven**

It's a maven based application, To build the application following command need to be run from command line.
~~~
mvn package
~~~
or
~~~
mvnw package
~~~
Again you will need to have minimum JDK8 available at you PATH variable. If you don't have JDK8 installed please follow the docker build section 2.

**Run the application**

~~~
java -jar target/springboot-assignment-telenor.jar
~~~

**Build the Docker image**

S1.

To build a docker image with the package, that has been generated at the previous step following command is necessary from command line.
~~~
docker build -t springboot-assignment-telenor .
~~~

S2.

At file DockerfileBuildWIthMavenImage is has been illustrated how to build the package from this project source code with maven docker image and then build the docker image. 
Its pretty helpful if no JDK is installed in the system. Only dependency is docker. Following is the command -
~~~
docker build -t springboot-assignment-telenor -f DockerfileBuildWIthMavenImage .
~~~

**Run the Docker image**

To run the newly created image command is give.  
~~~
docker run -p 8080:8080 springboot-assignment-telenor
~~~
NB: Need to make sure, the port 8080 is free.

