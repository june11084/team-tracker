package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewPostObjectGetsCorrectlyCreated_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals(true, post instanceof Post);
    }

    @Test
    public void PostInstantiatesWithContent_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals("Day 1: Intro", post.getTeam());
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