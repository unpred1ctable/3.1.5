package com.dzhabar.rest_test.service;

import com.dzhabar.rest_test.model.Role;
import com.dzhabar.rest_test.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getListRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesListById(List<Integer> id) {
        return roleRepository.findAllById(id);
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.getById(id);
    }
}
