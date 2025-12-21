package com.hqyj.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dto.EmployeeContractDto;
import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.pojo.EmployeeContract;
import com.hqyj.mapper.EmployeeContractMapper;
import com.hqyj.pojo.EmployeeFile;
import com.hqyj.pojo.Employer;
import com.hqyj.service.EmployeeContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqyj.service.EmployerService;
import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-18 08:38:59
 */
@Service
public class EmployeeContractServiceImpl extends ServiceImpl<EmployeeContractMapper, EmployeeContract> implements EmployeeContractService {
@Autowired
private EmployeeContractMapper employeeContractMapper;
@Autowired
private EmployerService employerService;
    @Override
    public ResultVo add(EmployeeContract emp) {

        if (emp.getEmployeeName()==null){
            return ResultVo.fail("请输入员工名称",null);
        }
        Employer emp1=employerService.findByEmployeeName(emp.getEmployeeName());
        if(emp1==null){
            return ResultVo.fail("该员工不存在",null);
        }
        emp.setEmployeeId(emp1.getEmpId());
        int num=this.baseMapper.insert(emp);
        if(num<=0){
            return ResultVo.fail("新增失败",null);
        }

        return ResultVo.success("新增成功",null);
    }

    @Override
    public ResultVo update(EmployeeContract emp) {
        if (emp.getEmployeeName()==null){
            return ResultVo.fail("请输入员工名称",null);
        }
        Employer emp1=employerService.findByEmployeeName(emp.getEmployeeName());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
        if(emp1==null){
            return ResultVo.fail("该员工不存在",null);
        }
        int num=this.baseMapper.updateById(emp);
        if(num<=0){
            return ResultVo.fail("修改失败",null);
        }
        return ResultVo.success("修改成功",null);
    }

    @Override
    public ResultVo delete(Integer id) {
        int num=this.baseMapper.deleteById(id);
        if(num<=0){
            return ResultVo.fail("删除失败",null);
        }
        return ResultVo.success("删除成功",null);
    }

    @Override
    public ResultVo queryByCondition(EmployeeContractDto dto) {
        boolean isEmpty = null != dto.getEmployeeName() && !dto.getEmployeeName().equals("");
        QueryWrapper<EmployeeContract> queryWrapper = new QueryWrapper<>();
        // 姓名模糊查询
        queryWrapper.like(isEmpty, "employee_name", dto.getEmployeeName());
        // 合同类型查询
        boolean isEmpDep = null != dto.getEmployeeId() && !dto.getEmployeeId().equals("");
        queryWrapper.like(isEmpDep, "employee_id", dto.getEmployeeId());

        // ========== 新增时间排序逻辑 ==========
        if (dto.getDate() != null) {
            switch (dto.getDate()) {
                case 0: // 按合同起始升序
                    queryWrapper.orderByAsc("start_date");
                    break;
                case 1: // 按合同起始降序
                    queryWrapper.orderByDesc("start_date");
                    break;
                case 2: // 按合同结束升序
                    queryWrapper.orderByAsc("end_date");
                    break;
                case 3: // 按合同结束降序
                    queryWrapper.orderByDesc("end_date");
                    break;
                default:
                    // 默认按开始日期降序
                    queryWrapper.orderByDesc("start_date");
                    break;
            }
        } else {
            // 无排序参数时，默认按开始日期降序
            queryWrapper.orderByDesc("start_date");
        }

        // 分页对象
        Page<EmployeeContract> page = new Page<>(dto.getPage(), dto.getRow());
        // 查询分页
        Page<EmployeeContract> pageList = employeeContractMapper.selectPage(page, queryWrapper);

        if (pageList.getRecords().size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageList.getRecords());
            map.put("total", pageList.getTotal());
            map.put("totalPage", pageList.getPages());
            return ResultVo.success("查询成功", map);
        }
        return ResultVo.fail("未找到匹配的合同", null);
    }
}
