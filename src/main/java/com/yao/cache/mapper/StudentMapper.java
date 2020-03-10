package com.yao.cache.mapper;

import com.yao.cache.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentMapper {

    @Select("select * from student where id=#{id}")
    Student getById(Integer id);

    @Update("UPDATE student set age=#{age} ,name=#{name} where id=#{id}")
    void updateStu(Student student);

    @Delete("DELETE FROM student where id=#{id}")
    void deleByid(Integer id);


    @Select("select * from student where name=#{name}")
    Student getByName(String name);
}
