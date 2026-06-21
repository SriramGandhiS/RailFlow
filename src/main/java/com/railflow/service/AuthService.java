package com.railflow.service;

import com.railflow.dto.AuthDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Auth;
import com.railflow.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;

    @Transactional(readOnly = true)
    public Page<AuthDTO> findAll(Pageable pageable) {
        return authRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public AuthDTO findById(Long id) {
        return toDTO(authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth", id)));
    }

    @Transactional
    public AuthDTO create(AuthDTO dto) {
        log.info("Creating auth: {}", dto);
        return toDTO(authRepository.save(toEntity(dto)));
    }

    @Transactional
    public AuthDTO update(Long id, AuthDTO dto) {
        Auth existing = authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth", id));
        return toDTO(authRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!authRepository.existsById(id))
            throw new ResourceNotFoundException("Auth", id);
        authRepository.deleteById(id);
    }

    private AuthDTO toDTO(Auth e) { return new AuthDTO(); }
    private Auth toEntity(AuthDTO dto) { return new Auth(); }
}
