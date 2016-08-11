package com.pressTheButton.User;

import com.stormpath.sdk.account.Account;

/**
 * Created by Tyler on 2016-08-10.
 */
public class User {
    private final String id;
    private final Account account;

    public User(String id,
                Account account) {
        this.id = id;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public Account getAccount(){
        return account;
    }
}
