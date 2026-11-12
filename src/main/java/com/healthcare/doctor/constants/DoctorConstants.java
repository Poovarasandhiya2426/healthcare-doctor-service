
package com.healthcare.doctor.constants;

public final class DoctorConstants {

    private DoctorConstants() {
        // Prevent object creation
    }

    // Doctor status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    // Success messages
    public static final String DOCTOR_REGISTERED =
            "Doctor registered successfully";

    public static final String DOCTOR_FETCHED =
            "Doctor details fetched successfully";

    public static final String DOCTORS_FETCHED =
            "Doctor list fetched successfully";

    public static final String DOCTOR_UPDATED =
            "Doctor details updated successfully";

    public static final String DOCTOR_DEACTIVATED =
            "Doctor deactivated successfully";

    // Error messages
    public static final String DOCTOR_NOT_FOUND =
            "Doctor not found";

    public static final String DUPLICATE_EMAIL =
            "Doctor email already exists";

    public static final String DUPLICATE_PHONE =
            "Doctor phone number already exists";

    public static final String DUPLICATE_LICENSE =
            "Doctor license number already exists";

    public static final String INVALID_DOCTOR_ID =
            "Valid doctor ID is required";

    public static final String INVALID_SEARCH_KEYWORD =
            "Search keyword cannot be empty";

    public static final String INVALID_DOCTOR_STATUS =
            "Invalid doctor status. Use ACTIVE or INACTIVE";

    public static final String DOCTOR_ALREADY_INACTIVE =
            "Doctor is already inactive";

    public static final String IDENTITY_USER_ALREADY_LINKED =
            "Identity user is already linked to a doctor";

    public static final String INVALID_SPECIALIZATION_ID =
            "Valid specialization ID is required";

    public static final String DOCTOR_STATUS_REQUIRED =
            "Doctor status is required";


    // Specialization success messages
    public static final String SPECIALIZATION_CREATED =
            "Specialization created successfully";

    public static final String SPECIALIZATION_FETCHED =
            "Specialization fetched successfully";

    public static final String SPECIALIZATIONS_FETCHED =
            "Specializations fetched successfully";

    public static final String SPECIALIZATION_UPDATED =
            "Specialization updated successfully";

    public static final String SPECIALIZATION_DEACTIVATED =
            "Specialization deactivated successfully";

    public static final String SPECIALIZATION_SEARCH_COMPLETED =
            "Specializations search completed successfully";

    // Specialization error messages
    public static final String SPECIALIZATION_NOT_FOUND =
            "Specialization not found";

    public static final String DUPLICATE_SPECIALIZATION_NAME =
            "Specialization name already exists";

    public static final String SPECIALIZATION_ALREADY_INACTIVE =
            "Specialization is already inactive";



    public static final String SPECIALIZATION_SEARCH_KEYWORD_REQUIRED =
            "Search keyword is required";

    // Security roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    public static final String ROLE_RECEPTIONIST = "ROLE_RECEPTIONIST";


        // Doctor Qualification
        public static final String QUALIFICATION_CREATED =
                "Doctor qualification created successfully";

        public static final String QUALIFICATION_FETCHED =
                "Doctor qualification fetched successfully";

        public static final String QUALIFICATION_UPDATED =
                "Doctor qualification updated successfully";

        public static final String QUALIFICATION_DELETED =
                "Doctor qualification deleted successfully";

        public static final String QUALIFICATION_NOT_FOUND =
                "Doctor qualification not found";

        public static final String DUPLICATE_QUALIFICATION =
                "This qualification already exists for the doctor";

        public static final String INVALID_QUALIFICATION_ID =
                "Invalid qualification ID";

        public static final String INVALID_COMPLETION_YEAR =
                "Invalid year of completion";

        public static final String QUALIFICATION_MOVE_NOT_ALLOWED =
                "Qualification cannot be moved to another doctor";

        public static final String QUALIFICATIONS_FETCHED =
            "Doctor qualifications fetched successfully";

    public static final String QUALIFICATION_REQUEST_EMPTY =
            "Qualification request cannot be empty";



// ==========================================
// DOCTOR HOSPITAL MAPPING
// ==========================================

    public static final String HOSPITAL_MAPPING_CREATED =
            "Doctor hospital mapping created successfully";

    public static final String HOSPITAL_MAPPING_FETCHED =
            "Doctor hospital mapping fetched successfully";

    public static final String HOSPITAL_MAPPINGS_FETCHED =
            "Doctor hospital mappings fetched successfully";

    public static final String HOSPITAL_MAPPING_UPDATED =
            "Doctor hospital mapping updated successfully";

    public static final String HOSPITAL_MAPPING_DELETED =
            "Doctor hospital mapping deleted successfully";

    public static final String HOSPITAL_MAPPING_NOT_FOUND =
            "Doctor hospital mapping not found";

    public static final String DUPLICATE_HOSPITAL_MAPPING =
            "Doctor is already mapped to this hospital";

    public static final String INVALID_HOSPITAL_ID =
            "Invalid hospital ID";

    public static final String INVALID_JOINING_DATE =
            "Invalid joining date";

    public static final String INVALID_CONSULTATION_FEE =
            "Consultation fee cannot be negative";

    public static final String HOSPITAL_MAPPING_ALREADY_INACTIVE =
            "Doctor hospital mapping is already inactive";

    public static final String PRIMARY_HOSPITAL_EXISTS =
            "Doctor already has a primary hospital";


// Additional Hospital Mapping Constants

    public static final String INVALID_HOSPITAL_MAPPING_ID =
            "Invalid hospital mapping ID";

    public static final String HOSPITAL_MAPPING_REQUEST_EMPTY =
            "Hospital mapping request cannot be empty";

    public static final String HOSPITAL_MAPPING_DOCTOR_CHANGE_NOT_ALLOWED =
            "Doctor cannot be changed in an existing hospital mapping";

    public static final String HOSPITAL_MAPPING_HOSPITAL_CHANGE_NOT_ALLOWED =
            "Hospital cannot be changed in an existing doctor mapping";

    public static final String HOSPITAL_MAPPING_DEACTIVATED =
            "Doctor hospital mapping deactivated successfully";

    // Doctor validation messages
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String PHONE_NUMBER_REQUIRED = "Phone number is required";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String INVALID_EXPERIENCE = "Years of experience cannot be negative";

    public static final String DOCTOR_INVALID_CONSULTATION_FEE =
            "Doctor consultation fee cannot be negative";


    // Doctor validation messages
    public static final String IDENTITY_USER_ID_REQUIRED =
            "Identity user ID is required";


    public static final String LICENSE_NUMBER_REQUIRED =
            "License number is required";


    public static final String ACTIVE_SPECIALIZATION_NOT_FOUND =
            "Active specialization not found";


    public static final String DATABASE_CONSTRAINT_VIOLATION =
            "Database constraint violation occurred";

    public static final String UNEXPECTED_ERROR =
            "An unexpected error occurred";

    public static final String VALIDATION_FAILED =
            "Request validation failed";


}

