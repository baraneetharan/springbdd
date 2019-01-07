package com.kgisl.springbdd.controller;

import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;
import java.util.List;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.kgisl.springbdd.SpringbddApplication;
import com.kgisl.springbdd.entity.Team;
import com.kgisl.springbdd.repository.TeamRepository;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.RestAssured.given;

/**
 * TeamControllerIT
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")

public class TeamControllerIT {
    private static final String EVENT_RESOURCE1 = "api/teams/";
    private static final String NAME_FIELD = "teamname";
    private static final String name = "mahesh";
    public Team team1 = new TeamBuilder().id(1L).name("mahesh").build();
    public Team team2 = new TeamBuilder().id(1L).name("mahesh").build();
    @Value("${local.server.port}")
    private int serverPort;
    private Team team;

    @Autowired
    private TeamRepository teamRepository;

    @Before
    public void setUp() {
        teamRepository.deleteAll();
        team = teamRepository.save(team1);
        RestAssured.port = serverPort;
    }

    @Test
    public void addItemShouldReturnSavedItem() {
        List<Team> port = new ArrayList<Team>();
        port.add(team2);
        given().body(EVENT_RESOURCE1).contentType(ContentType.JSON).when().post(EVENT_RESOURCE1).then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void allIT() {
        when().get(EVENT_RESOURCE1).then().body(NAME_FIELD, hasItem(name));
        System.out.println("Get all method executed");
    }
}