package com.kydeveloper.gleaderboard.github;

import java.io.IOException;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import com.google.common.cache.Cache;
import com.kydeveloper.gleaderboard.api.UserResponse;


public class GithubUser
{

  @NonNull
  @Getter
  private final String username;

  @Getter
  private final String orgName;

  @Getter
  @Setter
  private String avatar;

  @Getter
  @Setter
  private String fullname;

  @Getter
  @Setter
  private int followers;

  @Getter
  @Setter
  private String url;

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

    // Set up the Commit Cache on initialization of user
    this.getCommitData();
    this.updateGithubData();
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

  public void updateGithubData()
  {
    try
    {
      final UserAccessResponse resp = this.scraper.getUserData(username);
      this.avatar = resp.getAvatarUrl();
      this.followers = resp.getFollowers();
      this.fullname = resp.getName();
      this.url = resp.getHtmlUrl();
    }
    catch (final IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public UserResponse getResponse()
  {
    final CommitFields cf = getCommitData();
    return UserResponse.builder()
        .avatarUrl(getAvatar())
        .githubUrl(getUrl())
        .fullname(getFullname())
        .username(getUsername())
        .todayCommits(cf.getDaily())
        .yearCommits(cf.getYearly())
        .followers(getFollowers())
        .build();
  }

}
