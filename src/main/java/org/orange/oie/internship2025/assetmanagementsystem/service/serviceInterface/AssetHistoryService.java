package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ListAssetHistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetHistoryService {
    Page<ListAssetHistoryResponseDto> getHistoryByAssetId(Long assetId, Pageable pageable);
}