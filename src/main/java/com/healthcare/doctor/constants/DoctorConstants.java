
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

    public static final String SPECIALIZATION_NAME_REQUIRED =
            "Specialization name is required";

    public static final String SPECIALIZATION_NAME_MAX_LENGTH =
            "Specialization name cannot exceed 100 characters";

    public static final String SPECIALIZATION_DESCRIPTION_MAX_LENGTH =
            "Description cannot exceed 500 characters";

    public static final String DEGREE_REQUIRED =
            "Qualification degree is required";

    public static final String UNIVERSITY_REQUIRED =
            "University name is required";

    // Doctor Qualification Validation Messages

    public static final String DOCTOR_ID_REQUIRED =
            "Doctor ID is required";


    public static final String DEGREE_MAX_LENGTH =
            "Degree must not exceed 150 characters";

    public static final String UNIVERSITY_MAX_LENGTH =
            "University must not exceed 200 characters";

    public static final String COMPLETION_YEAR_REQUIRED =
            "Year of completion is required";

    public static final String CERTIFICATE_URL_MAX_LENGTH =
            "Certificate URL must not exceed 500 characters";

    // Doctor Availability Success Messages

    public static final String AVAILABILITY_CREATED =
            "Doctor availability created successfully";

    public static final String AVAILABILITY_FETCHED =
            "Doctor availability fetched successfully";

    public static final String AVAILABILITIES_FETCHED =
            "Doctor availability list fetched successfully";

    public static final String AVAILABILITY_UPDATED =
            "Doctor availability updated successfully";

    public static final String AVAILABILITY_DEACTIVATED =
            "Doctor availability deactivated successfully";

    public static final String AVAILABILITY_ALREADY_EXISTS =
            "Active availability already exists for this day";

    public static final String AVAILABILITY_ALREADY_INACTIVE =
            "Doctor availability is already inactive";

    public static final String INVALID_AVAILABILITY_ID =
            "Valid availability ID is required";

    public static final String AVAILABILITY_NOT_FOUND =
            "Doctor availability not found";

    public static final String AVAILABILITY_REQUEST_EMPTY =
            "Doctor availability request cannot be empty";

    public static final String INVALID_AVAILABILITY_TIME_RANGE =
            "End time must be after start time";

    public static final String DAY_OF_WEEK_REQUIRED =
            "Day of week is required";

    public static final String INVALID_DAY_OF_WEEK =
            "Invalid day of week";

    // Doctor Availability Validation Messages

    public static final String START_TIME_REQUIRED =
            "Start time is required";

    public static final String END_TIME_REQUIRED =
            "End time is required";

    public static final String CONSULTATION_MODE_REQUIRED =
            "Consultation mode is required";

    public static final String SLOT_DURATION_REQUIRED =
            "Slot duration is required";

    public static final String MIN_SLOT_DURATION =
            "Minimum slot duration is 5 minutes";

    public static final String MAX_SLOT_DURATION =
            "Maximum slot duration is 240 minutes";


}

