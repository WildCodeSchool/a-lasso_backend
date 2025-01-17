package com.back_alasso.config;

import com.back_alasso.demo.DemoEntity;
import com.back_alasso.demo.DemoRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

  private final DemoRepository demoRepository;

  public DatabaseInitializer(DemoRepository demoRepository) {
    this.demoRepository = demoRepository;
  }

  @Bean
  CommandLineRunner init() {
    return args -> {
      List.of(new DemoEntity("Hello"), new DemoEntity("Bonjour"), new DemoEntity("Sabaidi"), new DemoEntity("Ia ora na")).forEach(
        demoRepository::save
      );
    };
  }
}
