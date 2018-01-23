package dao;

import models.Post;

import java.util.ArrayList;
import java.util.List;


public interface TeamDao {

  //create
  void add (Post post);
  //read
  List<Post> getAll();

  List<Post> getAllTeamsByState(int stateId);

  Post findById(int id);
  //update
  void update(int id, String team, String members, String password, int stateId);
  //delete
  void deleteById(int id);
  void clearAllPosts();

}
