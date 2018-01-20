package dao;

import models.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2oTeamDaoTest {

  private Sql2oTeamDao teamDao; //ignore me for now
  private Connection conn; //must be sql2o class conn

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    teamDao = new Sql2oTeamDao(sql2o); //ignore me for now

    //keep connection open through entire test so it does not get erased.
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingPostSetsId() throws Exception {
    Post post = setupNewTask();
    int originalPostId = post.getId();
    teamDao.add(post);
    assertNotEquals(originalPostId, post.getId()); //how does this work?
  }

  @Test
  public void existingPostCanBeFoundById() throws Exception {
    Post post = new Post ("team ", "asdf",1);
    teamDao.add(post); //add to dao (takes care of saving)
    Post foundPost = teamDao.findById(post.getId()); //retrieve
    assertEquals(post.getId(), foundPost.getId()); //should be the same
  }

  @Test
  public void addedPostAreReturnedFromgetAll() throws Exception {
    Post post = setupNewTask();
    teamDao.add(post);
    assertEquals(1, teamDao.getAll().size());
  }

  @Test
  public void noPostReturnsEmptyList() throws Exception {
    assertEquals(0, teamDao.getAll().size());
  }

  @Test
  public void updateChangesPostContent() throws Exception {
    String team = "mow the lawn";
    Post post = new Post (team, "asdf",1);
    String members ="john,christine,Lee,adam,dave";
    List<String> membersList = new ArrayList<>(Arrays.asList(members.split(" , ")));
    post.updateMember((ArrayList) membersList);
    teamDao.add(post);

    teamDao.update(post.getId(),"brush the cat", (ArrayList) membersList,"asdf",1);
    Post updatedPost = teamDao.findById(post.getId()); //why do I need to refind this?
    assertNotEquals(team, updatedPost.getTeam());
  }

  @Test
  public void deleteByIdDeletesCorrectPost() throws Exception {
    Post post = setupNewTask();
    teamDao.add(post);
    teamDao.deleteById(post.getId());
    assertEquals(0, teamDao.getAll().size());
  }

  @Test
  public void clearAllClearsAll() throws Exception {
    Post post = setupNewTask();
    Post otherTask = new Post("team b", "asdf",1);
    teamDao.add(post);
    teamDao.add(otherTask);
    int daoSize = teamDao.getAll().size();
    teamDao.clearAllPosts();
    assertTrue(daoSize > 0 && daoSize > teamDao.getAll().size());
  }

  @Test
  public void StateIdIsReturnedCorrectly() throws Exception {
    Post post = setupNewTask();
    int originalStateId = post.getStateId();
    teamDao.add(post);
    assertEquals(originalStateId, teamDao.findById(post.getId()).getStateId());
  }


  public Post setupNewTask(){
    return new Post("team b","asdf",1);
  }

}
