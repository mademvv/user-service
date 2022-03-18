package com.microservices.user.service;

import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRespository;
import com.microservices.user.vo.Department;
import com.microservices.user.vo.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser Method of UserService");
        return userRespository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment Method of UserService");
        ResponseTemplateVO responseTemplateVO=new ResponseTemplateVO();
        User user=userRespository.findByUserId(userId);

        Department department=restTemplate.getForObject(
                "http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),Department.class);

        responseTemplateVO.setUser(user);
        responseTemplateVO.setDepartment(department);
        return  responseTemplateVO;
    }
}
