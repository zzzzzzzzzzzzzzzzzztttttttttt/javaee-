package com.hqyj.service;

import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.dto.EmployerDto;
import com.hqyj.pojo.EmployeeFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hqyj.pojo.Employer;
import com.hqyj.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-18 02:59:36
 */
public interface EmployeeFileService extends IService<EmployeeFile> {
    ResultVo add(EmployeeFile emp);
    ResultVo update(EmployeeFile emp);
    ResultVo delete(Integer id);
    ResultVo queryByCondition(EmployeeFileDto dto);
}
