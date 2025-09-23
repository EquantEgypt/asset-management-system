package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;

public interface RequestService {

     ResponseDTO addRequest(RequestDTO requestDTO);
}
