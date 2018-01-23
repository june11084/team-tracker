package dao;

import models.Team;

import java.util.List;


public interface TeamDao {

  //create
  void add (Team post);
  //read
  List<Team> getAll();

  List<Team> getAllTeamsByState(int stateId);

  Team findById(int id);
  //update
  void update(int id, String team, String members);
  //delete
  void deleteById(int id);
  void deleteAllById(int id);
  void clearAllPosts();

}
