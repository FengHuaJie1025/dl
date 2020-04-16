package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlUserRoom;
import com.dl.common.repository.DlUserRoomRepository;
import com.dl.mjweb.service.IDlUserRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DlUserRoomServiceImpl implements IDlUserRoomService {

    @Autowired
    private DlUserRoomRepository userRoomRepository;

    @Override
    public void delete(int id) {
        if (!userRoomRepository.existsById(id)){
            log.debug("DlUserRoom doesn`t exist this id: "+id);
        }
        userRoomRepository.deleteById(id);
    }

    @Override
    public DlUserRoom getById(int id) {
        if (!userRoomRepository.existsById(id)){
            log.debug("DlUserRoom doesn`t exist this id: "+id);
            return null;
        }
        return userRoomRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlUserRoom> spec, Pageable pageable) {
        Page<DlUserRoom> page = userRoomRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlUserRoom bean) {
        userRoomRepository.save(bean);
    }

    @Override
    public void update(DlUserRoom bean) {
        userRoomRepository.save(bean);
    }

    @Override
    public List<DlUserRoom> findAll() {
        return userRoomRepository.findAll();
    }
}
