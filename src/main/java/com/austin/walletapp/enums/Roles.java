package com.austin.walletapp.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.austin.walletapp.enums.UserPermissions.*;

public enum Roles {
    ADMIN(Sets.newHashSet(USER_READ, USER_EDIT, ACCOUNT_EDIT, ACCOUNT_READ)),
    USER(Sets.newHashSet(USER_READ, USER_EDIT));

    private final Set<UserPermissions> permissions;

    Roles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }
}
