package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class DatabaseTest {
    private SqliteDB db;

    @BeforeEach
    void before() {
        db = new SqliteDB();
        db.openConnection();
    }

    @AfterEach
    void after() {
        db.closeConnection();
        db = null;
    }

    @Test
    void basicMethods() throws SQLException {
        db.register("test", "test");
        assertTrue(db.getAllNicks().contains("test"));
        assertTrue(db.login("test", "test"));
        db.deleteUser("test");
        assertFalse(db.getAllNicks().contains("test"));
    }

    @Test
    void changePassword() throws SQLException {
        db.register("test", "test");
        db.changePassword("test", "test2");
        assertTrue(db.login("test", "test2"));
    }

    @Test
    void getPassword() throws SQLException {
        assertTrue(db.login(
                "test", db.getPassword("test")
        ));
    }
}
