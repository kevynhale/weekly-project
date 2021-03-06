package com.kydeveloper.gleaderboard.github;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.google.common.cache.Cache;
import com.kydeveloper.gleaderboard.api.OrderType;
import com.kydeveloper.gleaderboard.api.UserResponse;


public class GithubOrganization
{
  private final GithubScraper scraper;
  
  @Getter
  private final String orgName;
  
  @Getter
  private final HashMap<String, GithubUser> users;

  private final Cache<String, CommitFields> userCache;

  private final HashMap<String, Comparator> userSortMethod;

  @Getter
  @Setter
  private String imageUrl;

  @Builder
  public GithubOrganization(
      final GithubScraper scraper,
      final String orgName,
      final Cache<String, CommitFields> userCache,
      final HashMap<String, Comparator> userSortMethod)
  {
    this.scraper = scraper;
    this.orgName = orgName;
    this.userCache = userCache;
    this.userSortMethod = userSortMethod;
    
    this.users = new HashMap<String, GithubUser>();
  }

  public void addUser(final String username)
  {
    if (!users.containsKey(username))
    {
      users.put(
          username,
          GithubUser.builder()
              .username(username)
              .orgName(orgName)
              .scraper(scraper)
              .userCache(userCache)
              .build());
    }
  }
  
  public String getData()
  {
    String data = "";
    for (final Entry<String, GithubUser> user : users.entrySet())
    {
      data =
          data + user.getKey() + ": " + user.getValue().getCommitData().getDaily() + ", "
              + user.getValue().getCommitData().getYearly() + ", ";
    }

    return data;
  }

  public List<UserResponse> getUsers(
      final String sortField,
      final String order,
      final String filter)
  {

    return users.values().stream()
        // Filter by both username and fullname
        .filter(user ->
            user.getUsername()
                .toLowerCase()
                .contains(filter.toLowerCase())

                ||

                user.getFullname()
                    .toLowerCase()
                    .contains(filter.toLowerCase()))

        .map(user -> user.getResponse())

        .sorted(OrderType.valueOf(order) == OrderType.DESC ?
            userSortMethod.get(sortField) : userSortMethod.get(sortField).reversed())

        .collect(Collectors.toList());
  }


}
