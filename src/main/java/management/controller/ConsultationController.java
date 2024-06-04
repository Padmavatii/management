package management.controller;

import jakarta.validation.Valid;
import management.common.GenericResponse;
import management.dto.ConsultationDTO;
import management.entity.Consultation;
import management.interfaces.consultationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hms")
public class ConsultationController {

    private final consultationInterface iConsultationService;

    @Autowired
    public ConsultationController(management.interfaces.consultationInterface iConsultationService) {
        this.iConsultationService = iConsultationService;
    }

    @PostMapping("/doctor/consultation")
    public GenericResponse addConsultation(@RequestBody @Valid ConsultationDTO consultationDTO){
        return iConsultationService.submitConsultation(consultationDTO);
    }

    @PutMapping("/doctor/consultation/{id}")
    public GenericResponse updateConsultation(@PathVariable Long id, @RequestBody @Valid  ConsultationDTO consultationDTO){
        return iConsultationService.updateConsultation(id, consultationDTO);
    }

    @GetMapping("/doctor/consultations")
    public List<Consultation> getAllConsultations(){
        return iConsultationService.getAllConsultations();
    }

    @GetMapping("/doctor/consultation/{id}")
    public Optional<Consultation> getConsultationById(@PathVariable long id){
        return iConsultationService.getConsultation(id);
    }
}