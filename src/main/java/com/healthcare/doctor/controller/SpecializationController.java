
package com.healthcare.doctor.controller;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.SpecializationRequest;
import com.healthcare.doctor.dto.response.ApiResponse;
import com.healthcare.doctor.dto.response.SpecializationResponse;
import com.healthcare.doctor.service.SpecializationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    // Create specialization
    @PostMapping
    public ResponseEntity<ApiResponse<SpecializationResponse>>
    createSpecialization(
            @Valid @RequestBody SpecializationRequest request) {

        SpecializationResponse response =
                specializationService.createSpecialization(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Specialization created successfully", response));
    }

    // Get specialization by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecializationResponse>>
    getSpecializationById(@PathVariable Long id) {

        SpecializationResponse response =
                specializationService.getSpecializationById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Specialization fetched successfully", response));
    }

    // Get all active specializations
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SpecializationResponse>>>
    getAllSpecializations(Pageable pageable) {

        Page<SpecializationResponse> response =
                specializationService.getAllSpecializations(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Specializations fetched successfully", response));
    }

    // Update specialization
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecializationResponse>>
    updateSpecialization(
            @PathVariable Long id,
            @Valid @RequestBody SpecializationRequest request) {

        SpecializationResponse response =
                specializationService.updateSpecialization(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Specialization updated successfully", response));
    }

    // Deactivate specialization
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
    deactivateSpecialization(@PathVariable Long id) {

        specializationService.deactivateSpecialization(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Specialization deactivated successfully", null));
    }

    // Search specialization
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SpecializationResponse>>>
    searchSpecializations(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<SpecializationResponse> response =
                specializationService.searchSpecializations(
                        keyword, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Specializations search completed successfully",
                        response));
    }
}
