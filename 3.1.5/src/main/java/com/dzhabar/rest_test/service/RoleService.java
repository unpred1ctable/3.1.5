package com.dzhabar.rest_test.service;

import com.dzhabar.rest_test.model.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);

    List<Role> getListRoles();

    List<Role> getRolesListById(List<Integer> id);

    Role getRoleById(int id);
}
