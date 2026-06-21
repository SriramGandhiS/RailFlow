package com.railflow.service;

import com.railflow.dto.TrainDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Train;
import com.railflow.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainService {

    private final TrainRepository trainRepository;

    @Transactional(readOnly = true)
    public Page<TrainDTO> findAll(Pageable pageable) {
        return trainRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public TrainDTO findById(Long id) {
        return toDTO(trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id)));
    }

    @Transactional
    public TrainDTO create(TrainDTO dto) {
        log.info("Creating train: {}", dto);
        return toDTO(trainRepository.save(toEntity(dto)));
    }

    @Transactional
    public TrainDTO update(Long id, TrainDTO dto) {
        Train existing = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id));
        return toDTO(trainRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!trainRepository.existsById(id))
            throw new ResourceNotFoundException("Train", id);
        trainRepository.deleteById(id);
    }

    private TrainDTO toDTO(Train e) { return new TrainDTO(); }
    private Train toEntity(TrainDTO dto) { return new Train(); }
}
