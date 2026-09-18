package com.mycompany.prog5121part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testCheckUserNameCorrect() {
        Login instance = new Login();
        instance.username = "kyl_1";

        assertTrue(instance.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrect() {
        Login instance = new Login();
        instance.username = "kyle!!!!!!!";

        assertFalse(instance.checkUserName());
    }
    
    @Test
public void testCheckUserNameTooLong() {
    Login instance = new Login();
    instance.username = "abcde_";

    assertFalse(instance.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityCorrect() {
        Login instance = new Login();
        instance.password = "Taiman&28";

        assertTrue(instance.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityIncorrect() {
        Login instance = new Login();
        instance.password = "password";

        assertFalse(instance.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberCorrect() {
        Login instance = new Login();
        instance.cellPhoneNumber = "+27838968976";

        assertTrue(instance.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberIncorrect() {
        Login instance = new Login();
        instance.cellPhoneNumber = "08966553";

        assertFalse(instance.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUserSuccessful() {
        Login instance = new Login();

        instance.registeredUsername = "kyl_1";
        instance.registeredPassword = "Ch&&sec@ke99!";
        instance.loginUsername = "kyl_1";
        instance.loginPassword = "Ch&&sec@ke99!";

        assertTrue(instance.loginUser());
    }

    @Test
    public void testLoginUserFailed() {
        Login instance = new Login();

        instance.registeredUsername = "kyl_1";
        instance.registeredPassword = "Taiman&28";
        instance.loginUsername = "kyle!!!!!!!";
        instance.loginPassword = "password";

        assertFalse(instance.loginUser());
    }
    
    @Test
public void testRegisterUserSuccessful() {
    Login instance = new Login();

    instance.username = "kyl_1";
    instance.password = "Taiman&28!";
    instance.cellPhoneNumber = "+27838968976";

    assertEquals("User registered successfully.", instance.registerUser());
   }
   @Test
public void testRegisterUserInvalidUsername() {
    Login instance = new Login();

    instance.username = "kyle!!!!!!!";
    instance.password = "Taiman&28!";
    instance.cellPhoneNumber = "+27838968976";

    assertEquals(
        "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
        instance.registerUser()
    );
   }
    @Test
public void testRegisterUserInvalidPassword() {
    Login instance = new Login();

    instance.username = "kyl_1";
    instance.password = "password";
    instance.cellPhoneNumber = "+27838968976";

    assertEquals(
        "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
        instance.registerUser()
    );
   }
   @Test
public void testRegisterUserInvalidCellPhoneNumber() {
    Login instance = new Login();

    instance.username = "kyl_1";
    instance.password = "Taiman&28!";
    instance.cellPhoneNumber = "08966553";

    assertEquals(
        "Cell phone number incorrectly formatted or does not contain international code.",
        instance.registerUser()
    );
   }
   @Test
public void testReturnLoginStatusSuccessful() {
    Login instance = new Login();

    instance.firstName = "Tai";
    instance.lastName = "Man";
    instance.registeredUsername = "kyl_1";
    instance.registeredPassword = "Taiman&28";
    instance.loginUsername = "kyl_1";
    instance.loginPassword = "Taiman&28";

    assertEquals(
        "Welcome Tai Man it is great to see you again.",
        instance.returnLoginStatus()
    );
   }
   @Test
public void testReturnLoginStatusFailed() {
    Login instance = new Login();

    instance.firstName = "Tai";
    instance.lastName = "Man";
    instance.registeredUsername = "kyl_1";
    instance.registeredPassword = "Taiman&28";
    instance.loginUsername = "wrong";
    instance.loginPassword = "wrong";

    assertEquals(
        "Username or password incorrect, please try again.",
        instance.returnLoginStatus()
    );
   }
   @Test
public void testRegisterUserStoresDetails() {
    Login instance = new Login();

    instance.username = "kyl_1";
    instance.password = "Taiman&28";
    instance.cellPhoneNumber = "+27838968976";

    instance.registerUser();

    assertEquals("kyl_1", instance.registeredUsername);
    assertEquals("Taiman&28", instance.registeredPassword);
   }
@Test
public void testCheckPasswordExactlyEightCharacters() {
    Login instance = new Login();
    instance.password = "A1!bcdef";

    assertTrue(instance.checkPasswordComplexity());
   }
@Test
public void testCheckCellPhoneNumberTooShort() {
    Login instance = new Login();
    instance.cellPhoneNumber = "+2783896897";

    assertFalse(instance.checkCellPhoneNumber());
   }
}
