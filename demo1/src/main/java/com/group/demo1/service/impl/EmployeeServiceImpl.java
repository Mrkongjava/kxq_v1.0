package com.group.demo1.service.impl;

import com.group.demo1.entity.Employee;
import com.group.demo1.mapper.EmployeeMapper;
import com.group.demo1.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kxq
 * @since 2019-11-08
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
