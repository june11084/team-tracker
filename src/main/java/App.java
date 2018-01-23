import java.util.*;

import dao.Sql2oStateDao;
import dao.Sql2oTeamDao;
import models.Post;
import models.State;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oTeamDao teamDao = new Sql2oTeamDao(sql2o);
        Sql2oStateDao stateDao = new Sql2oStateDao(sql2o);

        //get: show about
        get("/posts/about", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "about.hbs");
        }, new HandlebarsTemplateEngine());

       //get: show new state form
        get("/states/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "state-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new state form
        post("/states/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();
            String state = request.queryParams("name");
            State newPost = new State(state);
            stateDao.add(newPost);
            model.put("states", newPost);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all states
        get("/states", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<State> states = stateDao.getAll();
            model.put("states", states);
            return new ModelAndView(model, "states.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new team form
        get("/states/:stateId/posts/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeam = parseInt(req.params("stateId"));
            State foundState = stateDao.findById(idOfTeam);
            model.put("state", foundState);
            return new ModelAndView(model, "post-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new team form
        post("/states/:stateId/posts/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();
            int stateId = Integer.parseInt(request.params("stateId"));
            State state = stateDao.findById(stateId);
            model.put("state",state);
            String team = request.queryParams("team");
            String members = request.queryParams("members");
            String password = request.queryParams("password");
            Post newPost = new Post(team,members,password, stateId);
            teamDao.add(newPost);
            model.put("post", newPost);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all teams
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<State> states = stateDao.getAll();
            model.put("states", states);
            return new ModelAndView(model, "states.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all states and teams
        get("/posts/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            teamDao.clearAllPosts();
            stateDao.clearAllStates();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //delete state by id and teams
        get("/states/:stateId/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStateToDelete = parseInt(req.params("stateId"));

            teamDao.deleteAllById(idOfStateToDelete);
            stateDao.deleteById(idOfStateToDelete);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //delete team by id
        get("/states/:stateId/posts/:teamId/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            int teamId = Integer.parseInt(req.params("teamId"));
            teamDao.deleteById(teamId);

            int idOfStateToFind = parseInt(req.params("stateId"));
            State foundState = stateDao.findById(idOfStateToFind);
            model.put("state", foundState);

            List<Post> teams = teamDao.getAllTeamsByState(idOfStateToFind);
            model.put("teams", teams);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual state in detail
        get("/states/:stateId", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStateToFind = Integer.parseInt(req.params("stateId")); //pull id - must match route segment
            State foundState = stateDao.findById(idOfStateToFind); //use it to find post
            model.put("state", foundState); //add it to model for template to display
            List<Post> teams = teamDao.getAllTeamsByState(idOfStateToFind);
            model.put("teams", teams);
            return new ModelAndView(model, "state-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual team in detail
        get("/posts/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(req.params("id"));
            Post foundPost = teamDao.findById(idOfPostToFind);
            model.put("post", foundPost);
            return new ModelAndView(model, "post-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a team
        get("/states/:stateId/posts/:teamId/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStateToEdit = parseInt(req.params("stateId"));
            State editState = stateDao.findById(idOfStateToEdit);
            model.put("state", editState);
            int idOfPostToEdit = Integer.parseInt(req.params("teamId"));
            Post editPost = teamDao.findById(idOfPostToEdit);
            model.put("editPost", editPost);
            return new ModelAndView(model, "post-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a team
        post("/states/:stateId/posts/:teamId/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newTeam = req.queryParams("team");
            String newMember = req.queryParams("members");
            String password = req.queryParams("password");
            int idOfStateToEdit = parseInt(req.params("stateId"));
            State editState = stateDao.findById(idOfStateToEdit);
            model.put("state", editState);
            int idOfPostToEdit = Integer.parseInt(req.params("teamId"));
            Post editPost = teamDao.findById(idOfPostToEdit);
            if(password.equals(editPost.getPassWord())) {
                teamDao.update(idOfPostToEdit,newTeam,newMember);
                return new ModelAndView(model, "success.hbs");
            }else{
                return new ModelAndView(model, "error.hbs");
            }
        }, new HandlebarsTemplateEngine());

    }
}