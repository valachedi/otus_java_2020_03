package hw12.server.servlet;

import com.google.gson.Gson;
import hw12.core.model.User;
import hw12.core.service.DbServiceUser;
import hw12.server.services.TemplateProcessor;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersServlet extends HttpServlet {

    private static final String MIME_HTML = "text/html";
    private static final String TEMPLATE_ATTR_USERS = "users";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
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
        paramsMap.put(TEMPLATE_ATTR_USERS, dbServiceUser.getAll());
        response.setContentType(MIME_HTML);
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }
}
