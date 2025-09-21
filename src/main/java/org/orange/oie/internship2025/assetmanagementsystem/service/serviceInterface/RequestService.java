package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;
import org.orange.oie.internship2025.assetmanagementsystem.dto.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

     ResponseDTO addRequest(RequestDTO requestDTO);
     Page<ResponseDTO> getRequests(Pageable pageable);
}
