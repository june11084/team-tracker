package models;

import java.util.ArrayList;
import java.util.Objects;

public class Post {
    private String team;
    private String password;
    private String members;
    private int id;
    private int stateId;

    public Post (String team,String members,String password,int stateId){
        this.team = team;
        this.password = password;
        this.members = members;
        this.stateId = stateId;
    }

    public int getStateId(){return stateId;}

    public String getTeam() {
        return team;
    }

    public String getPassword() {
        return password;
    }

    public String getMembers() {
        return members;
    }

    public String getMemberToString() {
        String memberString = members.toString();
        memberString = memberString.replaceAll("[\\[\\]]", "");
        return memberString;
    }


    public int getId() {
        return this.id;
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

    public void updateMember(String member) {
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
                Objects.equals(members, post.members);
    }

    @Override
    public int hashCode() {

        return Objects.hash(team, password, members, id, stateId);
    }
}
