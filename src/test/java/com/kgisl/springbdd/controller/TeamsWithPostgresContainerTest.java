package com.kgisl.springbdd.controller;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import com.kgisl.springbdd.entity.Team;
import com.kgisl.springbdd.repository.TeamRepository;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(initializers = { TeamsWithPostgresContainerTest.Initializer.class })
public class TeamsWithPostgresContainerTest {

       public Team team1 = new TeamBuilder().id(1L).name("Team 1").build();
       public Team team2 = new TeamBuilder().id(1L).name("Team 2").build();

       @ClassRule
       public static PostgreSQLContainer postgreSQLContainer =
           (PostgreSQLContainer) new PostgreSQLContainer("postgres:10.4")
                   .withDatabaseName("sampledb")
                   .withUsername("sampleuser")
                   .withPassword("samplepwd")
                   .withStartupTimeout(Duration.ofSeconds(600));
       @Autowired
       private TeamRepository teamRepository;

       @Test
       public void testWithDb() {
               Team teamA  = new TeamBuilder().id(1L).name("Team 1").build();
               Team teamB = new TeamBuilder().id(1L).name("Team 2").build();

               Team team1 = teamRepository.save(teamA);
               Team team2 = teamRepository.save(teamB);

               // assertThat(team1).matches(c -> c.getTeamid() != null && c.getTeamname() == "Team 1");
               // assertThat(team2).matches(c -> c.getTeamid() != null && c.getTeamname() == "team2");
               assertThat(teamRepository.findAll()).contains(team1, team2);
       }

       static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
               public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
                       TestPropertyValues.of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                                       "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                                       "spring.datasource.password=" + postgreSQLContainer.getPassword())
                                       .applyTo(configurableApplicationContext.getEnvironment());
               }
       }
}