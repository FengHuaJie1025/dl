package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DLRoom;
import com.dl.common.repository.RoomRepository;
import com.dl.mjweb.service.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void delete(int id) {
        if (!roomRepository.existsById(id)){
            log.debug("DLRoom doesn`t exist this id: "+id);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public DLRoom getById(int id) {
        if (!roomRepository.existsById(id)){
            log.debug("DLRoom doesn`t exist this id: "+id);
            return null;
        }
        return roomRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DLRoom> spec, Pageable pageable) {
        Page<DLRoom> page = roomRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DLRoom bean) {
        roomRepository.save(bean);
    }

    @Override
    public void update(DLRoom bean) {
        roomRepository.save(bean);
    }

    @Override
    public List<DLRoom> findAll() {
        return roomRepository.findAll();
    }
}
