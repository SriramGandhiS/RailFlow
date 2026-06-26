package com.railflow.service;

import com.railflow.dto.SearchDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Search;
import com.railflow.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final SearchRepository searchRepository;

    @Transactional(readOnly = true)
    public Page<SearchDTO> findAll(Pageable pageable) {
        return searchRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public SearchDTO findById(Long id) {
        return toDTO(searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search", id)));
    }

    @Transactional
    public SearchDTO create(SearchDTO dto) {
        log.info("Creating search: {}", dto);
        return toDTO(searchRepository.save(toEntity(dto)));
    }

    @Transactional
    public SearchDTO update(Long id, SearchDTO dto) {
        Search existing = searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search", id));
        return toDTO(searchRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!searchRepository.existsById(id))
            throw new ResourceNotFoundException("Search", id);
        searchRepository.deleteById(id);
    }

    private SearchDTO toDTO(Search e) { return new SearchDTO(); }
    private Search toEntity(SearchDTO dto) { return new Search(); }
}
