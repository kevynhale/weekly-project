package com.kydeveloper.gleaderboard.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Kevyn Hale
 */
@Getter
@Builder
@AllArgsConstructor
public class UserResponse
{

  private final String username;

  private final String fullname;

  private final String avatarUrl;

  private final String githubUrl;

  private final int followers;

  private final int yearCommits;

  private final int todayCommits;

}
