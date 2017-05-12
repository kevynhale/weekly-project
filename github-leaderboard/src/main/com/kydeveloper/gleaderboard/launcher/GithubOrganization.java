package com.kydeveloper.gleaderboard.launcher;

import java.util.HashMap;
import java.util.Map.Entry;

import lombok.Builder;

import com.google.common.cache.Cache;


public class GithubOrganization
{
  private final GithubScraper scraper;
  
  private final String orgName;
  
  private final HashMap<String, GithubUser> users;

  private final Cache<String, Integer> userCache;

  @Builder
  public GithubOrganization(
      final GithubScraper scraper,
      final String orgName,
      final Cache<String, Integer> userCache)
  {
    this.scraper = scraper;
    this.orgName = orgName;
    this.userCache = userCache;
    
    this.users = new HashMap<String, GithubUser>();
  }

  public void addUser(final String username)
  {
    System.out.println(users.toString());
    if (!users.containsKey(username))
    {
      users.put(
          username,
          GithubUser.builder()
              .username(username)
              .scraper(scraper)
              .userCache(userCache)
              .build());
      System.out.println("Added User:" + username);
    }
    System.out.println("User Already Added:" + username);
    System.out.println(users.toString());
  }
  
  public String getData()
  {
    String data = "";
    for (final Entry<String, GithubUser> user : users.entrySet())
    {
      data = data + user.getKey() + ": " + user.getValue().getTodaysCommit() + ", ";
    }

    return data;
  }


}
