
package com.healthcare.doctor.controller;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorHospitalRequest;
import com.healthcare.doctor.dto.response.ApiResponse;
import com.healthcare.doctor.dto.response.DoctorHospitalResponse;
import com.healthcare.doctor.service.DoctorHospitalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-hospitals")
@RequiredArgsConstructor
public class DoctorHospitalController {

    private final DoctorHospitalService hospitalService;

    // ==========================================
    // CREATE HOSPITAL MAPPING
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorHospitalResponse>>
    createHospitalMapping(
            @Valid @RequestBody DoctorHospitalRequest request) {

        DoctorHospitalResponse response =
                hospitalService.createHospitalMapping(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPING_CREATED,
                        response
                ));
    }

    // ==========================================
    // GET MAPPING BY ID
    // ==========================================

    @GetMapping("/{mappingId}")
    public ResponseEntity<ApiResponse<DoctorHospitalResponse>>
    getHospitalMappingById(
            @PathVariable Long mappingId) {

        DoctorHospitalResponse response =
                hospitalService.getHospitalMappingById(mappingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPING_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // GET HOSPITALS BY DOCTOR ID
    // ==========================================

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<DoctorHospitalResponse>>>
    getHospitalsByDoctorId(
            @PathVariable Long doctorId) {

        List<DoctorHospitalResponse> response =
                hospitalService.getHospitalsByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPINGS_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // GET DOCTORS BY HOSPITAL ID
    // ==========================================

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<ApiResponse<List<DoctorHospitalResponse>>>
    getDoctorsByHospitalId(
            @PathVariable Long hospitalId) {

        List<DoctorHospitalResponse> response =
                hospitalService.getDoctorsByHospitalId(hospitalId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPINGS_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // GET ACTIVE HOSPITALS BY DOCTOR ID
    // ==========================================

    @GetMapping("/doctor/{doctorId}/active")
    public ResponseEntity<ApiResponse<List<DoctorHospitalResponse>>>
    getActiveHospitalsByDoctorId(
            @PathVariable Long doctorId) {

        List<DoctorHospitalResponse> response =
                hospitalService.getActiveHospitalsByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPINGS_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // UPDATE HOSPITAL MAPPING
    // ==========================================

    @PutMapping("/{mappingId}")
    public ResponseEntity<ApiResponse<DoctorHospitalResponse>>
    updateHospitalMapping(
            @PathVariable Long mappingId,
            @Valid @RequestBody DoctorHospitalRequest request) {

        DoctorHospitalResponse response =
                hospitalService.updateHospitalMapping(
                        mappingId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPING_UPDATED,
                        response
                )
        );
    }

    // ==========================================
    // DEACTIVATE HOSPITAL MAPPING
    // ==========================================

    @PatchMapping("/{mappingId}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
    deactivateHospitalMapping(
            @PathVariable Long mappingId) {

        hospitalService.deactivateHospitalMapping(mappingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPING_DELETED,
                        null
                )
        );
    }

    // ==========================================
    // DELETE HOSPITAL MAPPING
    // ==========================================

    @DeleteMapping("/{mappingId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteHospitalMapping(
            @PathVariable Long mappingId) {

        hospitalService.deleteHospitalMapping(mappingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.HOSPITAL_MAPPING_DELETED,
                        null
                )
        );
    }
}
