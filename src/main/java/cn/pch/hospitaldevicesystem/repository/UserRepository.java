package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    /**
     * 查询发给某个人的所有短信
     *  联表查询实例
     *  nativeQuery = true配置查询方式，true表示Sql查询，false表示Jpql查询
     *  注意：（注：存储过程不能select * ）
     */
    @Query(value = "select user.id,username from user inner join message on user.id = message.to_user_id and user.id = :id ",nativeQuery = true)
    List<Object> findAllMessageByUserId(@Param("id")Long id);

    /*
        根据权限查询
    */
    List<User> findAllByRoleAndDelete(String role,Integer delete);

}
