package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RequestService {

     ResponseDTO addRequest(RequestDTO requestDTO);
     Page<ResponseDTO> getRequests(RequestStatus status, RequestType type, Pageable pageable);
     ResponseDTO  respondToRequest(ResponseDTO response);
}
