package com.dentflow.patient.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ClinicService clinicService;
    private final UserService userService;


    public PatientService(PatientRepository patientRepository, ClinicRepository clinicRepository, ClinicService clinicService, UserService userService) {
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
        this.clinicService = clinicService;
        this.userService = userService;
    }

    public void registerPatient(PatientRequest request, String email) {
        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> c.getId() == request.getClinicId())
                        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        patientRepository.save(patient);
        clinic.addPatient(patient);
        clinicRepository.save(clinic);
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public List<Patient> getAllPatientsFromClinic(long clinicId) {
       return null;
    }
}
