package com.mycompany.prog5121part1;

import java.util.Random;

import java.util.Scanner;

import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessagesSent = 0;
    
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    
    private static ArrayList<Message> disregardedMessages =
        new ArrayList<>();
    
    private static ArrayList<Message> storedMessages =
        new ArrayList<>();

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

    public String createMessageHash() {
    String[] words = messageText.trim().split("\\s+");

    String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
    String lastWord = words[words.length - 1]
            .replaceAll("[^a-zA-Z0-9]", "");

    return messageID.substring(0, 2) + ":"
            + messageNumber + ":"
            + firstWord.toUpperCase()
            + lastWord.toUpperCase();
}

    public boolean checkMessageID() {
    return messageID != null && messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
    return recipient != null
            && recipient.matches("^\\+\\d{1,11}$");
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
    
    public String SentMessage() {
    Scanner input = new Scanner(System.in);

    System.out.println("Choose an option:");
    System.out.println("1. Send Message");
    System.out.println("2. Disregard Message");
    System.out.println("3. Store Message");

    int choice = input.nextInt();

    switch (choice) {
        case 1:
            totalMessagesSent++;
            sentMessages.add(this);
            return "Message successfully sent.";

        case 2:
            return "Press 0 to delete the message.";

        case 3:
            return storeMessage();

        default:
            return "Invalid option. Please try again.";
    }
}
    
    public static String printMessages() {
    StringBuilder output = new StringBuilder();

    for (Message message : sentMessages) {
        output.append("Message ID: ")
              .append(message.getMessageID())
              .append("\nMessage Hash: ")
              .append(message.getMessageHash())
              .append("\nRecipient: ")
              .append(message.getRecipient())
              .append("\nMessage: ")
              .append(message.getMessageText())
              .append("\n\n");
    }

    if (output.length() == 0) {
        return "No messages have been sent.";
    }

    return output.toString();
}
    
    public static int returnTotalMessagess() {
    return totalMessagesSent;
}
    
    public String storeMessage() {
    System.out.println("Working directory: "
        + System.getProperty("user.dir"));
        Path filePath = Path.of("messages.json");

    String messageJson = "  {\n"
            + "    \"messageID\": \"" + escapeJson(messageID) + "\",\n"
            + "    \"messageNumber\": " + messageNumber + ",\n"
            + "    \"recipient\": \"" + escapeJson(recipient) + "\",\n"
            + "    \"messageText\": \"" + escapeJson(messageText) + "\",\n"
            + "    \"messageHash\": \"" + escapeJson(messageHash) + "\"\n"
            + "  }";

    try {
        String existingJson = "";

        if (Files.exists(filePath)) {
            existingJson = Files.readString(filePath).trim();
        }

        if (existingJson.isEmpty()) {
            existingJson = "[]";
        }

        if (!existingJson.startsWith("[") || !existingJson.endsWith("]")) {
            return "Unable to store message: messages.json is not a valid JSON array.";
        }

        String existingMessages = existingJson
                .substring(1, existingJson.length() - 1)
                .trim();

        String updatedJson;

        if (existingMessages.isEmpty()) {
            updatedJson = "[\n" + messageJson + "\n]";
        } else {
            updatedJson = "[\n"
                    + existingMessages + ",\n"
                    + messageJson + "\n]";
        }

        Files.writeString(filePath, updatedJson);

        return "Message successfully stored.";

    } catch (IOException e) {
        return "Unable to store message: " + e.getMessage();
    }
}

private String escapeJson(String value) {
    StringBuilder escaped = new StringBuilder();

    for (int i = 0; i < value.length(); i++) {
        char character = value.charAt(i);

        switch (character) {
            case '"':
                escaped.append("\\\"");
                break;
            case '\\':
                escaped.append("\\\\");
                break;
            case '\b':
                escaped.append("\\b");
                break;
            case '\f':
                escaped.append("\\f");
                break;
            case '\n':
                escaped.append("\\n");
                break;
            case '\r':
                escaped.append("\\r");
                break;
            case '\t':
                escaped.append("\\t");
                break;
            default:
                if (character < 0x20) {
                    escaped.append(String.format("\\u%04x", (int) character));
                } else {
                    escaped.append(character);
                }
                break;
        }
    }

    return escaped.toString();
}

public String processMessage(int choice) {
    switch (choice) {
        case 1:
            totalMessagesSent++;
            sentMessages.add(this);
            return "Message successfully sent.";

        case 2:
            disregardedMessages.add(this);
            return "Press 0 to delete the message";

        case 3:
            String result = storeMessage();

            if (result.equals("Message successfully stored.")) {
                storedMessages.add(this);
            }

            return result;

        default:
            return "Invalid option. Please try again.";
    }
}

public static String searchMessagesByRecipient(String recipient) {
    StringBuilder output = new StringBuilder();

    for (Message message : sentMessages) {
        if (message.getRecipient().equals(recipient)) {
            output.append("Message ID: ")
                  .append(message.getMessageID())
                  .append("\nMessage: ")
                  .append(message.getMessageText())
                  .append("\nRecipient: ")
                  .append(message.getRecipient())
                  .append("\n\n");
        }
    }

    if (output.length() == 0) {
        return "No messages found for this recipient.";
    }

    return output.toString();
 }

public static String searchMessageByID(String messageID) {
    for (Message message : sentMessages) {
        if (message.getMessageID().equals(messageID)) {
            return "Recipient: " + message.getRecipient()
                    + "\nMessage: " + message.getMessageText();
        }
    }

    return "Message not found.";
 }

public static String getLongestMessage() {
    if (sentMessages.isEmpty()) {
        return "No messages have been sent.";
    }

    Message longest = sentMessages.get(0);

    for (Message message : sentMessages) {
        if (message.getMessageText().length()
                > longest.getMessageText().length()) {
            longest = message;
        }
    }

    return longest.getMessageText();
}

public static boolean deleteMessageByHash(String messageHash) {
    for (int i = 0; i < sentMessages.size(); i++) {
        Message message = sentMessages.get(i);

        if (message.getMessageHash().equals(messageHash)) {
            sentMessages.remove(i);
            return true;
        }
    }

    return false;
}

public static ArrayList<Message> getStoredMessages() {
    return storedMessages;
}

public static ArrayList<Message> getDisregardedMessages() {
    return disregardedMessages;
}
}