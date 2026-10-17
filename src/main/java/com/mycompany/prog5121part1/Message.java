package com.mycompany.prog5121part1;

import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long id = random.nextLong(1_000_000_000L, 10_000_000_000L);
        return String.valueOf(id);
    }

    private String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return messageID.substring(0, 2) + ":"
                + messageNumber + ":"
                + firstWord.toUpperCase() + lastWord.toUpperCase();
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        return recipient != null
                && recipient.matches("^\\+\\d{1,10}$");
    }

    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public void incrementTotalMessagesSent() {
        totalMessagesSent++;
    }
}