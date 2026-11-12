
package com.healthcare.doctor.controller;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorAvailabilityRequest;
import com.healthcare.doctor.dto.response.ApiResponse;
import com.healthcare.doctor.dto.response.DoctorAvailabilityResponse;
import com.healthcare.doctor.service.DoctorAvailabilityService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-availability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService availabilityService;

    // ==========================================
    // CREATE DOCTOR AVAILABILITY
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorAvailabilityResponse>>
    createAvailability(
            @Valid @RequestBody DoctorAvailabilityRequest request) {

        DoctorAvailabilityResponse response =
                availabilityService.createAvailability(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        DoctorConstants.AVAILABILITY_CREATED,
                        response
                ));
    }

    // ==========================================
    // GET AVAILABILITY BY ID
    // ==========================================

    @GetMapping("/{availabilityId}")
    public ResponseEntity<ApiResponse<DoctorAvailabilityResponse>>
    getAvailabilityById(
            @PathVariable Long availabilityId) {

        DoctorAvailabilityResponse response =
                availabilityService.getAvailabilityById(availabilityId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.AVAILABILITY_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // GET AVAILABILITY BY DOCTOR ID
    // ==========================================

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<DoctorAvailabilityResponse>>>
    getAvailabilityByDoctorId(
            @PathVariable Long doctorId) {

        List<DoctorAvailabilityResponse> response =
                availabilityService.getAvailabilityByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.AVAILABILITIES_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // GET AVAILABILITY BY DOCTOR AND WEEKDAY
    // ==========================================

    @GetMapping("/doctor/{doctorId}/day/{dayOfWeek}")
    public ResponseEntity<ApiResponse<List<DoctorAvailabilityResponse>>>
    getAvailabilityByDoctorAndDay(
            @PathVariable Long doctorId,
            @PathVariable String dayOfWeek) {

        List<DoctorAvailabilityResponse> response =
                availabilityService.getAvailabilityByDoctorAndDay(
                        doctorId,
                        dayOfWeek
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.AVAILABILITY_FETCHED,
                        response
                )
        );
    }

    // ==========================================
    // UPDATE AVAILABILITY
    // ==========================================

    @PutMapping("/{availabilityId}")
    public ResponseEntity<ApiResponse<DoctorAvailabilityResponse>>
    updateAvailability(
            @PathVariable Long availabilityId,
            @Valid @RequestBody DoctorAvailabilityRequest request) {

        DoctorAvailabilityResponse response =
                availabilityService.updateAvailability(
                        availabilityId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.AVAILABILITY_UPDATED,
                        response
                )
        );
    }

    // ==========================================
    // DEACTIVATE AVAILABILITY
    // ==========================================

    @PatchMapping("/{availabilityId}/deactivate")
    public ResponseEntity<ApiResponse<DoctorAvailabilityResponse>>
    deactivateAvailability(
            @PathVariable Long availabilityId) {

        DoctorAvailabilityResponse response =
                availabilityService.deactivateAvailability(
                        availabilityId
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.AVAILABILITY_DEACTIVATED,
                        response
                )
        );
    }
}
