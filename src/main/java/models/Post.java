package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Post {
    private String team;
    private String password;
    private ArrayList<String> members;
    private static ArrayList<Post> instances = new ArrayList<>();
    private LocalDateTime createdAt;
    private int id;
    private int stateId;

    public Post (String team,String password,int stateId){
        this.team = team;
        this.password = password;
        members = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        instances.add(this);
        this.id = instances.size();
        this.stateId = stateId;
    }

    public int getStateId(){return stateId;}

    public String getTeam() {
        return team;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public String getMemberToString() {
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

    public String getPassWord() {
        return password;
    }

    public void updateTeam(String teamName) {
        this.team = teamName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateMember(ArrayList member) {
        members = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                stateId == post.stateId &&
                Objects.equals(team, post.team) &&
                Objects.equals(password, post.password) &&
                Objects.equals(members, post.members) &&
                Objects.equals(createdAt, post.createdAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(team, password, members, createdAt, id, stateId);
    }
}
