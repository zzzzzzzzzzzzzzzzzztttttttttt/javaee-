package com.hqyj.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.dto.EmployerDto;
import com.hqyj.pojo.EmployeeFile;
import com.hqyj.mapper.EmployeeFileMapper;
import com.hqyj.pojo.Employer;
import com.hqyj.service.EmployeeFileService;
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
 * @since 2025-12-18 02:59:36
 */
@Service
public class EmployeeFileServiceImpl extends ServiceImpl<EmployeeFileMapper, EmployeeFile> implements EmployeeFileService {
@Autowired
private EmployeeFileMapper employeeFileMapper;
@Autowired
private EmployerService employerService;
    @Override
    public ResultVo add(EmployeeFile emp) {
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
    public ResultVo update(EmployeeFile emp) {
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
    public ResultVo queryByCondition(EmployeeFileDto dto) {
        boolean isEmpty=null!=dto.getEmployeeName()&&!dto.getEmployeeName().equals("");
        QueryWrapper<EmployeeFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(isEmpty,"employee_name",dto.getEmployeeName());
        boolean isEmpDep=null!=dto.getArchiveType()&&!dto.getArchiveType().equals("");
        queryWrapper.like(isEmpDep,"archive_type",dto.getArchiveType());
        //分页对象
        Page<EmployeeFile> page=new Page<>(dto.getPage(),dto.getRow());
        //查询分页
        Page<EmployeeFile> pageList=employeeFileMapper.selectPage(page,queryWrapper);

        if(pageList.getRecords().size()>0){
            Map<String,Object> map=new HashMap<>();
            map.put("list",pageList.getRecords());
            map.put("total",pageList.getTotal());
            map.put("totalPage",pageList.getPages());
            return ResultVo.success("查询成功",map);
        }
        return ResultVo.fail("未找到匹配的档案",null);
    }
}
