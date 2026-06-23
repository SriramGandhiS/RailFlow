package com.railflow.service;

import com.railflow.dto.RouteStopDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.RouteStop;
import com.railflow.repository.RouteStopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteStopService {

    private final RouteStopRepository routeStopRepository;

    @Transactional(readOnly = true)
    public Page<RouteStopDTO> findAll(Pageable pageable) {
        return routeStopRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public RouteStopDTO findById(Long id) {
        return toDTO(routeStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteStop", id)));
    }

    @Transactional
    public RouteStopDTO create(RouteStopDTO dto) {
        log.info("Creating routeStop: {}", dto);
        return toDTO(routeStopRepository.save(toEntity(dto)));
    }

    @Transactional
    public RouteStopDTO update(Long id, RouteStopDTO dto) {
        RouteStop existing = routeStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteStop", id));
        return toDTO(routeStopRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!routeStopRepository.existsById(id))
            throw new ResourceNotFoundException("RouteStop", id);
        routeStopRepository.deleteById(id);
    }

    private RouteStopDTO toDTO(RouteStop e) { return new RouteStopDTO(); }
    private RouteStop toEntity(RouteStopDTO dto) { return new RouteStop(); }
}
