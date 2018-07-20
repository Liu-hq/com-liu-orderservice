package com.liu.orderservice.mapper;

import com.liu.orderservice.domain.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/20.
 */
public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

}
