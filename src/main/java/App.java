import java.util.*;

import dao.Sql2oTeamDao;
import dao.TeamDao;
import models.Post;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oTeamDao teamDao = new Sql2oTeamDao(sql2o);

        //get: show about
        get("/posts/about", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "about.hbs");
        }, new HandlebarsTemplateEngine());

       //get: show new team form
        get("/posts/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "post-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new team form
        post("/posts/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();
            String team = request.queryParams("team");
            String members = request.queryParams("members");
            String password = request.queryParams("password");
            Post newPost = new Post(team,members,password, 1);
            teamDao.add(newPost);
            model.put("post", newPost);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all teams
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Post> posts = (ArrayList<Post>) teamDao.getAll();
            model.put("posts", posts);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all teams
        get("/posts/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            teamDao.clearAllPosts();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual team in detail
        get("/posts/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Post foundPost = teamDao.findById(idOfPostToFind); //use it to find post
            model.put("post", foundPost); //add it to model for template to display
            return new ModelAndView(model, "post-detail.hbs"); //individual post page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a team
        get("/posts/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToEdit = Integer.parseInt(req.params("id"));
            Post editPost = teamDao.findById(idOfPostToEdit);
            model.put("editPost", editPost);
            return new ModelAndView(model, "post-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a team
        post("/posts/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newTeam = req.queryParams("team");
            String newMember = req.queryParams("members");
            String password = req.queryParams("password");
            int idOfPostToEdit = Integer.parseInt(req.params("id"));
            Post editPost = teamDao.findById(idOfPostToEdit);
            int stateId = editPost.getId();
            if(password.equals(editPost.getPassWord())) {
                teamDao.update(idOfPostToEdit,newTeam,newMember,password,stateId);
                return new ModelAndView(model, "success.hbs");
            }else{
                return new ModelAndView(model, "error.hbs");
            }
        }, new HandlebarsTemplateEngine());

    }
}