package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLRoom;
import com.dl.common.repository.RoomRepository;
import com.dl.mjapp.service.IRoomService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<DLRoom> findAll(Specification<DLRoom> spec) {
        return roomRepository.findAll(spec);
    }

    @Override
    public DLRoom getById(int id) {
        Optional<DLRoom> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()){
            return roomOptional.get();
        };
        return null;
    }

    @Override
    public List<DLRoom> findAllById(Set<Integer> roomids) {
        List<DLRoom> list = roomRepository.findAllById(roomids);
        return list;
    }
}
