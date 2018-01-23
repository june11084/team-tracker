package dao;

import models.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.ArrayList;
import java.util.List;

public class Sql2oTeamDao implements TeamDao { //implementing our interface

  private final Sql2o sql2o;

  public Sql2oTeamDao(Sql2o sql2o){
    this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
  }

  @Override
  public void add(Post team) {
    String sql = "INSERT INTO team (team, members, password, stateId) VALUES (:team, :members, :password, :stateId)"; //raw sql
    try(Connection con = sql2o.open()){ //try to open a connection
      int id = (int) con.createQuery(sql) //make a new variable
              .bind(team)
              .executeUpdate() //run it all
              .getKey(); //int id is now the row number (row “key”) of db
      team.setId(id); //update object to set id now from database
    } catch (Sql2oException ex) {
      System.out.println(ex); //oops we have an error!
    }
  }

  @Override
  public List<Post> getAll() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM team") //raw sql
              .executeAndFetch(Post.class); //fetch a list
    }
  }

  @Override
  public List<Post> getAllTeamsByState(int stateId) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM team WHERE stateId = :stateId")
              .addParameter("stateId", stateId)
              .executeAndFetch(Post.class);
    }
  }

  @Override
  public Post findById(int id) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM team WHERE id = :id")
              .addParameter("id", id) //key/value pair, key must match above
              .executeAndFetchFirst(Post.class); //fetch an individual item
    }
  }

  @Override
  public void update(int id, String team, String members){
    String sql = "UPDATE team SET (team, members) = (:team, :members) WHERE id=:id"; //raw sql
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
              .addParameter("team", team)
              .addParameter("members", members)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void deleteById(int id) {
    String sql = "DELETE from team WHERE id=:id";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public void clearAllPosts() {
    String sql = "DELETE from team";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql).executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }
}
