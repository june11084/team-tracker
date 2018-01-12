package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {
    private String team;
    private ArrayList<String> members;
    private static ArrayList<Post> instances = new ArrayList<>();
    private LocalDateTime createdAt;
    private int id;

    public Post (String team){
        this.team = team;
        members = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        instances.add(this);
        this.id = instances.size();
    }

    public String getTeam() {
        return team;
    }

    public String getMembers() {
        String memberString = members.toString();
        memberString = memberString.replaceAll("[\\[\\]]", "");
        return memberString;
    }
    public static Post findById(int id){
        return instances.get(id-1);
    }

    public static ArrayList<Post> getAll(){
        return instances;
    }

    public static void clearAllPosts(){
        instances.clear();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void updateTeam(String teamName) {
        this.team = teamName;
    }

    public void updateMember(ArrayList member) {
        members = member;
    }
}
