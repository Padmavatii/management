package management.controller;

import jakarta.validation.Valid;
import management.common.GenericResponse;
import management.dto.AvailabilityDTO;
import management.interfaces.availabilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hms")
public class AvailabilityController {

    private final availabilityInterface iAvailabilityService;

    @Autowired
    public AvailabilityController(availabilityInterface iAvailabilityService) {
        this.iAvailabilityService = iAvailabilityService;
    }

    @PostMapping("/availability")
    public GenericResponse addAvailability(@RequestBody @Valid AvailabilityDTO availabilityDTO){
        return iAvailabilityService.submitAvailability(availabilityDTO);
    }

    @PutMapping("/availability/{id}")
    public GenericResponse updateAvailability(@PathVariable Long id, @RequestBody @Valid AvailabilityDTO availabilityDTO){
        return iAvailabilityService.updateAvailability(id, availabilityDTO);
    }


}
