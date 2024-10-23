package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;

/**
 * A service for handling the <code>Account</code> business logic.
 */
@Service
public class AccountService {
    public AccountRepository accountRepository;

    /**
     * Constructor for the account service.
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Register a user with a given account if the account does not exist.
     * @param account The account of the user to register.
     * @return The account of the registered user.
     * @throws InvalidUsernameException <code>account.username</code> is blank.
     * @throws InvalidPasswordException <code>account.password</code> is less than 4 characters long.
     * @throws UserAlreadyExistsException a user with username <code>account.username</code> already exists.
     */
    public Account register(Account account) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        if (account.getUsername().length() <= 0)
            throw new InvalidUsernameException();
        if (account.getPassword().length() < 4)
            throw new InvalidPasswordException();
        if (accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new UserAlreadyExistsException();
        return accountRepository.save(account);
    }

    /**
     * Login a user with a given account.
     * @param account The account of the user to login.
     * @return The account of the logged in user.
     * @throws InvalidLoginException <code>account.username</code> and <code>account.password</code> do not make a valid login.
     */
    public Account login(Account account) throws InvalidLoginException {
        // If the username and password provided in the request body JSON match a real account existing on the database.
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).orElseThrow(() -> new InvalidLoginException());
    }
}
