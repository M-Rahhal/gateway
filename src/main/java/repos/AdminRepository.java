package repos;

import entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin , Long> {
    public Optional<Admin> findByUserName(String userName);

}
