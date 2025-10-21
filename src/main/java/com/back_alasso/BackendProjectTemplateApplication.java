package com.back_alasso;

import com.back_alasso.config.InitLogger;
import com.fasterxml.jackson.core.StreamReadConstraints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendProjectTemplateApplication {

  private final int ONE_HUNDRED_THOUSAND = 100_000_000;

  public static void main(String[] args) {
    System.setProperty("spring.jackson.parser.allow-unquoted-field-names", "true");
    ApplicationContext context = SpringApplication.run(BackendProjectTemplateApplication.class, args);
    InitLogger logger = new InitLogger();
    logger.logCurrentEnvironment(context);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    return builder ->
      builder.postConfigurer(mapper ->
        mapper.getFactory().setStreamReadConstraints(StreamReadConstraints.builder().maxStringLength(ONE_HUNDRED_THOUSAND).build())
      );
  }
}
