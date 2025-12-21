package com.hqyj.service;

import com.hqyj.dto.EmployerDto;
import com.hqyj.pojo.Employer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hqyj.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-16 03:59:23
 */
public interface EmployerService extends IService<Employer> {
ResultVo add(Employer emp);
ResultVo update(Employer emp);
ResultVo delete(Integer id);
ResultVo queryByCondition(EmployerDto dto);
ResultVo queryAge();

    Employer findByEmployeeName(String employeeName);
}
