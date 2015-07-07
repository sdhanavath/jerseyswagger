package edu.adias.jersey.swagger.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import edu.adias.jersey.swagger.data.StudentData;
import edu.adias.jersey.swagger.exception.ApiException;
import edu.adias.jersey.swagger.exception.NotFoundException;
import edu.adias.jersey.swagger.model.Student;

/**
 * 
 * @author sdhanavath
 * 
 */
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
