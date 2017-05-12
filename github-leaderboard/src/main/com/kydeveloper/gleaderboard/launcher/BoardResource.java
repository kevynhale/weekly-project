package com.kydeveloper.gleaderboard.launcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.NonNull;

import com.google.inject.Inject;

@Path("/leaderboard")
public class BoardResource
{
  @NonNull
  public final GithubMachine githubMachine;

  @Inject
  public BoardResource(
      final GithubMachine githubMachine)
  {
    this.githubMachine = githubMachine;
  }
  
  @GET
  @Produces("text/plain")
  @Path("/org/{name}")
  public String getOrg(
      @PathParam("name") final String name) throws Exception
  {
    return githubMachine.getOrg(name).getData();
  }

  @GET
  @Produces("text/plain")
  @Path("/org/{name}/adduser/{username}")
  public String addUser(
      @PathParam("name") final String name,
      @PathParam("username") final String username) throws Exception
  {
    githubMachine.getOrg(name).addUser(username);
    return username;
  }

}