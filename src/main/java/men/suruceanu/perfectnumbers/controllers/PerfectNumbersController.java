package men.suruceanu.perfectnumbers.controllers;

import men.suruceanu.perfectnumbers.dto.InvalidRangeException;
import men.suruceanu.perfectnumbers.services.PerfectNumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PerfectNumbersController {

    @Autowired
    private PerfectNumbersService perfectNumbersService;

    @GetMapping("perfect/number/check/{number}")
    public boolean isPerfectNumber(@PathVariable long number) {
        return perfectNumbersService.isPerfectNumber(number);
    }


    @GetMapping("perfect/number/range")
    public List<Long> getPerfectNumbersByRange(@RequestParam("start") long start, @RequestParam("end") long end) throws InvalidRangeException {
        return perfectNumbersService.getPerfectNumbersByRange(start, end);
    }

}
