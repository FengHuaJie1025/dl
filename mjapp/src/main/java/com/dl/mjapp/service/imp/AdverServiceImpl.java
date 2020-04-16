package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLAdver;
import com.dl.common.repository.AdverRepository;
import com.dl.common.repository.AreaRepository;
import com.dl.mjapp.service.IAdverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdverServiceImpl implements IAdverService {

    @Autowired
    private AdverRepository repository;

    @Override
    public List<DLAdver> findAll(Specification<DLAdver> spec) {
        List<DLAdver> areas = repository.findAll(spec);
        return areas;
    }

    @Override
    public DLAdver getById(int id) {
        DLAdver adver = repository.findById(id).get();
        return adver;
    }

    @Override
    public DLAdver getAdverByAreaId(Integer areaid) {
        List<DLAdver> advers = getAdversByAreaId(areaid);
        DLAdver adver = getAdByWeight(advers);
        return adver;
    }

    public List<DLAdver> getAdversByAreaId(Integer areaid) {
        return repository.findByAreaid(areaid);
    }

    public DLAdver getAdByWeight(List<DLAdver> advers) {
        //从未展示过的广告，权重+10
        //今天未展示过的广告，权重+5
        //曾经浏览过广告，3秒以上的每次+5
        //根据广告商给不同权重
        return null;
    }
}
