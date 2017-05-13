package com.kydeveloper.gleaderboard.launcher;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.NonNull;

import com.google.inject.Inject;
import com.kydeveloper.gleaderboard.api.OrderType;
import com.kydeveloper.gleaderboard.api.OrgUsersResponse;

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
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/org/{name}")
  public OrgUsersResponse getOrg(
      @PathParam("name") final String name,
      @DefaultValue("todayCommits")
      @QueryParam("order_by") final String orderBy,
      @DefaultValue("DESC")
      @QueryParam("order") final String order) throws Exception
  {
    final GithubOrganization org = githubMachine.getOrg(name);

    return OrgUsersResponse.builder()
        .organizationName(org.getOrgName())
        .totalUsers(org.getUsers().size())
        .pageNumber(0)
        .userFilterString("")
        .users(org.getUsers(orderBy, order))
        .usersPerPage(20)
        .orderBy(orderBy)
        .order(OrderType.valueOf(order))
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/org/{name}/adduser/{username}")
  public String addUser(
      @PathParam("name") final String name,
      @PathParam("username") final String username) throws Exception
  {
    githubMachine.getOrg(name).addUser(username);
    return username;
  }

}