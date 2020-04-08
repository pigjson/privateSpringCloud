package com.test.service;

import com.test.entity.PointLocation;
import com.test.entity.TravelLocation;

import java.util.List;

/**
 * @ClassName EsService
 * @Description: TODO
 * @Author shibo
 * @Date 2020/4/2
 **/
public interface EsService {
    void addOrUpdatePointLocation(List<PointLocation> lists);
    void addOrUpdateTravleLocation(List<TravelLocation> lists);
    void addOrUpdateTravleLocation(TravelLocation travelLocation);
}
