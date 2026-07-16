package com.railflow.service;

import com.railflow.dto.WaitingListPromotionDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.WaitingListPromotion;
import com.railflow.repository.WaitingListPromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaitingListPromotionService {

    private final WaitingListPromotionRepository waitingListPromotionRepository;

    @Transactional(readOnly = true)
    public Page<WaitingListPromotionDTO> findAll(Pageable pageable) {
        return waitingListPromotionRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public WaitingListPromotionDTO findById(Long id) {
        return toDTO(waitingListPromotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WaitingListPromotion", id)));
    }

    @Transactional
    public WaitingListPromotionDTO create(WaitingListPromotionDTO dto) {
        log.info("Creating waitingListPromotion: {}", dto);
        return toDTO(waitingListPromotionRepository.save(toEntity(dto)));
    }

    @Transactional
    public WaitingListPromotionDTO update(Long id, WaitingListPromotionDTO dto) {
        WaitingListPromotion existing = waitingListPromotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WaitingListPromotion", id));
        return toDTO(waitingListPromotionRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!waitingListPromotionRepository.existsById(id))
            throw new ResourceNotFoundException("WaitingListPromotion", id);
        waitingListPromotionRepository.deleteById(id);
    }

    private WaitingListPromotionDTO toDTO(WaitingListPromotion e) { return new WaitingListPromotionDTO(); }
    private WaitingListPromotion toEntity(WaitingListPromotionDTO dto) { return new WaitingListPromotion(); }
}
