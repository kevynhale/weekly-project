package com.kydeveloper.gleaderboard.github;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class UserAccessResponse implements Serializable
{
  private String login;

  private int id;

  @SerializedName("avatar_url")
  private String avatarUrl;

  @SerializedName("gravatar_id")
  private String gravatarId;

  private String url;

  @SerializedName("html_url")
  private String htmlUrl;

  @SerializedName("followers_url")
  private String followersUrl;

  @SerializedName("following_url")
  private String followingUrl;

  @SerializedName("gists_url")
  private String gistsUrl;

  @SerializedName("starred_url")
  private String starredUrl;

  @SerializedName("subscriptions_url")
  private String subscriptionsUrl;

  @SerializedName("organizations_url")
  private String organizationsUrl;

  @SerializedName("repos_url")
  private String reposUrl;

  @SerializedName("events_url")
  private String eventsUrl;

  @SerializedName("received_events_url")
  private String receivedEventsUrl;

  private String type;

  @SerializedName("site_admin")
  private Boolean siteAdmin;

  private String name;

  private String company;

  private String blog;

  private String location;

  private String email;

  private Boolean hireable;

  private String bio;

  @SerializedName("public_repos")
  private int publicRepos;

  @SerializedName("public_gists")
  private int publicGists;

  private int followers;

  private int following;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("updated_at")
  private String updatedAt;

}
