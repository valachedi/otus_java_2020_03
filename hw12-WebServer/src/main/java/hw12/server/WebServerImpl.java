package hw12.server;

import com.google.gson.Gson;
import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import hw12.core.service.DbServiceUser;
import hw12.helpers.FileSystemHelper;
import hw12.server.services.TemplateProcessor;
import hw12.server.services.UserAuthService;
import hw12.server.servlet.AuthorizationFilter;
import hw12.server.servlet.LoginServlet;
import hw12.server.servlet.UsersApiServlet;
import hw12.server.servlet.UsersServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Arrays;

public class WebServerImpl implements WebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final DbServiceUser dbServiceUser;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final UserAuthService authService;

    public WebServerImpl(int port, UserAuthService authService, DbServiceUser dbServiceUser, Gson gson, TemplateProcessor templateProcessor) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        this.authService = authService;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {
        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));
        server.setHandler(handlers);

        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, dbServiceUser, gson)), "/users");
        servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(dbServiceUser, gson)), "/api/user/*");
        return servletContextHandler;
    }
}
