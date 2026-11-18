package com.mycompany.prog5121part1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessagesSent = 0;

    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();

    private static final Path FILE_PATH = Path.of("messages.json");

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
        if (messageText == null || messageText.trim().isEmpty()) {
            return messageID.substring(0, 2) + ":" + messageNumber + ":";
        }

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
        return recipient != null && recipient.matches("^\\+\\d{1,11}$");
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

        if (!input.hasNextInt()) {
            input.nextLine();
            return "Invalid option. Please try again.";
        }

        int choice = input.nextInt();
        input.nextLine();

        return processMessage(choice);
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

    // Store the message in messages.json.
    public String storeMessage() {
        ArrayList<Message> messagesOnFile = readMessagesFromFile();

        if (messagesOnFile == null) {
            return "Unable to store message: could not read messages.json.";
        }

        messagesOnFile.add(this);

        try {
            Gson gson = new Gson();
            Files.writeString(FILE_PATH, gson.toJson(messagesOnFile));

            storedMessages.clear();
            storedMessages.addAll(messagesOnFile);

            return "Message successfully stored.";
        } catch (IOException e) {
            return "Unable to store message: " + e.getMessage();
        }
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
                return storeMessage();

            default:
                return "Invalid option. Please try again.";
        }
    }

    // PART 2 METHODS: these continue to work with sent messages.

    public static String searchMessagesByRecipient(String recipient) {
        return searchRecipientInList(sentMessages, recipient);
    }

    public static String searchMessageByID(String messageID) {
        return searchIDInList(sentMessages, messageID);
    }

    public static String getLongestMessage() {
        return findLongestMessage(sentMessages, "No messages have been sent.");
    }

    public static boolean deleteMessageByHash(String messageHash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            Message message = sentMessages.get(i);

            if (messageHash != null
                    && messageHash.equals(message.getMessageHash())) {
                sentMessages.remove(i);
                return true;
            }
        }

        return false;
    }

    // PART 3 METHODS: these work with stored messages.

    public static String displayStoredSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : storedMessages) {
            output.append("Sender: ")
                    .append("You")
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n\n");
        }

        return output.toString();
    }

    public static String getLongestStoredMessage() {
        return findLongestMessage(storedMessages, "No stored messages found.");
    }

    public static String searchStoredMessageByID(String messageID) {

    String result = searchIDInList(sentMessages, messageID);

    if (!result.equals("Message not found.")) {
        return result;
    }

    result = searchIDInList(storedMessages, messageID);

    if (!result.equals("Message not found.")) {
        return result;
    }

    return searchIDInList(disregardedMessages, messageID);
    }

    public static String searchStoredMessagesByRecipient(String recipient) {
        return searchRecipientInList(storedMessages, recipient);
    }

    public static boolean deleteStoredMessageByHash(String messageHash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message message = storedMessages.get(i);

            if (messageHash != null
                    && messageHash.equals(message.getMessageHash())) {
                storedMessages.remove(i);

                if (writeStoredMessagesToFile()) {
                    return true;
                }

                storedMessages.add(i, message);
                return false;
            }
        }

        return false;
    }

    public static String displayStoredMessagesReport() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : storedMessages) {
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

        return output.toString();
    }

    private static String searchRecipientInList(
            ArrayList<Message> messages, String recipient) {

        StringBuilder output = new StringBuilder();

        for (Message message : messages) {
            if (recipient != null
                    && recipient.equals(message.getRecipient())) {
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

    private static String searchIDInList(
            ArrayList<Message> messages, String messageID) {

        for (Message message : messages) {
            if (messageID != null
                    && messageID.equals(message.getMessageID())) {
                return "Recipient: " + message.getRecipient()
                        + "\nMessage: " + message.getMessageText();
            }
        }

        return "Message not found.";
    }

    private static String findLongestMessage(
            ArrayList<Message> messages, String emptyMessage) {

        if (messages.isEmpty()) {
            return emptyMessage;
        }

        Message longest = messages.get(0);

        for (Message message : messages) {
            if (message.getMessageText() != null
                    && (longest.getMessageText() == null
                    || message.getMessageText().length()
                    > longest.getMessageText().length())) {
                longest = message;
            }
        }

        return longest.getMessageText();
    }

    private static ArrayList<Message> readMessagesFromFile() {
        Gson gson = new Gson();

        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(FILE_PATH);

            Type messageListType =
                    new TypeToken<ArrayList<Message>>() {}.getType();

            ArrayList<Message> messages =
                    gson.fromJson(json, messageListType);

            return messages == null ? new ArrayList<>() : messages;

        } catch (IOException | com.google.gson.JsonSyntaxException e) {
            System.out.println("Error reading messages.json: " + e.getMessage());
            return null;
        }
    }

    private static boolean writeStoredMessagesToFile() {
        try {
            Gson gson = new Gson();
            Files.writeString(FILE_PATH, gson.toJson(storedMessages));
            return true;
        } catch (IOException e) {
            System.out.println("Error updating messages.json: " + e.getMessage());
            return false;
        }
    }

    public static void loadStoredMessages() {
        ArrayList<Message> loadedMessages = readMessagesFromFile();

        if (loadedMessages == null) {
            System.out.println("Stored messages could not be loaded.");
            return;
        }

        storedMessages.clear();
        storedMessages.addAll(loadedMessages);

        System.out.println("Stored messages loaded: " + storedMessages.size());
    }

    public static ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public static ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public static ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    // Part 3 helper data for message IDs and hashes.
    public static ArrayList<String> getMessageIDs() {
        ArrayList<String> ids = new ArrayList<>();

        for (Message message : sentMessages) {
            ids.add(message.getMessageID());
        }

        return ids;
    }

    public static ArrayList<String> getMessageHashes() {
        ArrayList<String> hashes = new ArrayList<>();

        for (Message message : sentMessages) {
            hashes.add(message.getMessageHash());
        }

        return hashes;
    }
}