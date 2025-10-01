package org.orange.oie.internship2025.assetmanagementsystem.repository;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
    Page<AssetHistory> findByAssetId(Long assetId, Pageable pageable);
}
