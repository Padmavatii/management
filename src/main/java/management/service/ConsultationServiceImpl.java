package management.service;

import management.common.GenericResponse;
import management.common.NotFoundException;
import management.dto.ConsultationDTO;
import management.entity.Consultation;
import management.interfaces.consultationInterface;
import management.repository.ConsultationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements consultationInterface {

    Logger logger = LoggerFactory.getLogger(ConsultationServiceImpl.class);

    private final ConsultationRepository consultationRepository;

    private final MessageSource messageSource;

    private final WebClient webClient;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, MessageSource messageSource, WebClient webClient) {
        this.consultationRepository = consultationRepository;
        this.messageSource = messageSource;
        this.webClient = webClient;
    }

    private static final String DOCTOR_CONSULTATION_SUBMIT_SUCCESS = "Consultation.submit.success";
    private static final String DOCTOR_NOT_FOUND = "Doctor.Not.Found.Error";
    private static final String DOCTOR_CONSULTATION_UPDATE_SUCCESS="Consultation.update.success";
    private static final String DOCTOR_CONSULTATION_NOT_FOUND="Doctor.consultation.Not.Found";

    @Override
    public GenericResponse submitConsultation(ConsultationDTO consultationDTO) {
        try {
            logger.info("API call to add consultation of doctor{}", consultationDTO);

            webClient.get()
                    .uri("http://localhost:9091/hms/doctor/{doctorId}", consultationDTO.getDoctorID())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Consultation consultation = new Consultation();
            consultation.setDoctorID(consultationDTO.getDoctorID());
            consultation.setPatientID(consultationDTO.getPatientID());
            consultation.setConsultationDate(consultationDTO.getConsultationDate());
            consultation.setDiagnosis(consultationDTO.getDiagnosis());

            consultationRepository.save(consultation);

            GenericResponse response = new GenericResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setResponseCode(DOCTOR_CONSULTATION_SUBMIT_SUCCESS);
            response.setResponseMessage(messageSource.getMessage(DOCTOR_CONSULTATION_SUBMIT_SUCCESS, null, Locale.ENGLISH));

            return response;
        } catch (Exception e) {
            logger.error("Id Not Found{}", consultationDTO.getDoctorID());
            throw new NotFoundException(messageSource.getMessage(DOCTOR_NOT_FOUND, null, Locale.ENGLISH));
        }
    }

    @Override
    public GenericResponse updateConsultation(Long id, ConsultationDTO consultationDTO){
        try{
        logger.info("API call to update consultation of doctor{}", consultationDTO);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(DOCTOR_CONSULTATION_NOT_FOUND, null, Locale.ENGLISH)));

        webClient.get()
                .uri("http://localhost:9091/hms/doctor/{doctorId}", consultationDTO.getDoctorID())
                .retrieve()
                .bodyToMono(String.class)
                .block();


        consultation.setDoctorID(consultationDTO.getDoctorID());
        consultation.setPatientID(consultationDTO.getPatientID());
        consultation.setConsultationDate(consultationDTO.getConsultationDate());
        consultation.setDiagnosis(consultationDTO.getDiagnosis());

        consultationRepository.save(consultation);

        GenericResponse response = new GenericResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setResponseCode(DOCTOR_CONSULTATION_UPDATE_SUCCESS);
        response.setResponseMessage(messageSource.getMessage(DOCTOR_CONSULTATION_UPDATE_SUCCESS, null, Locale.ENGLISH));

        return response;
        } catch (Exception e) {
            logger.error("Doctor Id Not Found{}", consultationDTO.getDoctorID());
            throw new NotFoundException(messageSource.getMessage(DOCTOR_NOT_FOUND, null, Locale.ENGLISH));
        }
    }

    @Override
    public List<Consultation> getAllConsultations() {
        logger.info("API call to get all the Consultation of doctors");
        return consultationRepository.findAll();
    }

    @Override
    public Optional<Consultation> getConsultation(long id) {
        logger.info("API call to get a doctor consultation by their ID{}",id );
        Optional<Consultation> consultation = consultationRepository.findById(id);
        if(consultation.isEmpty()){
            logger.error("Invalid Id passed{}",id);
            throw new NotFoundException(messageSource.getMessage(DOCTOR_CONSULTATION_NOT_FOUND, null, Locale.ENGLISH));
        }
        return consultation;
    }

}
