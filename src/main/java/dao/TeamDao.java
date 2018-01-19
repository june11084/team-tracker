package dao;

import models.Post;

import java.util.ArrayList;
import java.util.List;


public interface TeamDao {

  //create
  void add (Post task);
  //read
  List<Post> getAll();

  Post findById(int id);
  //update
  void update(int id, String team, ArrayList<String> members, String password, int stateId);
  //delete
  void deleteById(int id);
  void clearAllTasks();

}
