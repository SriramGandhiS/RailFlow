package com.railflow.service;

import com.railflow.dto.DistributedLockDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.DistributedLock;
import com.railflow.repository.DistributedLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistributedLockService {

    private final DistributedLockRepository distributedLockRepository;

    @Transactional(readOnly = true)
    public Page<DistributedLockDTO> findAll(Pageable pageable) {
        return distributedLockRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public DistributedLockDTO findById(Long id) {
        return toDTO(distributedLockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DistributedLock", id)));
    }

    @Transactional
    public DistributedLockDTO create(DistributedLockDTO dto) {
        log.info("Creating distributedLock: {}", dto);
        return toDTO(distributedLockRepository.save(toEntity(dto)));
    }

    @Transactional
    public DistributedLockDTO update(Long id, DistributedLockDTO dto) {
        DistributedLock existing = distributedLockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DistributedLock", id));
        return toDTO(distributedLockRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!distributedLockRepository.existsById(id))
            throw new ResourceNotFoundException("DistributedLock", id);
        distributedLockRepository.deleteById(id);
    }

    private DistributedLockDTO toDTO(DistributedLock e) { return new DistributedLockDTO(); }
    private DistributedLock toEntity(DistributedLockDTO dto) { return new DistributedLock(); }
}
