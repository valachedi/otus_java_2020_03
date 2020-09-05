package hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hw12.components.AdminUserCreator;
import hw12.core.dao.UserDao;
import hw12.core.model.Address;
import hw12.core.model.Phone;
import hw12.core.model.User;
import hw12.core.service.DbServiceUser;
import hw12.core.service.DbServiceUserImpl;
import hw12.hibernate.dao.UserDaoHibernate;
import hw12.hibernate.HibernateUtils;
import hw12.hibernate.sessionmanager.SessionManagerHibernate;
import hw12.server.services.*;
import hw12.server.services.UserAuthService;
import hw12.server.services.UserAuthServiceImpl;
import hw12.server.WebServer;
import hw12.server.WebServerImpl;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final int LISTEN_PORT = 8888;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CONFIG_NAME = "hibernate.cfg.xml";

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONFIG_NAME, User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        AdminUserCreator adminCreator = new AdminUserCreator(dbServiceUser);
        adminCreator.createAdminUser();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        WebServer server = new WebServerImpl(LISTEN_PORT, authService, dbServiceUser, gson, templateProcessor);
        server.start();
        server.join();
    }
}
