package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
