package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 潘成花
 * @name MessageRepository
 * @description
 * @date 2021/1/27 16:32
 **/
public interface MessageRepository extends JpaRepository<Message, Long> {

}

