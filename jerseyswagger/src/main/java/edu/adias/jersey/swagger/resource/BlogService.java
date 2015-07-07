package edu.adias.jersey.swagger.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import edu.adias.jersey.swagger.model.Blog;

@Path("/blog")
@Produces({ "application/json", "application/xml" })
public class BlogService {
	
	@GET
	public Response getAllBlogs() {
		
		
		List<Blog> list=new ArrayList<Blog>();
		
		Blog b1=new Blog();
		b1.setId("100");
		b1.setTitle("JerseywithSwagger");
		b1.setCategory("java webservice rest");
		list.add(b1);
		Blog b2=new Blog();
		b1.setId("101");
		b1.setTitle("JerseywithSwagger1");
		b1.setCategory("java webservice rest1");
		list.add(b2);
		
		GenericEntity<List<Blog>> l=new GenericEntity<List<Blog>>(list){
			
		};
		
		 return Response.ok().entity(l).build(); 
	}

}
