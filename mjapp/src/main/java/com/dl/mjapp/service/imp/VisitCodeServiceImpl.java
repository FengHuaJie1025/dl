package com.dl.mjapp.service.imp;

import com.dh.buildlock.pub.DHDataProcessPub;
import com.dl.common.model.entity.DLGate;
import com.dl.common.model.entity.DLRoom;
import com.dl.common.model.entity.DlVisiteCode;
import com.dl.common.repository.GateRepository;
import com.dl.common.repository.RoomRepository;
import com.dl.common.repository.VisitCodeRepository;
import com.dl.common.utils.QueryUtil;
import com.dl.common.utils.SearchFilter;
import com.dl.mjapp.service.IOpenlogService;
import com.dl.mjapp.service.IVisitCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class VisitCodeServiceImpl implements IVisitCodeService {

    @Autowired
    private VisitCodeRepository repository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GateRepository gateRepository;

    @Override
    public List<DlVisiteCode> findAll(String phone) {
        Collection<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("vPhone",SearchFilter.Operator.EQ,phone));
        Specification<DlVisiteCode> spec = QueryUtil.bySearchFilter(filters);
        List<DlVisiteCode> list = repository.findAll(spec);
        return list;
    }

    @Override
    public DlVisiteCode generateVisitCode(DlVisiteCode code) {
        String visitCode = "";
        DHDataProcessPub process = DHDataProcessPub.lockInit();
        Optional<DLRoom> roomOptional = roomRepository.findById(code.getURoomId());
        if (roomOptional.isPresent()){
            Integer gateid = roomOptional.get().getGateid();
            Optional<DLGate> gateOptional = gateRepository.findById(gateid);
            if (gateOptional.isPresent()){
                Integer areaid = gateOptional.get().getAreaid();
                visitCode = process.generateVisitCodeWithIds("", "", new Date(), areaid, gateid & 0xFF, 1, 1, 7);
            }
        }
        code.setVCode(visitCode);
        return code;
    }
}
