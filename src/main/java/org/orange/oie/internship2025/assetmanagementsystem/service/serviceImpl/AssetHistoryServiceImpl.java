package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetHistoryDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetHistoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetHistoryServiceImpl implements AssetHistoryService {
    @Autowired
    private  AssetHistoryRepository assetHistoryRepository;

    public List<AssetHistoryDto> getHistoryByAssetId(Long assetId) {
        List<AssetHistory> histories = assetHistoryRepository.findByAssetId(assetId);
        List<AssetHistoryDto> Assethistorydtos = new ArrayList<>();

        for (AssetHistory history : histories) {
            AssetHistoryDto dto = new AssetHistoryDto();
            dto.setId(history.getId());
            dto.setAssetName(history.getAsset().getName());
            dto.setAssignedTo(history.getUser() != null ? history.getUser().getUsername() : null);
            dto.setNote(history.getNote());
            dto.setTimestamp(history.getTimestamp());
            dto.setStatus(history.getStatus());
            Assethistorydtos.add(dto);
        }

        return Assethistorydtos;
    }
}
