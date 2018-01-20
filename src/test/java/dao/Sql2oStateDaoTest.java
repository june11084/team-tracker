package dao;

import models.State;
import models.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class Sql2oStateDaoTest {

  private Sql2oStateDao stateDao;
  private Sql2oTeamDao teamDao;
  private Connection conn;

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    stateDao = new Sql2oStateDao(sql2o);
    teamDao = new Sql2oTeamDao(sql2o);

    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingStatesSetsId() throws Exception {
    State state = setupNewState();
    int originalCategoryId = state.getId();
    stateDao.add(state);
    assertNotEquals(originalCategoryId, state.getId());
  }

  @Test
  public void existingStatesCanBeFoundById() throws Exception {
    State state = setupNewState();
    stateDao.add(state);
    State foundState = stateDao.findById(state.getId());
    assertEquals(state, foundState);
  }

  @Test
  public void addedStatesAreReturnedFromGetAll() throws Exception {
    State state = setupNewState();
    stateDao.add(state);
    assertEquals(1, stateDao.getAll().size());
  }

  @Test
  public void noStatesReturnsEmptyList() throws Exception {
    assertEquals(0, stateDao.getAll().size());
  }

  @Test
  public void updateChangesStateContent() throws Exception {
    String initialDescription = "Yardwork";
    State state = new State (initialDescription);
    stateDao.add(state);

    stateDao.update(state.getId(),"Cleaning");
    State updatedState = stateDao.findById(state.getId());
    assertNotEquals(initialDescription, updatedState.getName());
  }

  @Test
  public void deleteByIdDeletesCorrectState() throws Exception {
    State state = setupNewState();
    stateDao.add(state);
    stateDao.deleteById(state.getId());
    assertEquals(0, stateDao.getAll().size());
  }

  @Test
  public void clearAllClearsAll() throws Exception {
    State state = setupNewState();
    State otherState = new State("CT");
    stateDao.add(state);
    stateDao.add(otherState);
    int daoSize = stateDao.getAll().size();
    stateDao.clearAllStates();
    assertTrue(daoSize > 0 && daoSize > stateDao.getAll().size());
  }

  @Test
  public void getAllTasksByStateReturnsTasksCorrectly() throws Exception {
    State state = setupNewState();
    stateDao.add(state);
    int stateId = state.getId();
    Post newPost = new Post("team a ", "asdf",stateId);
    Post otherPost = new Post("team b ", "asdf",stateId);
    Post thirdPost = new Post("team c", "asdf",stateId);
    teamDao.add(newPost);
    teamDao.add(otherPost);

    assertTrue(stateDao.getAllTasksByState(stateId).size() == 2);
    assertFalse(stateDao.getAllTasksByState(stateId).contains(thirdPost));
  }

  public State setupNewState(){
    return new State("OR");
  }
}

