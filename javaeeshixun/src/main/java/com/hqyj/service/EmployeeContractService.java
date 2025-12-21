package com.hqyj.service;

import com.hqyj.dto.EmployeeContractDto;
import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.pojo.EmployeeContract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hqyj.pojo.EmployeeFile;
import com.hqyj.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-18 08:38:59
 */
public interface EmployeeContractService extends IService<EmployeeContract> {
    ResultVo add(EmployeeContract emp);
    ResultVo update(EmployeeContract emp);
    ResultVo delete(Integer id);
    ResultVo queryByCondition(EmployeeContractDto dto);
}
