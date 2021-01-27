package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wanggang317
 * @name MessageRepository
 * @description
 * @date 2021/1/27 16:32
 **/
public interface MessageRepository extends CrudRepository<Message, Long> {

}

