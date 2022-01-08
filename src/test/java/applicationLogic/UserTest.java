package applicationLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User("nick","secret");
    }

    @Test
    void changePassword() {
        user.changePassword("s", "newSecret");
        assertEquals("secret", user.getPassword());

        user.changePassword("secret", "newSecret");
        assertEquals("newSecret", user.getPassword());
    }
}