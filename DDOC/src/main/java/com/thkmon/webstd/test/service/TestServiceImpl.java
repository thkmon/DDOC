package com.thkmon.webstd.test.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thkmon.webstd.test.dao.TestDao;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Resource(name="testDao")
    private TestDao testDao;
    
    public int getTestCount() throws Exception {
        return testDao.selectCount();
    }
}