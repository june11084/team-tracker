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
  public void addingCourseSetsId() throws Exception {
    State state = setupNewCategory();
    int originalCategoryId = state.getId();
    stateDao.add(state);
    assertNotEquals(originalCategoryId, state.getId());
  }

  @Test
  public void existingCategorysCanBeFoundById() throws Exception {
    State state = setupNewCategory();
    stateDao.add(state);
    State foundState = stateDao.findById(state.getId());
    assertEquals(state, foundState);
  }

  @Test
  public void addedCategorysAreReturnedFromgetAll() throws Exception {
    State state = setupNewCategory();
    stateDao.add(state);
    assertEquals(1, stateDao.getAll().size());
  }

  @Test
  public void noCategorysReturnsEmptyList() throws Exception {
    assertEquals(0, stateDao.getAll().size());
  }

  @Test
  public void updateChangesCategoryContent() throws Exception {
    String initialDescription = "Yardwork";
    State state = new State (initialDescription);
    stateDao.add(state);

    stateDao.update(state.getId(),"Cleaning");
    State updatedState = stateDao.findById(state.getId());
    assertNotEquals(initialDescription, updatedState.getName());
  }

  @Test
  public void deleteByIdDeletesCorrectCategory() throws Exception {
    State state = setupNewCategory();
    stateDao.add(state);
    stateDao.deleteById(state.getId());
    assertEquals(0, stateDao.getAll().size());
  }

  @Test
  public void clearAllClearsAll() throws Exception {
    State state = setupNewCategory();
    State otherState = new State("CT");
    stateDao.add(state);
    stateDao.add(otherState);
    int daoSize = stateDao.getAll().size();
    stateDao.clearAllCategories();
    assertTrue(daoSize > 0 && daoSize > stateDao.getAll().size());
  }

  @Test
  public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
    State state = setupNewCategory();
    stateDao.add(state);
    int stateId = state.getId();
    Post newPost = new Post("team a ", "asdf",1);
    Post otherPost = new Post("team b ", "asdf",2);
    Post thirdPost = new Post("team c", "asdf",3);
    teamDao.add(newPost);
    teamDao.add(otherPost); //we are not adding task 3 so we can test things precisely.


    assertTrue(stateDao.getAllTasksByCategory(stateId).size() == 1);
    assertTrue(stateDao.getAllTasksByCategory(stateId).contains(newPost));
    assertTrue(stateDao.getAllTasksByCategory(stateId).contains(otherPost));
    assertFalse(stateDao.getAllTasksByCategory(stateId).contains(thirdPost)); //things are accurate!
  }

  public State setupNewCategory(){
    return new State("OR");
  }
}

