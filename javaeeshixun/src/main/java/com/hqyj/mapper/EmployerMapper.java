package com.hqyj.mapper;

import com.hqyj.pojo.Employer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqyj.pojo.EmployerAge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-16 03:59:23
 */
@Mapper
public interface EmployerMapper extends BaseMapper<Employer> {
   List<EmployerAge> queryAge();

   Employer findByEmployeeName(String employeeName);
}
