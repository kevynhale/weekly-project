package com.kydeveloper.gleaderboard.launcher;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SimpleConfig extends GuiceServletContextListener
{
  @Override
  protected Injector getInjector()
  {
    return Guice.createInjector(new ServletModule()
    {
      @Override
      protected void configureServlets()
      {
        /* bind the REST resources */
        bind(BoardResource.class);
        bind(GithubMachine.class).toInstance(new GithubMachine());
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.feature.Trace",
            "true");
        serve("*").with(
            GuiceContainer.class,
            initParams);
      }
    });
  }
}