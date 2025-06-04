package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityUserDTO;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.security.SecurityUserService;
import com.businessprosuite.api.mapper.SecurityUserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityUserServiceImpl implements SecurityUserService {
    private final SecurityUserRepository userRepo;
    private final SecurityRoleRepository roleRepo;
    private final CompanyRepository companyRepo;
    private final SecurityUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<SecurityUserDTO> findAll() {
        return userRepo.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityUserDTO findById(Integer id) {
        SecurityUser u = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        return userMapper.toDto(u);
    }

    @Override
    public SecurityUserDTO create(SecurityUserDTO dto) {
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        SecurityUser u = userMapper.toEntity(dto);
        u.setSecusPassword(passwordEncoder.encode(dto.getPassword()));
        u.setSecusRole(role);
        u.setSecusCmp(cmp);
        u.setSecusCreatedAt(LocalDateTime.now());
        u.setSecusUpdatedAt(LocalDateTime.now());
        
        SecurityUser saved = userRepo.save(u);
        return userMapper.toDto(saved);
    }

    @Override
    public SecurityUserDTO update(Integer id, SecurityUserDTO dto) {
        SecurityUser u = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        userMapper.updateEntity(dto, u);
        u.setSecusPassword(passwordEncoder.encode(dto.getPassword()));
        u.setSecusRole(role);
        u.setSecusCmp(cmp);
        u.setSecusUpdatedAt(LocalDateTime.now());
        
        SecurityUser updated = userRepo.save(u);
        return userMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("SecurityUser not found with id " + id);
        }
        userRepo.deleteById(id);
    }

}
