package com.kydeveloper.gleaderboard.launcher;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class GithubMachine
{

  private final HashMap<String, GithubOrganization> organizations;
  
  private final Cache<String, CommitFields> userCache;

  @NonNull
  private final GithubScraper scraper;

  public GithubMachine()
  {
    organizations = new HashMap<String, GithubOrganization>();
    this.scraper = new GithubScraper();

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
              .build());
    }
    return organizations.get(name);
  }
}
