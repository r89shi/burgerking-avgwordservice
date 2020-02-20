package br.com.average.fluig.soft;
import br.com.average.fluig.json.JsonStructure;
import br.com.average.fluig.logic.ChangeRole;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/WordWritter")
public class WordChange {

	@GET
	@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response itsWorking()
	{
		return Response.ok().entity("Word webservice is working...").build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response escreveWord(JsonStructure json)
	{
		String alterarword = new ChangeRole().changeWordContent(json);
		return Response.ok().entity(alterarword).build();
	}
}
