package dao;

import models.State;

import java.util.List;

public interface StateDao {

  //create
  void add (State state);

  //read
  List<State> getAll();

  State findById(int id);

  //update
  void update(int id, String name);

  //delete
  void deleteById(int id);
  void clearAllStates();

}