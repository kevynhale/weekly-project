package com.kydeveloper.gleaderboard.github;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GithubScraper
{
  public GithubScraper()
  {

  }

  private Document getDoc(final String name) throws Exception
  {
    final Document doc = Jsoup.connect("http://github.com/" + name).get();
    return doc;
  }

  public String getTodaysCount(final String name) throws Exception
  {
    final Document doc = getDoc(name);
    final Date date = new Date();
    final SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
    final String today = sd.format(date).toString();

    final String svg = doc.getElementsByClass("js-calendar-graph-svg").first().html();
    
    final String ln =
        Arrays.stream(svg.split("\\r?\\n"))
            .filter(line -> line.contains(today))
            .findFirst()
            .get()
            .toString();

    final String re = "\"([^\"]*)\"";
    final Pattern p = Pattern.compile(re);
    final Matcher m = p.matcher(ln);
    final List<String> matches = new ArrayList<String>();
    while (m.find())
    {
      final String match = m.group(1);
      matches.add(match);
    }
    // Match 6 provides the count
    return matches.get(6).toString();

  }
  
  public String getYearCount(final String name) throws Exception
  {
    final Document doc = getDoc(name);
    final String yearString =
        doc.getElementsByClass("js-contribution-graph")
            .get(0)
            .getElementsByTag("h2").html();

    return Arrays.stream(yearString.split(" ")).findFirst().get().toString().replaceAll(",", "");
  }
  
  public UserAccessResponse getUserData(final String username) throws IOException
  {
    try (CloseableHttpClient httpClient = HttpClients.createDefault())
    {
      final String url = "https://api.github.com/users/" + username;
      final HttpGet request = new HttpGet(url);
      request.addHeader("content-type", "application/json");
      final ResponseHandler<UserAccessResponse> responseHandler =
          new ResponseHandler<UserAccessResponse>()
          {
            @Override
            public UserAccessResponse handleResponse(final HttpResponse response)
                throws IOException
            {
              final StatusLine statusLine = response.getStatusLine();
              final HttpEntity entity = response.getEntity();

              if (statusLine.getStatusCode() >= 300)
              {
                throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
              }

              if (entity == null)
              {
                throw new ClientProtocolException("Response contains no content");
              }

              final Gson gson = new GsonBuilder().create();
              return gson.fromJson(EntityUtils.toString(entity), UserAccessResponse.class);
            }
          };
      return httpClient.execute(request, responseHandler);
    }
  }
}