package core;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import core.filter.CORSFilter;

@Configuration
public class SpringConfig {

  @Bean
  public Filter corsFilter() {
    return new CORSFilter();
  }
}