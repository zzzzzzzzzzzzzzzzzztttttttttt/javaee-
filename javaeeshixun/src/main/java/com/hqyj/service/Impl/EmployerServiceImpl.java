package com.hqyj.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.dto.EmployerDto;
import com.hqyj.pojo.Employer;
import com.hqyj.mapper.EmployerMapper;
import com.hqyj.pojo.EmployerAge;
import com.hqyj.service.EmployerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-16 03:59:23
 */
@Service
public class EmployerServiceImpl extends ServiceImpl<EmployerMapper, Employer> implements EmployerService {
    @Autowired
    private EmployerMapper employerMapper;

    @Override
    public ResultVo add(Employer emp) {
        int num=employerMapper.insert(emp);
        if(num<=0){
            return ResultVo.fail("新增失败",null);
        }
        return ResultVo.success("新增成功",null);
    }

    @Override
    public ResultVo update(Employer emp) {
        int num=employerMapper.updateById(emp);
        if(num<=0){
            return ResultVo.fail("修改失败",null);
        }
        return ResultVo.success("修改成功",null);

    }

    @Override
    public ResultVo delete(Integer id) {
        int num=employerMapper.deleteById(id);
        if(num<=0){
            return ResultVo.fail("删除失败",null);
        }
        return ResultVo.success("删除成功",null);
    }

    @Override
    public ResultVo queryByCondition(EmployerDto dto) {
        //判断用户是否输入
        boolean isEmpty=null!=dto.getEmpName()&&!dto.getEmpName().equals("");
        QueryWrapper<Employer> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(isEmpty,"emp_name",dto.getEmpName());
        boolean isEmpDep=null!=dto.getEmpDepartment()&&!dto.getEmpDepartment().equals("");
        queryWrapper.like(isEmpDep,"emp_department",dto.getEmpDepartment());
        //分页对象
        Page<Employer> page=new Page<>(dto.getPage(),dto.getRow());
        //查询分页
        Page<Employer> pageList=employerMapper.selectPage(page,queryWrapper);

        if(pageList.getRecords().size()>0){
            Map<String,Object> map=new HashMap<>();
            map.put("list",pageList.getRecords());
            map.put("total",pageList.getTotal());
            map.put("totalPage",pageList.getPages());
            return ResultVo.success("查询成功",map);
        }
        return ResultVo.fail("未找到该员工",null);
    }

    @Override
    public ResultVo queryAge() {
        List<EmployerAge> list=employerMapper.queryAge();
        if(list.size()>0){
            return ResultVo.success("查询成功",list);
        }
        return ResultVo.fail("查询失败",null);
    }

    @Override
    public Employer findByEmployeeName(String employeeName) {
        Employer employer=employerMapper.findByEmployeeName(employeeName);
        return employer;
    }
}
