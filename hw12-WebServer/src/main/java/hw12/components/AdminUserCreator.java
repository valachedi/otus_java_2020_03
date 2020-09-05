package hw12.components;

import hw12.core.model.Address;
import hw12.core.model.Phone;
import hw12.core.model.User;
import hw12.core.service.DbServiceUser;
import java.util.ArrayList;
import java.util.List;

public class AdminUserCreator {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "nimda";

    private DbServiceUser dbServiceUser;

    public AdminUserCreator(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    public void createAdminUser() {
        Address address = new Address(0, "ул. Ленина, д. 364, кв. 84");
        List<Phone> phones = new ArrayList<Phone>(1);
        User newUser = new User(0, ADMIN_LOGIN, ADMIN_PASSWORD, "Администратор", 30);
        newUser.setAddress(address);
        newUser.addPhone(new Phone(0, "+ 7 123 456 78 90"));
        newUser.addPhone(new Phone(0, "+ 7 111 364 84 99"));
        dbServiceUser.save(newUser);
    }
}
