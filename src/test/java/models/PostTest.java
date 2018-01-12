package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PostTest {

    @Test
    public void NewPostObjectGetsCorrectlyCreated_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals(true, post instanceof Post);
    }

    @Test
    public void Post_InstantiatesWithContent_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals("Day 1: Intro", post.getTeam());
    }

  @Test
  public void Post_getMember_toString() throws Exception {
    Post post = new Post("Day 1: Intro");
    String members ="john,christine,Lee,adam,dave";
    List<String> membersList = new ArrayList<>(Arrays.asList(members.split(" , ")));
    post.updateMember((ArrayList) membersList);
    assertEquals("john,christine,Lee,adam,dave", post.getMembers());
  }



//    @Test
//    public void updateChangesPostContent() throws Exception {
//        Post post = setupNewPost();
//        String formerContent = post.getContent();
//        LocalDateTime formerDate = post.getCreatedAt();
//        int formerId = post.getId();
//
//        post.update("Android: Day 40");
//
//        assertEquals(formerId, post.getId());
//        assertEquals(formerDate, post.getCreatedAt());
//        assertNotEquals(formerContent, post.getContent());
//    }

}