package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Guest on 1/10/18.
 */
public class Post {
    private String content;
    private static ArrayList<Post> instances = new ArrayList<>();
    private boolean published;
    private LocalDateTime createdAt;
    private int id;

    public Post (String content){
        this.content = content;
        this.published = false;
        this.createdAt = LocalDateTime.now();
        instances.add(this);
        this.id = instances.size();
    }

    public String getContent() {
        return content;
    }

    public static ArrayList<Post> getAll(){
        return instances;
    }

    public static void clearAllPosts(){
        instances.clear();
    }

    public boolean getPublished(){
        return this.published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public static Post findById(int id){
        return instances.get(id-1); //why minus 1? See if you can figure it out.
    }

    public void update(String content) {
        this.content = content;
    }
    public void deletePost(){
        instances.remove(id-1); //same reason
    }
}
