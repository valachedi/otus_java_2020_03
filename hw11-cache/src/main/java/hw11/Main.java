package hw11;

import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import hw11.core.model.Address;
import org.hibernate.SessionFactory;
import hw11.core.dao.UserDao;
import hw11.core.service.DBServiceUser;
import hw11.core.service.DbServiceUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hw11.core.model.Address;
import hw11.core.model.User;
import hw11.core.model.Phone;
import java.util.ArrayList;
import java.util.List;
import hw11.hibernate.dao.UserDaoHibernate;
import hw11.hibernate.HibernateUtils;
import hw11.hibernate.sessionmanager.SessionManagerHibernate;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        Address address = new Address(0, "ул. Ленина, д. 364, кв. 84");
        List<Phone> phones = new ArrayList<Phone>(1);
        User newUser = new User(0, "Вася", 30);
        newUser.setAddress(address);
        newUser.addPhone(new Phone(0, "+ 7 123 456 78 90"));
        newUser.addPhone(new Phone(0, "+ 7 111 364 84 99"));

        long id = dbServiceUser.save(newUser);
        Optional<User> createdUser = dbServiceUser.getById(id);
        outputUserOptional("Created user", createdUser);

        for(int i = 0; i < 3; i++) {
            dbServiceUser.getById(id);
        }
    }

    private static void outputUserOptional(String header, Optional<User> userOptional) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);

        userOptional.ifPresentOrElse((User user) -> {
            System.out.println(user);
            System.out.println(user.getAddress());

            for(Phone phone : user.getPhones()) {
                System.out.println(phone);
            }
        }, () -> logger.info("User not found"));
    }
}
