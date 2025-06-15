package com.unla.tp_oo2_g16.enums;

public enum RoleType {

    ADMIN,
    USER;

    public String getPrefixedName() {
        return "ROLE_" + this.name();
    }
}
