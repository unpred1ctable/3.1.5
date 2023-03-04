package com.dzhabar.rest_test.repositories;

import com.dzhabar.rest_test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
