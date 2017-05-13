package com.kydeveloper.gleaderboard.github;

import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.kydeveloper.gleaderboard.api.UserResponse;

public class GithubMachine
{

  private final HashMap<String, GithubOrganization> organizations;
  
  private final Cache<String, CommitFields> userCache;
  
  private final HashMap<String, Comparator> userSortMethod;

  @NonNull
  private final GithubScraper scraper;

  public GithubMachine()
  {
    organizations = new HashMap<String, GithubOrganization>();
    this.scraper = new GithubScraper();
    this.userSortMethod = new HashMap<String, Comparator>();
    this.setupUserMethod();

    final RemovalListener<String, CommitFields> removalListener =
        new RemovalListener<String, CommitFields>()
        {
          @Override
          public void onRemoval(final RemovalNotification<String, CommitFields> not)
          {

            not.getValue().getUser().getCommitData();
          }
        };

    this.userCache = CacheBuilder.newBuilder()
        .expireAfterWrite(900, TimeUnit.SECONDS)
        .maximumSize(10000)
        .removalListener(removalListener)
        .build(new UserCacheLoader());
  }

  public GithubOrganization getOrg(final String name)
  {
    if (!organizations.containsKey(name))
    {
      organizations.put(
          name,
          GithubOrganization.builder()
              .orgName(name)
              .scraper(scraper)
              .userCache(userCache)
              .userSortMethod(userSortMethod)
              .build());
    }
    return organizations.get(name);
  }
  
  public void setupUserMethod()
  {
    userSortMethod.put("todayCommits", Comparator.comparing(UserResponse::getTodayCommits));
    userSortMethod.put("yearCommits", Comparator.comparing(UserResponse::getYearCommits));
    userSortMethod.put("followers", Comparator.comparing(UserResponse::getFollowers));
    userSortMethod.put("fullname", Comparator.comparing(UserResponse::getFullname));
    
  }
}
