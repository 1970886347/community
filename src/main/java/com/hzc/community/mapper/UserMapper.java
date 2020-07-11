package com.hzc.community.mapper;

import com.hzc.community.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    void insert(UserModel user);
    void delete(long accountId);
    UserModel select(String token);
    @Select("select * from user where id=#{id}")
    UserModel selectById(Integer id);
    @Update("update user set avatar_url=#{newUrl} where account_id=#{id}")
    void updateAvatarUrl(@Param("newUrl") String newUrl, @Param("id") Long id);
    UserModel selectByAccount(@Param("accountId")Long accountId);
}
