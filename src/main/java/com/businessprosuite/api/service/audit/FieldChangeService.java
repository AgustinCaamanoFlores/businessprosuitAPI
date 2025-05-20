package com.businessprosuite.api.service.audit;

import com.businessprosuite.api.dto.audit.FieldChangeDTO;
import java.util.List;

public interface FieldChangeService {
    List<FieldChangeDTO> findAll();
    FieldChangeDTO findById(Integer id);
    FieldChangeDTO create(FieldChangeDTO dto);
    FieldChangeDTO update(Integer id, FieldChangeDTO dto);
    void delete(Integer id);
}
