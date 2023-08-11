package com.austin.walletapp.enums;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

class Testing {
    public static void main(String[] args) {
        System.out.println(Roles.USER.getPermissions().stream().map(Objects::toString).collect(Collectors.joining(",")));
    }
}
