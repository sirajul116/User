package com.example.sirajul116.user.config;

import com.example.sirajul116.user.entity.CompanyEntity;
import com.example.sirajul116.user.entity.RoleEntity;
import com.example.sirajul116.user.entity.UserEntity;
import com.example.sirajul116.user.entity.UserRoleCompanyEntity;
import com.example.sirajul116.user.repository.CompanyRepository;
import com.example.sirajul116.user.repository.RoleRepository;
import com.example.sirajul116.user.repository.UserRepository;
import com.example.sirajul116.user.repository.UserRoleCompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final UserRoleCompanyRepository userRoleCompanyRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CompanyRepository companyRepository,
                           UserRoleCompanyRepository userRoleCompanyRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.userRoleCompanyRepository = userRoleCompanyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create roles
            RoleEntity adminRole = new RoleEntity();
            adminRole.setRoleName("ADMIN");
            RoleEntity userRole = new RoleEntity();
            userRole.setRoleName("USER");
            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            // Create companies
            CompanyEntity companyA = new CompanyEntity();
            companyA.setCompanyName("Acme Corp");
            CompanyEntity companyB = new CompanyEntity();
            companyB.setCompanyName("Tech Ltd");
            companyRepository.save(companyA);
            companyRepository.save(companyB);

            // Create a user
            UserEntity user = new UserEntity();
            user.setUserName("hridoy");
            user.setPassword(passwordEncoder.encode("password123"));
            user.setEmail("hridoy@example.com");
            userRepository.save(user);

            // Assign roles to user in companies
            UserRoleCompanyEntity assignment1 = new UserRoleCompanyEntity();
            assignment1.setUser(user);
            assignment1.setRole(adminRole);
            assignment1.setCompany(companyA);
            userRoleCompanyRepository.save(assignment1);

            UserRoleCompanyEntity assignment2 = new UserRoleCompanyEntity();
            assignment2.setUser(user);
            assignment2.setRole(userRole);
            assignment2.setCompany(companyB);
            userRoleCompanyRepository.save(assignment2);
        }
    }
}
