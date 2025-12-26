package beloved.beloved.controller;

import beloved.beloved.entity.SpecialDay;
import beloved.beloved.repository.SpecialDayRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/special-days")
public class SpecialDayController {

    private final SpecialDayRepository specialDayRepository;

    public SpecialDayController(SpecialDayRepository specialDayRepository) {
        this.specialDayRepository = specialDayRepository;
    }

    @GetMapping
    public List<SpecialDay> getAllSpecialDays() {
        return specialDayRepository.findAll();
    }
}
