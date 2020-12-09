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

import org.json.simple.JSONObject;

@Path("/WordWritter")
public class WordChange {

	@GET
	@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response itsWorking()
	{
		JSONObject resp = new JSONObject();
		resp.put("code", "200");
		resp.put("message", "Word webservice is working v 2.1");
		
		return Response.ok().entity(resp).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response escreveWord(JsonStructure json)
	{
		JSONObject alterarword = new ChangeRole().changeWordContent(json);
		return Response.ok().entity(alterarword).build();
	}
}
