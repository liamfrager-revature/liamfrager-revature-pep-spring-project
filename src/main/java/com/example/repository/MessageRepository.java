package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByPostedBy(int postedBy);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = ?1")
    int deleteMessageByMessageId(int messageID);

    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText = ?2 WHERE messageId = ?1")
    int updateMessageTextById(int messageID, String messageText);
}
