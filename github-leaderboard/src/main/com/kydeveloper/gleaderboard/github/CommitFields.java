package com.kydeveloper.gleaderboard.github;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommitFields
{
  private final GithubUser user;

  private final int daily;

  private final int yearly;

}
