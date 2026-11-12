
package com.healthcare.doctor.controller;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorQualificationRequest;
import com.healthcare.doctor.dto.response.ApiResponse;
import com.healthcare.doctor.dto.response.DoctorQualificationResponse;
import com.healthcare.doctor.service.DoctorQualificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-qualifications")
@RequiredArgsConstructor
public class DoctorQualificationController {

    private final DoctorQualificationService qualificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorQualificationResponse>>
    createQualification(
            @Valid @RequestBody DoctorQualificationRequest request) {

        DoctorQualificationResponse response =
                qualificationService.createQualification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        DoctorConstants.QUALIFICATION_CREATED,
                        response
                ));
    }

    @GetMapping("/{qualificationId}")
    public ResponseEntity<ApiResponse<DoctorQualificationResponse>>
    getQualificationById(@PathVariable Long qualificationId) {

        DoctorQualificationResponse response =
                qualificationService.getQualificationById(qualificationId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.QUALIFICATION_FETCHED,
                        response
                )
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<DoctorQualificationResponse>>>
    getQualificationsByDoctorId(@PathVariable Long doctorId) {

        List<DoctorQualificationResponse> response =
                qualificationService.getQualificationsByDoctorId(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.QUALIFICATIONS_FETCHED,
                        response
                )
        );
    }

    @PutMapping("/{qualificationId}")
    public ResponseEntity<ApiResponse<DoctorQualificationResponse>>
    updateQualification(
            @PathVariable Long qualificationId,
            @Valid @RequestBody DoctorQualificationRequest request) {

        DoctorQualificationResponse response =
                qualificationService.updateQualification(
                        qualificationId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.QUALIFICATION_UPDATED,
                        response
                )
        );
    }

    @DeleteMapping("/{qualificationId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteQualification(@PathVariable Long qualificationId) {

        qualificationService.deleteQualification(qualificationId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        DoctorConstants.QUALIFICATION_DELETED,
                        null
                )
        );
    }
}
