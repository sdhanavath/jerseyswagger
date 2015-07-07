
You need to complete the four steps in order to set up your jersey application with Swagger:

1. Create a Maven based Jersey2.0 Java EE Web Application

	mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp 
		       	       -DarchetypeGroupId=org.glassfish.jersey.archetypes 
                               -DinteractiveMode=false -DgroupId=edu.adias.jersey.swagger.sample 
                               -DartifactId=jersey-swagger-sample 
                               -Dpackage=edu.adias.jersey.swagger.sample -DarchetypeVersion=2.0

  NOTE: Do the following test that the project is created and rest resource is accessible
        1/ mvn clean install
        2/ deploy the generated war on any java ee container
        3/ http://localhost:8080/jerseyswagger/
        
        Do add few plain(I mean without swagger) rest services into your application and  make sure that they work.


2. Add the swagger dependencies to your application

Ref: https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5

	<dependency>
  		<groupId>io.swagger</groupId>
  		<artifactId>swagger-jersey2-jaxrs</artifactId>
 		 <version>1.5.0</version>
	</dependency>
	
	The swagger-jersey2-jaxrs artifact pulls in a specific version of Jersey 2. 
	Maven's dependency management should resolve the version properly if you use an newer version, 
	however in some cases you may be required to manually exclude it
	
3. Hooking up Swagger-Core in your Application.

3.1. hook the swagger annotations into at your API definition so, that the swagger can generate the spec. for  the same
     for example:
     
     Before Swagger: StudentResource
     @Path("/student")
	@Produces({ "application/json", "application/xml" })
	public class StudentResource {
	static StudentData studentData = new StudentData();

	@GET
	@Path("/{id}")
	public Response getStudent(@PathParam("id") long id) {
		 Student student = studentData.findStudentById(id);
		    if (null != student) {
		      return Response.ok().entity(student).build();
		    } else {
		      throw new NotFoundException("Student not found");
		    }
		}
	}
     
     After Swagger:
     
     @Path("/swagger-student")
@Produces({ "application/json", "application/xml" })
@Api(value = "/swagger-student", description = "Operations about student")
public class SwaggerStudentResource {
	static StudentData studentData = new StudentData();

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Get student by name", response = Student.class, position = 0)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid student name supplied"),
			@ApiResponse(code = 404, message = "Student not found") })
	public Response getStudent(
			@ApiParam(value = "The name that needs to be fetched. Student student1 for testing. ", required = true) @PathParam("id") long id)
			throws ApiException {
		Student student = studentData.findStudentById(id);
		if (null != student) {
			return Response.ok().entity(student).build();
		} else {
			throw new NotFoundException(404, "Student not found");
		}
	}
}

3.2. Write/copy couple of classes from some example
     1/ Bootstrap.java
     2/ Extend few Exception classe: ApiException,BadRequestException and NotFoundException
     3/ Few Util filter classes: ApiAuthorizationFilterImpl,ApiOriginFilter

3.3.  Hook the servlet filters and Bootstrap servlet at web.xml as shown below, which helps in generating the swagger.json

<?xml version="1.0" encoding="UTF-8"?>
<!-- This web.xml file is not required when using Servlet 3.0 container,
     see implementation details http://jersey.java.net/nonav/documentation/latest/jax-rs.html -->
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <servlet>
        <servlet-name>jersey</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>
                io.swagger.jaxrs.listing,
                edu.adias.jersey.swagger.resource
            </param-value>
        </init-param>
        <init-param>
			<param-name>jersey.config.server.wadl.disableWadl</param-name>
			<param-value>true</param-value>
		</init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>jersey</servlet-name>
        <url-pattern>/api/*</url-pattern>
    </servlet-mapping>
    
    <servlet>
		<servlet-name>Jersey2Config</servlet-name>
		<servlet-class>io.swagger.jersey.config.JerseyJaxrsConfig</servlet-class>
		<init-param>
			<param-name>api.version</param-name>
			<param-value>1.0.0</param-value>
		</init-param>
		<init-param>
			<param-name>swagger.api.basepath</param-name>
			<param-value>http://localhost:8080/jerseyswagger/api</param-value>
		</init-param>
		<init-param>
			<param-name>swagger.filter</param-name>
			<param-value>edu.adias.jersey.swagger.util.ApiAuthorizationFilterImpl</param-value>
		</init-param>
		<load-on-startup>2</load-on-startup>
	</servlet>
	<filter>
		<filter-name>ApiOriginFilter</filter-name>
		<filter-class>edu.adias.jersey.swagger.util.ApiOriginFilter</filter-class>
	</filter>
	<servlet>
		<servlet-name>Bootstrap</servlet-name>
		<servlet-class>edu.adias.jersey.swagger.Bootstrap</servlet-class>
		<load-on-startup>2</load-on-startup>
	</servlet>
	<filter-mapping>
		<filter-name>ApiOriginFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
</web-app>

4. And you are done! try this http://localhost:8080/jerseyswagger/api/swagger.json 



