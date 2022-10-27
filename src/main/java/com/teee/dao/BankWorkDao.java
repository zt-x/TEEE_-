package com.teee.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teee.domain.works.BankQuestion;
import com.teee.domain.works.BankWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BankWorkDao extends BaseMapper<BankWork> {
}
