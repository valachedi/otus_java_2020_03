package hw12.server.servlet;

import com.google.gson.Gson;
import hw12.core.model.User;
import hw12.core.service.DbServiceUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;


public class UsersApiServlet extends HttpServlet {
    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final int PARAM_VALUE_NOT_SET = -1;

    private final DbServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DbServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long userId = extractIdFromRequest(request);
        String responseString;

        if(userId == PARAM_VALUE_NOT_SET) {
            List<User> users = dbServiceUser.getAll();
            responseString = gson.toJson(users);
        } else {
            User user;
            user = dbServiceUser.getById(userId).orElse(null);
            responseString = gson.toJson(user);
        }

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(responseString);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(PARAM_VALUE_NOT_SET);
        return Long.parseLong(id);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(jsonString);
        User user = gson.fromJson(jsonString, User.class);
        dbServiceUser.save(user);

        Optional<User> createdUserOptional = dbServiceUser.getByLogin(user.getLogin());

        if(createdUserOptional.isPresent()) {
            response.getOutputStream().print(gson.toJson(createdUserOptional.get()));
        }
    }
}
