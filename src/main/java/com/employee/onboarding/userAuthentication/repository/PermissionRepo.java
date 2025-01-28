package com.employee.onboarding.userAuthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.onboarding.userAuthentication.entity.Permission;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {

	boolean existsByPermissionName(String permissionName);

	List<Permission> findByPermissionNameIn(List<String> permissionNames);
	
	@Query("SELECT p FROM Permission p WHERE p.id IN :ids")
	List<Permission> findAllById(List<Long> ids);
}
