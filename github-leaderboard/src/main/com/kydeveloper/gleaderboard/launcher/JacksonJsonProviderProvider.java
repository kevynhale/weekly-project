package com.kydeveloper.gleaderboard.launcher;

import javax.inject.Inject;
import javax.inject.Provider;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJsonProviderProvider implements Provider<JacksonJsonProvider>
{
  private final ObjectMapper mapper;

  @Inject
  JacksonJsonProviderProvider(final ObjectMapper mapper)
  {
    this.mapper = mapper;
  }

  @Override
  public JacksonJsonProvider get()
  {
    return new JacksonJsonProvider(mapper);
  }
}