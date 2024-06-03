package management.service;

import management.common.GenericResponse;
import management.common.NotFoundException;
import management.dto.AvailabilityDTO;
import management.dto.DoctorDTO;
import management.entity.Availability;
import management.interfaces.availabilityInterface;
import management.repository.AvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements availabilityInterface {

    Logger logger = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final AvailabilityRepository availabilityRepository;

    private final MessageSource messageSource;

    private final WebClient webClient;

    private final RestTemplate restTemplate;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, MessageSource messageSource, WebClient webClient, RestTemplate restTemplate){
        this.availabilityRepository = availabilityRepository;
        this.messageSource = messageSource;
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    private static final String DOCTOR_AVAILABILITY_SUBMIT_SUCCESS="Availability.submit.success";
    private static final String DOCTOR_AVAILABILITY_UPDATE_SUCCESS="Availability.update.success";
    private static final String DOCTOR_AVAILABILITY_NOT_FOUND="Availability.Not.Found";
    private static final String DOCTOR_NOT_FOUND="Doctor.Not.Found.Error";

    @Transactional
    @Override
    public GenericResponse submitAvailability(AvailabilityDTO availabilityDTO) {
        try {
            logger.info("API call to add availability of doctor{}", availabilityDTO);

                     webClient.get()
                    .uri("http://localhost:8080/hms/doctor/{doctorId}", availabilityDTO.getDoctorId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

                Availability availability = new Availability();
                availability.setDoctorId(availabilityDTO.getDoctorId());
                availability.setDoctorName(availabilityDTO.getDoctorName());
                availability.setShiftTimingStartTime(availabilityDTO.getShiftTimingStartTime());
                availability.setShiftTimingEndTime(availabilityDTO.getShiftTimingEndTime());
                availability.setStatus(availabilityDTO.getStatus());
                availabilityRepository.save(availability);

            GenericResponse response = new GenericResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setResponseCode(DOCTOR_AVAILABILITY_SUBMIT_SUCCESS);
            response.setResponseMessage(messageSource.getMessage(DOCTOR_AVAILABILITY_SUBMIT_SUCCESS, null, Locale.ENGLISH));

            return response;
        }catch (Exception e) {
            logger.error("Id Not Found{}", availabilityDTO.getDoctorId());
            throw new NotFoundException(messageSource.getMessage(DOCTOR_NOT_FOUND, null, Locale.ENGLISH));
        }
    }

    @Transactional
    @Override
    public GenericResponse updateAvailability(Long id, AvailabilityDTO availabilityDTO){
        try {
            logger.info("API call to update availability of doctor{}", availabilityDTO);
            Availability availability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(messageSource.getMessage(DOCTOR_AVAILABILITY_NOT_FOUND, null, Locale.ENGLISH)));

            restTemplate.getForObject("http://localhost:8080/hms/doctor/{doctorId}", DoctorDTO.class, availabilityDTO.getDoctorId());
            availability.setDoctorId(availabilityDTO.getDoctorId());
            availability.setDoctorName(availabilityDTO.getDoctorName());
            availability.setShiftTimingStartTime(availabilityDTO.getShiftTimingStartTime());
            availability.setShiftTimingEndTime(availabilityDTO.getShiftTimingEndTime());
            availability.setStatus(availabilityDTO.getStatus());

            availabilityRepository.save(availability);

            GenericResponse response = new GenericResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setResponseCode(DOCTOR_AVAILABILITY_UPDATE_SUCCESS);
            response.setResponseMessage(messageSource.getMessage(DOCTOR_AVAILABILITY_UPDATE_SUCCESS, null, Locale.ENGLISH));

            return response;
        }catch(Exception e) {
            logger.error("Doctor Id Not Found{}", availabilityDTO.getDoctorId());
            throw new NotFoundException(messageSource.getMessage(DOCTOR_NOT_FOUND, null, Locale.ENGLISH));
        }
    }

    @Override
    public List<Availability> getAvailability() {
        logger.info("API call to get all the availability of doctors");
        return availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> getAvailabilityById(Long availabilityId) {
        logger.info("API call to get a doctor by their ID{}",availabilityId );
        Optional<Availability> availability = availabilityRepository.findById(availabilityId);
        if(availability.isEmpty()){
            logger.error("Invalid Id passed{}",availabilityId);
            throw new NotFoundException(messageSource.getMessage(DOCTOR_AVAILABILITY_NOT_FOUND, null, Locale.ENGLISH));
        }
        return availability;
    }
}