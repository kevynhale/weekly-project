package com.kydeveloper.gleaderboard.launcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GithubScraper
{
  public GithubScraper()
  {

  }

  public String parse(final String name) throws Exception
  {
    final Document doc = Jsoup.connect("http://github.com/" + name).get();
    final String count = getTodaysCount(doc);
    return count;
  }

  private String getOrganization(final Document doc)
  {
    final Elements org = doc.getElementsByAttributeValue(
        "aria-label", "Organization");

    final Document doc2 = Jsoup.parse(org.html());
    return doc2.getElementsByTag("div").html();
  }

  private String getTodaysCount(final Document doc)
  {
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
    final ArrayList<String> matches = new ArrayList<String>();
    while (m.find())
    {
      final String match = m.group(1);
      matches.add(match);
    }
    // Match 6 provides the count
    return matches.get(6).toString();

  }
}