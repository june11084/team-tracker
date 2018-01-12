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
    public void Post_GetsCorrectTeam() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals("Day 1: Intro", post.getTeam());
    }

    @Test
    public void Post_CanFindById() throws Exception {
      Post post = new Post("Day 1: Intro");
      Post post1 = new Post("Day 2: Intro");
      Post post2 = new Post("Day 3: Intro");
      assertEquals(Post.getAll().get(0), Post.findById(1));
      assertEquals(Post.getAll().get(1), Post.findById(2));
      assertEquals(Post.getAll().get(2), Post.findById(3));
    }

    @Test
    public void Post_ClearsAllInstances() throws Exception {
      Post post = new Post("Day 1: Intro");
      Post post1 = new Post("Day 2: Intro");
      Post post2 = new Post("Day 3: Intro");
      Post.clearAllPosts();
      assertEquals(0, Post.getAll().size() );
    }

    @Test
    public void Post_UpdatesTeam() throws Exception {
      Post post = new Post("Day 1: Intro");
      post.updateTeam("Day 2: objects");
      assertEquals("Day 2: objects", post.getTeam());
    }

    @Test
    public void Post_getMember_toString() throws Exception {
      Post post = new Post("Day 1: Intro");
      String members ="john,christine,Lee,adam,dave";
      List<String> membersList = new ArrayList<>(Arrays.asList(members.split(" , ")));
      post.updateMember((ArrayList) membersList);
      assertEquals("john,christine,Lee,adam,dave", post.getMembers());
    }
}