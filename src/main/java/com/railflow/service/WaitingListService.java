package com.railflow.service;

import com.railflow.dto.WaitingListDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.WaitingList;
import com.railflow.repository.WaitingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaitingListService {

    private final WaitingListRepository waitingListRepository;

    @Transactional(readOnly = true)
    public Page<WaitingListDTO> findAll(Pageable pageable) {
        return waitingListRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public WaitingListDTO findById(Long id) {
        return toDTO(waitingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WaitingList", id)));
    }

    @Transactional
    public WaitingListDTO create(WaitingListDTO dto) {
        log.info("Creating waitingList: {}", dto);
        return toDTO(waitingListRepository.save(toEntity(dto)));
    }

    @Transactional
    public WaitingListDTO update(Long id, WaitingListDTO dto) {
        WaitingList existing = waitingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WaitingList", id));
        return toDTO(waitingListRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!waitingListRepository.existsById(id))
            throw new ResourceNotFoundException("WaitingList", id);
        waitingListRepository.deleteById(id);
    }

    private WaitingListDTO toDTO(WaitingList e) { return new WaitingListDTO(); }
    private WaitingList toEntity(WaitingListDTO dto) { return new WaitingList(); }
}
