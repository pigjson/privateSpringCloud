package com.test.service.Impl;

import com.test.elasticsearch.ESClient;
import com.test.entity.PointLocation;
import com.test.entity.TravelLocation;
import com.test.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EsServiceImpl
 * @Description: TODO
 * @Author shibo
 * @Date 2020/4/2
 **/
@Service
public class EsServiceImpl implements EsService {
    @Autowired
    private ESClient esClient;
    @Override
    public void addOrUpdatePointLocation(List<PointLocation> lists) {
        try {
            esClient.batchSaveOrUpdate(lists);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addOrUpdateTravleLocation(List<TravelLocation> lists) {
        try {
            esClient.batchSaveOrUpdate(lists);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void addOrUpdateTravleLocation(TravelLocation travelLocation) {
        try {
            esClient.saveOrUpdate(travelLocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
