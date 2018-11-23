package com.moon.mapper;

import com.moon.model.email.TbMailModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Author : CaoXm
 * Date  : 2018/11/23 10:36
 * Description : Interface for
 */
public interface MailMapper {

    @Insert("insert into tb_mail(user_id,mail,code,create_time) " +
            "values(#{userId,jdbcType=INTEGER},#{mail,jdbcType=VARCHAR},#{code,jdbcType=VARCHAR},now())")
    int addNewMailCheck(TbMailModel model);

    @Select("select * from tb_mail where user_id=#{id} and code=#{uuid} and status=0")
    TbMailModel findMailByUserIdAndCode(@Param("id") Integer id, @Param("uuid") String uuid);

    @Update("update tb_mail set update_time = now(),status= 1 where user_id=#{id}")
    int activeSuccess(@Param("id") Integer id);
}
