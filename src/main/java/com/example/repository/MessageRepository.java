package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

/**
 * A JPA repository for messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Get all messages posted by the user with the given ID.
     * @param postedBy The ID of the user whose messages will be returned.
     * @return A list of <code>Message</code>s posted by the given user.
     */
    List<Message> findByPostedBy(int postedBy);
    
    /**
     * Delete a message with the given ID.
     * @param messageID The ID of the message to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = ?1")
    int deleteMessageByMessageId(int messageID);

    /**
     * Update the message text of the message with the given ID.
     * @param messageID The ID of the message to update.
     * @param messageText The new text to update the message with.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText = ?2 WHERE messageId = ?1")
    int updateMessageTextById(int messageID, String messageText);
}
