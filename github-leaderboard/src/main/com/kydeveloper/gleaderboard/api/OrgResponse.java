package com.kydeveloper.gleaderboard.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrgResponse
{
  public final String name;

  public final String image;
}
