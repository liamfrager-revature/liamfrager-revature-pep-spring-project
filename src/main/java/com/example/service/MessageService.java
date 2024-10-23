package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

/**
 * A service for handling the <code>Message</code> business logic.
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    /**
     * Constructor for the message service.
     */
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Get all messages.
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Get a message with a given ID.
     * @param id The ID of the message to be returned.
     * @return The message with the given <code>id</code>.
     */
    public Message getMessageByID(int id) {
        return messageRepository.findById(id).orElse(null);
    }
    
    /**
     * Add a new message to the database if the message is valid.
     * @param message The message to be added.
     * @return The new message.
     * @throws InvalidMessageTextException <code>message.message_text</code> is either empty or longer than 255 characters.
     * @throws InvalidUserIDException There is no user with the given <code>message.posted_by</code> ID.
     */
    public Message postMessage(Message message) throws InvalidMessageTextException, InvalidUserIDException {
        if (message.getMessageText().length() <= 0 || message.getMessageText().length() >= 255)
            throw new InvalidMessageTextException();
        if (accountRepository.findById(message.getPostedBy()).isEmpty())
            throw new InvalidUserIDException(message.getPostedBy());
        return messageRepository.save(message);
        
    }
    
    /**
     * Update a message with a given ID if the given new_message is valid.
     * @param id The ID of the message to update.
     * @param newMessage The new message that will replace the old message.
     * @return The updated message.
     * @throws InvalidMessageTextException <code>new_message.message_text</code> is either empty or longer than 255 characters.
     * @throws InvalidMessageIDException There is no message with the given <code>id</code>.
     */
    public int patchMessageByID(int id, Message newMessage) throws InvalidMessageTextException, InvalidMessageIDException {
        if (newMessage.getMessageText().length() <= 0 || newMessage.getMessageText().length() >= 255)
            throw new InvalidMessageTextException();
        int updatedRows = messageRepository.updateMessageTextById(id, newMessage.getMessageText());
        if (updatedRows > 0)
            return updatedRows;
        throw new InvalidMessageIDException();
    }
    
    /**
     * Delete a message with a given ID if it exists.
     * @param id The ID of the message to delete.
     * @return The number of entries removed from the database.
     */
    public int deleteMessageByID(int id) {
        return messageRepository.deleteMessageByMessageId(id);
    }
    
    /**
     * Get all messages posted by a user with the given ID.
     * @param id The ID of the user whose messages will be returned. Returns null if the ID is invalid
     * @return A list of all messages by the user with the given <code>id</code>.
     */
    public List<Message> getAllMessagesByAccountID(int id) {
        return messageRepository.findByPostedBy(id);
    }
}

