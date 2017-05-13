package com.kydeveloper.gleaderboard.launcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

import com.google.common.cache.Cache;
import com.kydeveloper.gleaderboard.api.UserResponse;


public class GithubOrganization
{
  private final GithubScraper scraper;
  
  @Getter
  private final String orgName;
  
  @Getter
  private final HashMap<String, GithubUser> users;

  private final Cache<String, CommitFields> userCache;

  @Builder
  public GithubOrganization(
      final GithubScraper scraper,
      final String orgName,
      final Cache<String, CommitFields> userCache)
  {
    this.scraper = scraper;
    this.orgName = orgName;
    this.userCache = userCache;
    
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

  public List<UserResponse> getUsers()
  {
    return users.values().stream()
        .map(user -> {
          final CommitFields cf = user.getCommitData();
          return UserResponse.builder()
              .avatarUrl(user.getAvatar())
              .githubUrl(user.getUrl())
              .fullname(user.getFullname())
              .username(user.getUsername())
              .todayCommits(cf.getDaily())
              .yearCommits(cf.getYearly())
              .followers(user.getFollowers())
              .build();
        }).collect(Collectors.toList());
  }


}
