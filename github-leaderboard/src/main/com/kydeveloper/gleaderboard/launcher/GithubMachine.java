package com.kydeveloper.gleaderboard.launcher;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GithubMachine
{

  private final HashMap<String, GithubOrganization> organizations;
  
  private final Cache<String, Integer> userCache;

  @NonNull
  private final GithubScraper scraper;

  public GithubMachine()
  {
    organizations = new HashMap<String, GithubOrganization>();
    this.scraper = new GithubScraper();
    this.userCache = CacheBuilder.newBuilder()
        .expireAfterWrite(300, TimeUnit.SECONDS)
        .maximumSize(10000)
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
