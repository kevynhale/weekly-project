package com.kydeveloper.gleaderboard.launcher;

import lombok.Builder;
import lombok.NonNull;

import com.google.common.cache.Cache;


public class GithubUser
{

  @NonNull
  private final String username;

  private final String avatar;

  private final Cache<String, Integer> userCache;

  @NonNull
  private final GithubScraper scraper;
  
  @Builder
  public GithubUser(
      final String username,
      final Cache<String, Integer> userCache,
      final GithubScraper scraper
      )
  {
    this.username = username;
    this.userCache = userCache;
    this.scraper = scraper;

    this.avatar = null;
    
  }

  public Integer getTodaysCommit()
  {
    if (!userCache.asMap().containsKey(username))
    {

      try
      {
        final Integer count = Integer.parseInt(scraper.parse(username));
        userCache.asMap().put(username, count);
      }
      catch (final Exception e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return userCache.asMap().get(username);
  }

}
