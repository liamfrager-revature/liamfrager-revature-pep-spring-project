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

/**
 * The REST controller that exposes the endpoints for the social media API.
 */
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

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/register</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be registered.
     */
    @PostMapping("/register")
    private ResponseEntity<Account> register(@RequestBody Account account) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        return ResponseEntity.ok(accountService.register(account));
    }

    /**
     * Handler for the <code>/login</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be logged in.
     */
    @PostMapping("/login")
    private ResponseEntity<Account> login(@RequestBody Account account) throws InvalidLoginException {
        return ResponseEntity.ok(accountService.login(account));
    }

    /**
     * Handler for the <code>/messages</code> <code>POST</code> endpoint.
     * @param message The body of the request containing the message data to be added.
     */
    @PostMapping("/messages")
    private ResponseEntity<Message> postMessage(@RequestBody Message message) throws InvalidMessageTextException , InvalidUserIDException {
        return ResponseEntity.ok(messageService.postMessage(message));
    }

    /**
     * Handler for the <code>/messages</code> <code>GET</code> endpoint.
     */
    @GetMapping("/messages")
    private ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>GET</code> endpoint.
     * @param id The ID of the message to be returned.
     */
    @GetMapping("/messages/{id}")
    private ResponseEntity<Message> getMessageByID(@PathVariable int id) {
        Message message = messageService.getMessageByID(id);
        if (message == null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok(message);
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>DELETE</code> endpoint.
     * @param id The ID of the message to be deleted.
     */
    @DeleteMapping("/messages/{id}")
    private ResponseEntity<Integer> deleteMessageByID(@PathVariable int id) {
        int deletedRows = messageService.deleteMessageByID(id);
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/messages/{id}</code> <code>PATCH</code> endpoint.
     * @param id The ID of the message to be updated.
     * @param message The body of the request containing the message data to be updated.
     */
    @PatchMapping("/messages/{id}")
    private ResponseEntity<Integer> patchMessageByID(@PathVariable int id, @RequestBody Message message) throws InvalidMessageTextException, InvalidMessageIDException {
        return ResponseEntity.ok(messageService.patchMessageByID(id, message));
    }

    /**
     * Handler for the <code>/accounts/{account_id}/messages</code> <code>GET</code> endpoint.
     * @param account_id The ID of the account of whose messsages will be returned.
     */
    @GetMapping("/accounts/{account_id}/messages")
    private ResponseEntity<List<Message>> getAllMessagesByAccountID(@PathVariable int account_id) {
        return ResponseEntity.ok(messageService.getAllMessagesByAccountID(account_id));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>401 Unauthorized</code>.
     * Exception handler for: <code>InvalidLoginException</code>.
     */
    @ExceptionHandler(InvalidLoginException.class)
    private ResponseEntity<Void> invalidLoginExceptionHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidMessageTextException</code>,
     * <code>InvalidMessageIDException</code>,
     * <code>InvalidUserIDException</code>,
     * <code>InvalidUsernameException</code>,
     * <code>InvalidPasswordException</code>.
     */
    @ExceptionHandler({
        InvalidMessageTextException.class,
        InvalidMessageIDException.class,
        InvalidUserIDException.class,
        InvalidUsernameException.class,
        InvalidPasswordException.class
    })
    private ResponseEntity<Void> badRequestExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }

    /**
     * <code>409 Conflict</code>.
     * Exception handler for: <code>UserAlreadyExistsException</code>.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<Void> userAlreadyExistsExceptionHandler() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
