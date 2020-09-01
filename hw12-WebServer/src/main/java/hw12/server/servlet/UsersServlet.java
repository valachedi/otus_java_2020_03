package hw12.server.servlet;

import hw12.core.model.User;
import hw12.core.service.DbServiceUser;
import hw12.server.services.TemplateProcessor;
import com.google.gson.Gson;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_LAST_USER = "lastUser";
    private static final Logger logger = LoggerFactory.getLogger(UsersServlet.class);

    private final DbServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public UsersServlet(TemplateProcessor templateProcessor, DbServiceUser dbServiceUser, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        dbServiceUser.getLastUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_LAST_USER, randomUser));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }
}
