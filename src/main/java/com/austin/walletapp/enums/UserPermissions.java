package com.austin.walletapp.enums;

public enum UserPermissions {
    USER_READ("user:read"),
    USER_EDIT("user:write"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_EDIT("account:edit"),
    ACCOUNT_WITHDRAW("account:withdraw"),
    ACCOUNT_DEPOSIT("account:deposit");

    private final String permission;
    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
