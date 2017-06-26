package com.kydeveloper.gleaderboard.rest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.NonNull;

import com.google.inject.Inject;
import com.kydeveloper.gleaderboard.api.OrderType;
import com.kydeveloper.gleaderboard.api.OrgResponse;
import com.kydeveloper.gleaderboard.api.OrgUsersResponse;
import com.kydeveloper.gleaderboard.api.UserResponse;
import com.kydeveloper.gleaderboard.github.GithubMachine;
import com.kydeveloper.gleaderboard.github.GithubOrganization;

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
  @Path("/orgs")
  public List<OrgResponse> getOrgs(
      @DefaultValue("") @QueryParam("filter") final String filter)
  {
    return githubMachine.getOrgs().stream()
        .filter(org -> org
            .getName()
            .toLowerCase()
            .contains(filter.toLowerCase()))
        .sorted(Comparator.comparing(OrgResponse::getName))
        .collect(Collectors.toList());
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/org/{name}")
  public OrgResponse getOrg(
      @PathParam("name") final String name)
  {
    final GithubOrganization org = githubMachine.getOrg(name);
    return OrgResponse.builder().name(org.getOrgName()).image(org.getImageUrl()).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/orgimage/{name}")
  public OrgResponse setOrgImage(
      @PathParam("name") final String name,
      @QueryParam("image") final String image)
  {
    final GithubOrganization org = githubMachine.getOrg(name);
    org.setImageUrl(image);
    return OrgResponse.builder().name(org.getOrgName()).image(org.getImageUrl()).build();
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/orgusers/{name}")
  public OrgUsersResponse getOrgUsers(
      @PathParam("name") final String name,
      @DefaultValue("todayCommits") @QueryParam("order_by") final String orderBy,
      @DefaultValue("DESC") @QueryParam("order") final String order,
      @DefaultValue("1") @QueryParam("page") final int page,
      @DefaultValue("20") @QueryParam("page_size") final int pageSize,
      @DefaultValue("") @QueryParam("filter") final String filter) throws Exception
  {
    final GithubOrganization org = githubMachine.getOrg(name);

    return OrgUsersResponse.builder()
        .organizationName(org.getOrgName())
        .totalUsers(org.getUsers().size())
        .pageNumber(page)
        .userFilterString("")
        .users(
            getPage(
                org.getUsers(orderBy, order, filter),
                pageSize,
                page))
        .usersPerPage(pageSize)
        .orderBy(orderBy)
        .order(OrderType.valueOf(order))
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/org/{name}/adduser/{username}")
  public UserResponse addUser(
      @PathParam("name") final String name,
      @PathParam("username") final String username) throws Exception
  {
    final GithubOrganization org = githubMachine.getOrg(name);
    org.addUser(username);
    return org.getUsers().get(username).getResponse();
  }

  private <T> List<T> getPage(final List<T> list, final int pageSize, final int page)
  {
    final int start = pageSize * (page - 1);
    final int end = start + pageSize;
    return list.subList(start == 0 ? 0 : start, list.size() < end ? list.size() : end);
  }

}