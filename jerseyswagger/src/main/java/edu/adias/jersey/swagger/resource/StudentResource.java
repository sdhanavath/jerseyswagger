package edu.adias.jersey.swagger.resource;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import edu.adias.jersey.swagger.data.StudentData;
import edu.adias.jersey.swagger.model.Student;

/**
 * 
 * @author sdhanavath
 * 
 */
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
