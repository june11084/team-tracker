
package dao;

import models.State;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oStateDao implements StateDao {

  private final Sql2o sql2o;

  public Sql2oStateDao(Sql2o sql2o){
    this.sql2o = sql2o;
  }

  @Override
  public void add(State state) {
    String sql = "INSERT INTO states (name) VALUES (:name)";
    try(Connection con = sql2o.open()){
      int id = (int) con.createQuery(sql)
              .bind(state)
              .executeUpdate()
              .getKey();
      state.setId(id);
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }

  @Override
  public List<State> getAll() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM states")
              .executeAndFetch(State.class);
    }
  }

  @Override
  public State findById(int id) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM states WHERE id = :id")
              .addParameter("id", id)
              .executeAndFetchFirst(State.class);
    }
  }

  @Override
  public void update(int id, String newName){
    String sql = "UPDATE states SET name = :name WHERE id=:id";
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
              .addParameter("name", newName)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void deleteById(int id) {
    String sql = "DELETE from states WHERE id=:id"; //raw sql
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
              .addParameter("id", id)
              .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public void clearAllStates() {
    String sql = "DELETE from states"; //raw sql
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
              .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

}

