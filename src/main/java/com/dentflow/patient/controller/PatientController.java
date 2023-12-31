package com.dentflow.patient.controller;

import com.dentflow.exception.ApiRequestException;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientDescriptionRequest;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/getPatient")
    public Patient getPatientFromClinic(
            @PathVariable long patientId){
        return patientService.getPatientById(patientId);
    }
    @PostMapping
    public void registerPatient(@RequestBody PatientRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        patientService.registerPatient(request, user.getEmail());
    }
    @PatchMapping
    public void updatePatient(@RequestBody PatientRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        patientService.updatePatient(request, user.getEmail());
    }

    @GetMapping
    public Set<Visit> getVisitHistory(@RequestParam Long patientId, @RequestParam Long clinicId ,Authentication authentication){
        User user = (User) authentication.getPrincipal();


        if(!patientService.checkIfPatientExist(patientId)){
            throw new ApiRequestException("Cannot find patient with that id: " + patientId);
        }

        return patientService.getPatientVisitHistory(clinicId, patientId, user.getEmail());
    }
    @PutMapping("/change-description")
    public void setPatientDescription(@RequestBody PatientDescriptionRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        if(!patientService.checkIfPatientExist(request.getPatientId())){
            throw new ApiRequestException("Cannot find patient with that id: " + request.getPatientId());
        }

        patientService.updatePatientDescription(request.getClinicId(), request.getPatientId(), request.getPatientDescription(), user.getEmail());
    }
}
