package org.orange.oie.internship2025.assetmanagementsystem.mapper;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssignmentStatus;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AssignmentMapper {
    private final AssetRepository assetRepository;
    public AssignmentMapper(AssetRepository assetRepository){
        this.assetRepository=assetRepository;
    }


    public AssetAssignment toAssignAsset(AssetAssignmentRequest request, Asset asset, User user)
    {
        if (request == null) {
            return null;
        }
        asset.setStatus(AssetStatus.ASSIGNED);
        assetRepository.save(asset);
        AssetAssignment assignment = new AssetAssignment();
        assignment.setAsset(asset);
        assignment.setAssignedTo(user);
        assignment.setStatus(AssignmentStatus.ACTIVE);
        assignment.setAssignmentDate(
                request.getAssignmentDate()
        );
        assignment.setReturnDate(request.getReturnDate());
        assignment.setNote(request.getNote());

        return assignment;
    }
    public AssetHistory toCreateAssetHistory(Asset asset, User user, AssetStatus status, String note) {
        AssetHistory history = new AssetHistory();
        history.setAsset(asset);
        history.setUser(user);
        history.setStatus(status);
        history.setNote(note);
        history.setTimestamp(LocalDateTime.now());
        return  history;
    }

}