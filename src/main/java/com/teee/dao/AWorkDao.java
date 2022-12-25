package com.teee.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teee.domain.works.AWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Xu ZhengTao
 */
@Mapper
public interface AWorkDao extends BaseMapper<AWork> {
    @Select("select id from work_table where cid=#{cid}")
    List<Integer> getWorkByCid(@Param("cid") Integer cid);
}
