package com.kydeveloper.gleaderboard.launcher;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;

public class GleaderboardMain
{
  public static void main(final String[] args) throws Exception
  {
    final Server server = new Server(8050);
    final ServletContextHandler root =
        new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

    root.addEventListener(new SimpleConfig());
    root.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    root.addServlet(EmptyServlet.class, "/*");

    server.start();
  }
}
