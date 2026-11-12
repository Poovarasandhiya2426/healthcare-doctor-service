
package com.healthcare.doctor.controller;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
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

    // Register Doctor
    @PostMapping
    public ResponseEntity<DoctorResponse> registerDoctor(
            @Valid @RequestBody DoctorRegistrationRequest request) {

        DoctorResponse response =
                doctorService.registerDoctor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get Doctor By ID
    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(doctorId));
    }

    // Get Doctor By Email
    @GetMapping("/email/{email}")
    public ResponseEntity<DoctorResponse> getDoctorByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                doctorService.getDoctorByEmail(email));
    }

    // Get All Doctors
    @GetMapping
    public ResponseEntity<Page<DoctorSummaryResponse>> getAllDoctors(
            @PageableDefault(size = 10, sort = "id")
            Pageable pageable) {

        return ResponseEntity.ok(
                doctorService.getAllDoctors(pageable));
    }

    // Update Doctor
    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorUpdateRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(doctorId, request));
    }

    // Deactivate Doctor
    @PatchMapping("/{doctorId}/deactivate")
    public ResponseEntity<DoctorResponse> deactivateDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                doctorService.deactivateDoctor(doctorId));
    }

    // Search Doctors
    @GetMapping("/search")
    public ResponseEntity<Page<DoctorSummaryResponse>> searchDoctors(
            @RequestParam String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                doctorService.searchDoctors(keyword, pageable));
    }

    // Get Doctors By Specialization
    @GetMapping("/specialization/{specializationId}")
    public ResponseEntity<Page<DoctorSummaryResponse>>
    getDoctorsBySpecialization(
            @PathVariable Long specializationId,
            Pageable pageable) {

        return ResponseEntity.ok(
                doctorService.getDoctorsBySpecialization(
                        specializationId, pageable));
    }

    // Get Doctors By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<DoctorSummaryResponse>> getDoctorsByStatus(
            @PathVariable String status,
            Pageable pageable) {

        return ResponseEntity.ok(
                doctorService.getDoctorsByStatus(status, pageable));
    }
}
