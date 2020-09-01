package hw12.server.servlet;

import com.google.gson.Gson;
import hw12.core.model.User;
import hw12.core.service.DbServiceUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Optional;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DbServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DbServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = dbServiceUser.getById(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
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
