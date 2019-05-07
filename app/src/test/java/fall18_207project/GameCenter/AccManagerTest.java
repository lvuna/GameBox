package fall18_207project.GameCenter;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccManagerTest {
    private Account myAcc;
    private AccountManager manager;

    /**
     * add a new account into the AccountManager.
     */
    private void setUpCorrect() {
        String email = "123@gmail.com";
        String userName = "123";
        String password = "abc123";
        myAcc = new Account(email, userName, password);
        manager = new AccountManager();
        manager.addAccount(new Account(email, userName, password));
    }

    /**
     * test Getter methods.
     */
    @Test
    public void testGetters() {
        setUpCorrect();

        assertEquals("123", manager.getAccount(myAcc.getEmail()).getUserName());
        assertEquals("abc123", manager.getAccount(myAcc.getEmail()).getPassword());

        assertEquals("123@gmail.com", myAcc.getEmail());
        assertEquals("abc123", myAcc.getPassword());
        assertEquals("123", myAcc.getUserName());

        myAcc.setPassword("abc");
        assertNotEquals("abc123", myAcc.getPassword());
        assertEquals("abc", myAcc.getPassword());
//        assertEquals(true, myAcc.getScoreRecord()[0] == null);
    }

    /**
     * test Setters including setUserName, setPassword, setEmail.
     */
    @Test
    public void testSetters() {
        setUpCorrect();

        assertEquals("abc123", myAcc.getPassword());
        myAcc.setPassword("abc");
        assertEquals("abc", myAcc.getPassword());

        myAcc.setUserName("abc");
        assertEquals("abc", myAcc.getUserName());

        myAcc.setEmail("abc@gmail.com");
        assertEquals("abc@gmail.com", myAcc.getEmail());
    }

//    /**
//     * Test ScoreRecord and addScoreRecord method
//     */
//    @Test
//    public void testScoreRecord() {
//        setUpCorrect();
//
//        assertEquals(true, myAcc.getScoreRecord()[0] == null);
//        myAcc.addScore(1, "123");
//        assertEquals(false, myAcc.getScoreRecord()[0].equals("123"));
//        assertEquals(true, myAcc.getScoreRecord()[1].equals("123"));
//    }

    /**
     * test removeAccount method in AccountManager.
     */
    @Test
    public void testRemoveAccount() {
        setUpCorrect();

        boolean removed = manager.removeAccount(myAcc.getEmail());
        assertTrue(removed);

        assertNull(manager.getAccount(myAcc.getEmail())); // cannot find it anymore
    }

    /**
     * Test Validate method ...
     */
    @Test
    public void testValidate() {
        setUpCorrect();
        // it must be true, because it's my account...
        assertTrue(manager.validate(myAcc.getEmail(), myAcc.getPassword()));
        assertFalse(manager.validate(myAcc.getEmail(), "Paul Gries"));

        // What if this email not exists?
        assertFalse(manager.validate("csc207@utoronto.ca", "Lindsey!"));
        assertFalse(manager.validate("csc207@utoronto.ca", myAcc.getPassword()));
    }

    @Test
    public void testAddAccount() {
        setUpCorrect();
        String email = "123@gmail.com";
        String userName = "123";
        String password = "abc123";

        // this one is already there, returns false
        assertFalse(manager.addAccount(new Account(email, userName, password)));

        assertTrue(manager.addAccount(new Account("1", "1", "1")));
        assertEquals("1", manager.getAccount("1").getEmail());
    }
}
