package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from sys_user")
//    List<User> findAll_user();
//
//    @Insert("insert into sys_user(username,password,email,phone,address) values " +
//            "(#{username}, #{password}, #{email}, #{phone}, #{address})")
//    int insert_user(User user);
//
//    @Update("update sys_user set username = #{username}, password = #{password}, email = #{email}," +
//            "phone = #{phone}, address = #{address} where id = #{id}")
//    int update_user(User user);
//
//    @Delete("delete from sys_user where id = #{id}")
//    Integer deleteById_user(@Param("id") Integer id);
//
//    @Select("select * from sys_user where username like concat('%', #{username}, '%')" +
//            " limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String username);
//
//    @Select("select count(*) from sys_user where username like concat('%', #{username}, '%')")
//    Integer selectTotal(String username);
}
