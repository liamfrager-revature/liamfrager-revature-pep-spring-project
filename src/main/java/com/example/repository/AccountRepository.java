package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

/**
 * A JPA repository for accounts.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Get the account with the given username.
     * @param username The username of the account to return.
     * @return An <code>Optional</code> of the user's account information.
     */
    Optional<Account> findByUsername(String username);

    /**
     * Get the account with the given username and password.
     * @param username The username of the account to return.
     * @param password The password of the account to return.
     * @return An <code>Optional</code> of the user's account information.
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
