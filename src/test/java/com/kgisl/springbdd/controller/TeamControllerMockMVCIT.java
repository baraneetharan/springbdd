package com.kgisl.springbdd.controller;

import java.util.ArrayList;
import java.util.List;

import com.kgisl.springbdd.entity.Team;
import com.kgisl.springbdd.service.TeamService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
* TeamControllerMockMVCIT
*/
@RunWith(SpringRunner.class)
@WebMvcTest(value = TeamController.class, secure = false)
public class TeamControllerMockMVCIT {
   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private TeamService teamService;

   public Team team1 = new TeamBuilder().id(1L).name("Team 1").build();
   public Team team2 = new TeamBuilder().id(1L).name("Team 2").build();

   @Test
   public void getAll() throws Exception {


       List<Team> alist1 = new ArrayList<Team>();
       alist1.add(team1);
       given(teamService.getTeams()).willReturn(alist1);
       mockMvc.perform(get("/api/teams/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               .andExpect(content().json("[{'teamid': 1,'teamname': 'Team 1'}]"));

   }

   @Test
   public void postmapping() throws Exception {

       List<Team> alist1 = new ArrayList<Team>();
       alist1.add(team1);

       when(teamService.findByTeamId(team1.getTeamid())).thenReturn(team1);
       mockMvc.perform(
               post("/api/teams/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(team1)))
               .andExpect(status().isCreated());

       //verify(accountService, times(1)).find(acc.getAccountId());
       // verify(accountService, times(1)).save(acc);
       // verifyNoMoreInteractions(accountService);

   }

   @Test
   public void deleteByID() throws Exception {


       List<Team> alist = new ArrayList<Team>();
       alist.add(team1);

       when(teamService.findByTeamId(team1.getTeamid())).thenReturn(team1);

       mockMvc.perform(delete("/api/teams/1", team1.getTeamid())).andExpect(status().is2xxSuccessful());

       // verify(eventService, times(1)).find(currentevent.getId());
       // verify(teamService, times(1)).deleteTeamById(team1.getTeamid());
       // verifyNoMoreInteractions(teamService);
   }

   public static String asJsonString(final Object obj) {
       try {
           final ObjectMapper mapper = new ObjectMapper();
           return mapper.writeValueAsString(obj);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
}