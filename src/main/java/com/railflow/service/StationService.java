package com.railflow.service;

import com.railflow.dto.StationDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Station;
import com.railflow.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationService {

    private final StationRepository stationRepository;

    @Transactional(readOnly = true)
    public Page<StationDTO> findAll(Pageable pageable) {
        return stationRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public StationDTO findById(Long id) {
        return toDTO(stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station", id)));
    }

    @Transactional
    public StationDTO create(StationDTO dto) {
        log.info("Creating station: {}", dto);
        return toDTO(stationRepository.save(toEntity(dto)));
    }

    @Transactional
    public StationDTO update(Long id, StationDTO dto) {
        Station existing = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station", id));
        return toDTO(stationRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!stationRepository.existsById(id))
            throw new ResourceNotFoundException("Station", id);
        stationRepository.deleteById(id);
    }

    private StationDTO toDTO(Station e) { return new StationDTO(); }
    private Station toEntity(StationDTO dto) { return new Station(); }
}
