package com.example.sirajul116.user.repository;

import com.example.sirajul116.user.entity.UserRoleCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleCompanyRepository extends JpaRepository<UserRoleCompanyEntity, Long> {
}
