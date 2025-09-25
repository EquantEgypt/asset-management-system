package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<AssetRequest, Long> {
    Page<AssetRequest> findAllByRequesterIdIn(List<Long> requesterIds, Pageable pageable);
    Page<AssetRequest> findAllByRequesterId(Long requesterId, Pageable pageable);
    Page<AssetRequest> findAllByRequestType(RequestType requestType, Pageable pageable);
}