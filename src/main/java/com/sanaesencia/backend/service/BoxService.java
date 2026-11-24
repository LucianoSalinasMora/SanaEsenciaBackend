package com.sanaesencia.backend.service;

import com.sanaesencia.backend.dto.BoxDto;
import com.sanaesencia.backend.entity.Box;
import com.sanaesencia.backend.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;

    public List<BoxDto> getAllBoxes() {
        return boxRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<BoxDto> getAvailableBoxes() {
        return boxRepository.findByStatusIgnoreCase("disponible").stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public BoxDto updateBoxStatus(Integer boxNumber, String status) {
        Box box = boxRepository.findById(boxNumber)
                .orElseThrow(() -> new IllegalArgumentException("Box no encontrado: " + boxNumber));
        box.setStatus(status.toLowerCase());
        if ("disponible".equalsIgnoreCase(status)) {
            box.setCurrentAppointmentId(null);
        }
        return toDto(boxRepository.save(box));
    }

    public BoxDto toDto(Box box) {
        return BoxDto.builder()
                .boxNumber(box.getBoxNumber())
                .name(box.getName())
                .status(box.getStatus())
                .description(box.getDescription())
                .currentAppointmentId(box.getCurrentAppointmentId())
                .build();
    }
}
