package com.railflow.service;

import com.railflow.dto.UserDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.User;
import com.railflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return toDTO(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id)));
    }

    @Transactional
    public UserDTO create(UserDTO dto) {
        log.info("Creating user: {}", dto);
        return toDTO(userRepository.save(toEntity(dto)));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toDTO(userRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User", id);
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User e) { return new UserDTO(); }
    private User toEntity(UserDTO dto) { return new User(); }
}
