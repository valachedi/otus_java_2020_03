package hw12;

import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import hw12.core.model.Address;
import org.hibernate.SessionFactory;
import hw12.core.dao.UserDao;
import hw12.core.service.DbServiceUser;
import hw12.core.service.DbServiceUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw12.core.model.Address;
import hw12.core.model.User;
import hw12.core.model.Phone;
import java.util.ArrayList;
import java.util.List;
import hw12.hibernate.dao.UserDaoHibernate;
import hw12.hibernate.HibernateUtils;
import hw12.hibernate.sessionmanager.SessionManagerHibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hw12.server.services.*;
import hw12.server.WebServer;
import hw12.server.WebServerImpl;
import hw12.server.services.UserAuthService;
import hw12.server.services.UserAuthServiceImpl;

public class Main {
    private static final int LISTEN_PORT = 8888;
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "nimda";

    private static final String TEMPLATES_DIR = "/templates/";

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        Address address = new Address(0, "ул. Ленина, д. 364, кв. 84");
        List<Phone> phones = new ArrayList<Phone>(1);
        User newUser = new User(0, ADMIN_LOGIN, ADMIN_PASSWORD, "Администратор", 30);
        newUser.setAddress(address);
        newUser.addPhone(new Phone(0, "+ 7 123 456 78 90"));
        newUser.addPhone(new Phone(0, "+ 7 111 364 84 99"));
        dbServiceUser.save(newUser);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        WebServer server = new WebServerImpl(LISTEN_PORT, authService, dbServiceUser, gson, templateProcessor);
        server.start();
        server.join();
    }
}
