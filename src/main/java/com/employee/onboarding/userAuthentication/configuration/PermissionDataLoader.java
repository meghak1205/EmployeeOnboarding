package com.employee.onboarding.userAuthentication.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.employee.onboarding.userAuthentication.entity.Permission;
import com.employee.onboarding.userAuthentication.repository.PermissionRepo;

@Configuration
public class PermissionDataLoader {

	@Bean
    public CommandLineRunner loadData(PermissionRepo permissionRepository) {
        return args -> {
            if (!permissionRepository.existsByPermissionName("READ")) {
                Permission readPermission = new Permission("READ", "Permission to read data");
                permissionRepository.save(readPermission);
            }
            
            if (!permissionRepository.existsByPermissionName("WRITE")) {
                Permission writePermission = new Permission("WRITE", "Permission to write data");
                permissionRepository.save(writePermission);
            }
        };
    }
}
