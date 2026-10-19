package com.mycompany.prog5121part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageIDIsTenDigits() {
        Message message = new Message(
                1,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                message.getMessageID().substring(0, 2)
                        + ":0:HITONIGHT",
                message.createMessageHash()
        );
    }

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                1,
                "08575975889",
                "Hello"
        );

        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageLengthWithinLimit() {
        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthOverLimit() {
        Message message = new Message(
                1,
                "+27718693002",
                "A".repeat(251)
        );

        assertFalse(message.checkMessageLength());
    }
    
    @Test
    public void testSendMessage() {
    Message message = new Message(
            1,
            "+27834557896",
            "Did you get the cake?"
    );

    assertEquals(
            "Message successfully sent.",
            message.processMessage(1)
    );
    }
    
    @Test
    public void testDisregardMessage() {
    Message message = new Message(
            2,
            "+27834557896",
            "Did you get the cake?"
    );

    assertEquals(
            "Press 0 to delete the message",
            message.processMessage(2)
    );
    }

    @Test
    public void testStoreMessage() {
    Message message = new Message(
            3,
            "+27834557896",
            "Did you get the cake?"
    );

    assertEquals(
            "Message successfully stored.",
            message.processMessage(3)
    );
    }
    
    @Test
public void testSearchMessagesByRecipient() {
    Message message = new Message(
            1,
            "+27834557896",
            "Did you get the cake?"
    );

    message.processMessage(1);

    String result =
            Message.searchMessagesByRecipient("+27834557896");

    assertTrue(result.contains("Did you get the cake?"));
    }

@Test
public void testSearchMessageByID() {
    Message message = new Message(
            1,
            "+27834557896",
            "Did you get the cake?"
    );

    message.processMessage(1);

    String result =
            Message.searchMessageByID(message.getMessageID());

    assertTrue(result.contains("+27834557896"));
    assertTrue(result.contains("Did you get the cake?"));
}

 @Test
public void testGetLongestMessage() {
    Message shortMessage = new Message(
            1, "+27834557896", "Hi"
    );

    Message longMessage = new Message(
            2, "+27834557896", "Are you coming to dinner tonight?"
    );

    shortMessage.processMessage(1);
    longMessage.processMessage(1);

    assertEquals(
            "Are you coming to dinner tonight?",
            Message.getLongestMessage()
    );
 }

@Test
public void testDeleteMessageByHash() {
    Message message = new Message(
            1,
            "+27834557896",
            "Did you get the cake?"
    );

    message.processMessage(1);

    String messageHash = message.getMessageHash();

    assertTrue(Message.deleteMessageByHash(messageHash));

    assertEquals(
            "Message not found.",
            Message.searchMessageByID(message.getMessageID())
    );
}

@Test
public void testPrintMessages() {
    Message message = new Message(
            1,
            "+27834557896",
            "Hello there!"
    );

    message.processMessage(1);

    String report = Message.printMessages();

    assertTrue(report.contains(message.getMessageID()));
    assertTrue(report.contains(message.getMessageHash()));
    assertTrue(report.contains(message.getRecipient()));
    assertTrue(report.contains(message.getMessageText()));
 }

@Test
public void testStoredMessagesArray() {
    Message message = new Message(
            20,
            "+27834557896",
            "Where are you? You are late!"
    );

    assertEquals(
            "Message successfully stored.",
            message.processMessage(3)
    );

    assertTrue(
            Message.getStoredMessages().contains(message)
    );
}

@Test
public void testDisregardedMessagesArray() {
    Message message = new Message(
            21,
            "+27834557896",
            "I will send this later."
    );

    assertEquals(
            "Press 0 to delete the message",
            message.processMessage(2)
    );

    assertTrue(
            Message.getDisregardedMessages().contains(message)
    );
}

@Test
public void testDeleteMessageKeepsOtherMessages() {
    Message firstMessage = new Message(
            30,
            "+27834557896",
            "First message."
    );

    Message secondMessage = new Message(
            31,
            "+27834557896",
            "Second message."
    );

    firstMessage.processMessage(1);
    secondMessage.processMessage(1);

    assertTrue(
            Message.deleteMessageByHash(firstMessage.getMessageHash())
    );

    assertTrue(
            Message.searchMessageByID(secondMessage.getMessageID())
                    .contains("Second message.")
    );
}
}