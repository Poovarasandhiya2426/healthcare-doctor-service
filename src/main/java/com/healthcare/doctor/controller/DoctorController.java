
package com.healthcare.doctor.controller;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.ApiResponse;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;
import com.healthcare.doctor.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // Register doctor
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> registerDoctor(
            @Valid @RequestBody DoctorRegistrationRequest request) {

        DoctorResponse response = doctorService.registerDoctor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        DoctorConstants.DOCTOR_REGISTERED,
                        response));
    }

    // Get doctor by ID
    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(
            @PathVariable Long doctorId) {

        DoctorResponse response = doctorService.getDoctorById(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTOR_FETCHED,
                        response));
    }

    // Get doctor by email
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorByEmail(
            @PathVariable String email) {

        DoctorResponse response = doctorService.getDoctorByEmail(email);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTOR_FETCHED,
                        response));
    }

    // Get all doctors with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorSummaryResponse>>> getAllDoctors(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id"
            ) Pageable pageable) {

        Page<DoctorSummaryResponse> response =
                doctorService.getAllDoctors(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTORS_FETCHED,
                        response));
    }

    // Update doctor
    @PutMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorUpdateRequest request) {

        DoctorResponse response =
                doctorService.updateDoctor(doctorId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTOR_UPDATED,
                        response));
    }

    // Deactivate doctor
    @PatchMapping("/{doctorId}/deactivate")
    public ResponseEntity<ApiResponse<DoctorResponse>> deactivateDoctor(
            @PathVariable Long doctorId) {

        DoctorResponse response =
                doctorService.deactivateDoctor(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTOR_DEACTIVATED,
                        response));
    }

    // Search doctors
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<DoctorSummaryResponse>>> searchDoctors(
            @RequestParam String keyword,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<DoctorSummaryResponse> response =
                doctorService.searchDoctors(keyword, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTORS_FETCHED,
                        response));
    }

    // Get doctors by specialization
    @GetMapping("/specialization/{specializationId}")
    public ResponseEntity<ApiResponse<Page<DoctorSummaryResponse>>> getDoctorsBySpecialization(
            @PathVariable Long specializationId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<DoctorSummaryResponse> response =
                doctorService.getDoctorsBySpecialization(
                        specializationId, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTORS_FETCHED,
                        response));
    }

    // Get doctors by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<DoctorSummaryResponse>>> getDoctorsByStatus(
            @PathVariable String status,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<DoctorSummaryResponse> response =
                doctorService.getDoctorsByStatus(status, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.DOCTORS_FETCHED,
                        response));
    }
}
