package com.yao.cache.service;


import com.yao.cache.entity.Student;
import com.yao.cache.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 属性：
     *     cacheNames default {}; 【指定缓存组件的名字】
     *
     *     key default "";  【 缓存数据指定的key：默认是方法的参数
     *       1、可以是方法的返回值. 编写SpEl ; #id参数id的值， #a0 ,#root.args[0] 】
     *
     *      keyGenerator default "";  【key的生成器，可以自己指定key的生成器指定id】  key/keyGenerator ;二选一使用
     *
     *     cacheManager default ""; 指定缓存管理器
     *    condition default "";  给定条件，满足才缓存  【condition="#id>0"】
     *
     *    unless default "";  与condition相反
     *
     *     sync default false;  【是否开启异步模式，默认false】
     *
     *     自动配置类： CacheAutoConfiguration
     * @param id
     * @return
     */

    @Cacheable(value = "stu")
    public Student getById(Integer id) {

        System.out.println("查询" + id + "学生");
        return studentMapper.getById(id);
    }

    /**
     * CachePut: 既调用方法，又更新缓存数据
     * 1、先调用目标方法
     * 2、将目标方法的结果缓存起来
     * <p>
     * 测试步骤：
     * 1、查询1号学生，查到的结果会放到缓存中
     * key:1 value :  name 赵云
     * 2、以后查询还是之前的结果
     * 3、更新1号学生：
     * 将方法的返回值也放入缓存了
     * key: 传入的student对象 值 ： 返回的 student对象；
     * 4、查询1号学生？【 如果不加key="#result.id 或者 student.id" 则更新完后再次查询显示的还是更新前的数据，缓存也更新了，但是key不一样，默认的key是student,而更新前的key是student.id】
     * 因此要一致的话，应该是更新后的学生；
     * 【key = "#student.id"使用传入参数的学生id
     * key ="#result.id" 使用返回结果student的id;】
     *
     * @param student
     * @return
     * @Cacheable的key不能用#result
     */
    @CachePut(value = "stu", key = "#result.id")
    public Student updateStu(Student student) {
        System.out.println("updateStudent:" + student);
        studentMapper.updateStu(student);
        return student;
    }

    /**
     * CacheEvict: 缓存清除
     * key： 指定要删除的缓存
     * allEntries = true 删除这个缓存中所有的数据
     * 【 boolean allEntries() default false; 默认是false】
     * 【 boolean beforeInvocation() default false; 缓存的清除是否在方法之前执行，默认是false】
     *
     * @param id
     */
    @CacheEvict(value = "stu",beforeInvocation = true/* allEntries = true*/)/*key="#id"*/
    public void delStu(Integer id) {
        System.out.println("delStu" + id);
        //由于数据库数据少，这里不删除数据库数据，模拟一下就好
        // studentMapper.deleByid(id);
         //错误，缓存清除失败，如果加
        //【beforeInvocation() 方法之前执行，这样无论方法是否执行完都清除缓存】
        int i = 10 / 0; //除0异常
    }


    /**
     * Caching 用于复杂缓存处理
     * 【Cacheable[] cacheable() default {};
     *     CachePut[] put() default {};
     *     CacheEvict[] evict() default {};】
     */
    @Caching(
            cacheable = {
                    @Cacheable(value = "stu",key = "#name")
            },
            put = {
                    @CachePut(value = "stu",key="#result.age"),
                    @CachePut(value = "stu",key = "#result.id")
            }
    )
    public Student getByName(String name){
        Student byName = studentMapper.getByName(name);
        return byName;
    }
}
