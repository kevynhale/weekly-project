package com.kydeveloper.gleaderboard.launcher;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import com.google.common.cache.Cache;


public class GithubUser
{

  @NonNull
  @Getter
  private final String username;

  @Getter
  private final String orgName;

  @Getter
  private final String avatar;

  private final Cache<String, CommitFields> userCache;

  @NonNull
  private final GithubScraper scraper;
  
  @Builder
  public GithubUser(
      final String username,
      final Cache<String, CommitFields> userCache,
      final GithubScraper scraper,
      final String orgName
)
  {
    this.username = username;
    this.userCache = userCache;
    this.scraper = scraper;
    this.orgName = orgName;

    this.avatar = null;

    // Set up the Commit Cache on initialization of user
    this.getCommitData();
    this.scraper.getUserData(username);
    
  }

  public CommitFields getCommitData()
  {
    if (!userCache.asMap().containsKey(username))
    {

      try
      {
        final Integer daily = Integer.parseInt(scraper.getTodaysCount(username));
        final Integer yearly = Integer.parseInt(scraper.getYearCount(username));
        System.out.println("Updating cache for " + username);
        userCache.asMap().put(username,
            CommitFields.builder()
                .daily(daily)
                .yearly(yearly)
                .user(this)
                .build());
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
