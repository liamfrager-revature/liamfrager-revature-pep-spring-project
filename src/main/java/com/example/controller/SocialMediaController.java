package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    /**
     * Constructor for the social media controller.
     */
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    /**
     * Handler for the <code>/register</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be registered.
     */
    @PostMapping("/register")
    private ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.register(account));
        } catch (InvalidUsernameException | InvalidPasswordException | UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Handler for the <code>/login</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be logged in.
     */
    @PostMapping("/login")
    private ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.login(account));
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Handler for the <code>/messages</code> <code>POST</code> endpoint.
     * @param message The body of the request containing the message data to be added.
     */
    @PostMapping("/messages")
    private ResponseEntity<Message> postMessage(@RequestBody Message message) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.postMessage(message));
        } catch (InvalidMessageTextException | InvalidUserIDException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Handler for the <code>/messages</code> <code>GET</code> endpoint.
     */
    @GetMapping("/messages")
    private ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>GET</code> endpoint.
     * @param id The ID of the message to be returned.
     */
    @GetMapping("/messages/{id}")
    private ResponseEntity<Message> getMessageByID(@PathVariable int id) {
        Message message = messageService.getMessageByID(id);
        if (message == null)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>DELETE</code> endpoint.
     * @param id The ID of the message to be deleted.
     */
    @DeleteMapping("/messages/{id}")
    private ResponseEntity<Message> deleteMessageByID(@PathVariable int id) {
        Message deletedMessage = messageService.deleteMessageByID(id);
        if (deletedMessage == null)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(deletedMessage);
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>PATCH</code> endpoint.
     * @param id The ID of the message to be updated.
     * @param message The body of the request containing the message data to be updated.
     */
    @PatchMapping("/messages/{id}")
    private ResponseEntity<Message> patchMessageByID(@PathVariable int id, @RequestBody Message message) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.patchMessageByID(id, message));
        } catch (InvalidMessageTextException | InvalidMessageIDException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Handler for the <code>/accounts/{account_id}/messages</code> <code>GET</code> endpoint.
     * @param account_id The ID of the account of whose messsages will be returned.
     */
    @GetMapping("/account/{account_id}/messages")
    private ResponseEntity<List<Message>> getAllMessagesByAccountID(@PathVariable int account_id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesByAccountID(account_id));
    }

    @ExceptionHandler({InvalidMessageTextException.class, InvalidMessageIDException.class})
    private ResponseEntity<Void> invalidMessageExceptionHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
