package com.candy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candy.dao.IRedisDao;
import com.candy.dao.RedisResult;
import com.candy.dao.RedisServerEntity;
import com.candy.service.patrol.RedisPatroller;

@Service
public class RedisService {

    @Autowired
    private RedisPatroller rp;

    @Autowired
    private IRedisDao rDao;

    public List<RedisResult> healthInfo() {
        return rDao.listResult();
    }

    /**
     * 将先前保存的健康信息都取出来
     * @return
     */
    public List<RedisResult> healthHistoryInfo(String id) {
        return rDao.listHistoryResult(id);
    }

    public String addServer(RedisServerEntity rse) {
        return rDao.addServer(rse);
    }

    public void modifyServer(RedisServerEntity rse) {
        rDao.updateServer(rse);
    }

    public void deleteServer(String id) {
        // 1.删除配置数据
        rDao.deleteServer(id);
        // 2.删除链接数据
        rp.deleteClient(id);
    }

    public void pubRedisTask(OperationTask ot) {
        rDao.addTask(ot);
    }

    public void deleteResult(String id) {
        rDao.deleteResult(id);
    }

}
