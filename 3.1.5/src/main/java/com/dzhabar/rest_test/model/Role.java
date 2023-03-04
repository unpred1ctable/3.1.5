package com.dzhabar.rest_test.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rolename;

    public Role() {
    }

    public Role(String role) {
        this.rolename = role;
    }

    public Role(int id, String role) {
        this.id = id;
        this.rolename = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(rolename, role.rolename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rolename);
    }

    @Override
    public String toString() {
        return rolename;
    }
}