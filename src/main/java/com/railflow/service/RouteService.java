package com.railflow.service;

import com.railflow.dto.RouteDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Route;
import com.railflow.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteService {

    private final RouteRepository routeRepository;

    @Transactional(readOnly = true)
    public Page<RouteDTO> findAll(Pageable pageable) {
        return routeRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public RouteDTO findById(Long id) {
        return toDTO(routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route", id)));
    }

    @Transactional
    public RouteDTO create(RouteDTO dto) {
        log.info("Creating route: {}", dto);
        return toDTO(routeRepository.save(toEntity(dto)));
    }

    @Transactional
    public RouteDTO update(Long id, RouteDTO dto) {
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route", id));
        return toDTO(routeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!routeRepository.existsById(id))
            throw new ResourceNotFoundException("Route", id);
        routeRepository.deleteById(id);
    }

    private RouteDTO toDTO(Route e) { return new RouteDTO(); }
    private Route toEntity(RouteDTO dto) { return new Route(); }
}
