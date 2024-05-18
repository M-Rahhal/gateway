package com.streamPatform.gateway.repos;

import com.streamPatform.gateway.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin , Long> {
    public Optional<Admin> findByUserName(String userName);

}
