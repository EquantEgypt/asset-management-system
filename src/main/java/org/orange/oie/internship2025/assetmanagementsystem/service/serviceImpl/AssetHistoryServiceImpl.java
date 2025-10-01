package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetHistoryDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetHistoryMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetHistoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AssetHistoryServiceImpl implements AssetHistoryService {

    @Autowired
    private AssetHistoryRepository assetHistoryRepository;

    @Autowired
    private AssetHistoryMapper assetHistoryMapper;


    public Page<AssetHistoryDto> getHistoryByAssetId(Long assetId, Pageable pageable) {
        Page<AssetHistory> histories = assetHistoryRepository.findByAssetId(assetId, pageable);
        return histories.map(assetHistoryMapper::toDto);
    }
}
