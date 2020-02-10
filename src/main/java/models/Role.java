package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Роль для доступа к тем или иным действиям.
 */
public class Role {

    @JsonProperty("role_name")
    private String name;
    @JsonProperty("role_main")
    private boolean mainPermission;
    @JsonProperty("role_admin")
    private boolean adminPermission;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMainPermission() {
        return mainPermission;
    }

    public void setMainPermission(boolean mainPermission) {
        this.mainPermission = mainPermission;
    }

    public boolean isAdminPermission() {
        return adminPermission;
    }

    public void setAdminPermission(boolean adminPermission) {
        this.adminPermission = adminPermission;
    }
}