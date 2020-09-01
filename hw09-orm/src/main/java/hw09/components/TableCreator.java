package hw09.components;

import java.sql.SQLException;
import javax.sql.DataSource;

public class TableCreator {
    private static final String QUERY_CREATE_ACCOUNT = "create table account(no bigint(20) auto_increment,type varchar(255), rest int(3))";
    private static final String QUERY_CREATE_USER = "create table user(id bigint(20) auto_increment,name varchar(255), age int(3))";

    private DataSource dataSource;

    public TableCreator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTableAccount() throws SQLException {
        createTableByQuery(QUERY_CREATE_ACCOUNT);
        System.out.println("table account created");
    }

    public void createTableUser() throws SQLException {
        createTableByQuery(QUERY_CREATE_USER);
        System.out.println("table user created");
    }

    private void createTableByQuery(String query) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(query)) {
            pst.executeUpdate();
        }
    }
}
