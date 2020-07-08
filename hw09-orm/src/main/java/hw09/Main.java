package hw09;

import java.sql.SQLException;
import javax.sql.DataSource;

import hw09.components.TableCreator;
import hw09.h2.DataSourceH2;

public class Main {
    private static DataSource dataSource = new DataSourceH2();

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        createTables();

        new DemoUser(dataSource).run();
        new DemoAccount(dataSource).run();
    }

    private void createTables() throws SQLException {
        var tableCreator = new TableCreator(dataSource);
        tableCreator.createTableAccount();
        tableCreator.createTableUser();
    }
}
