package com.kydeveloper.gleaderboard.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Kevyn Hale
 */
@Getter
@Builder
@AllArgsConstructor
public class OrgUsersResponse
{

  private final String organizationName;

  private final List<UserResponse> users;

  private final int totalUsers;

  private final int usersPerPage;

  private final int pageNumber;

  private final String userFilterString;

}
