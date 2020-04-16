package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlUserCard;
import com.dl.common.repository.CardRepository;
import com.dl.mjweb.service.ICardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void delete(int id) {
        if (!cardRepository.existsById(id)){
            log.debug("DlUserCard doesn`t exist this id: "+id);
        }
        cardRepository.deleteById(id);
    }

    @Override
    public DlUserCard getById(int id) {
        if (!cardRepository.existsById(id)){
            log.debug("DlUserCard doesn`t exist this id: "+id);
            return null;
        }
        return cardRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlUserCard> spec, Pageable pageable) {
        Page<DlUserCard> page = cardRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlUserCard bean) {
        cardRepository.save(bean);
    }

    @Override
    public void update(DlUserCard bean) {
        cardRepository.save(bean);
    }

    @Override
    public List<DlUserCard> findAll() {
        return cardRepository.findAll();
    }
}
