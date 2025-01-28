package com.employee.onboarding.userAuthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.onboarding.userAuthentication.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

	@Query("SELECT COUNT(u) > 0 FROM Role u WHERE u.name = :roleName")
	Boolean existsByRoleName(String roleName);
	
	@Query("SELECT u FROM Role u WHERE u.name = :roleName")
	Role findByName(String roleName);
	
//	@Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.name = :roleName")
//	Role findByPermissionName(String roleName);
}
