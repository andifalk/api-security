package com.example.workshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkshopApi {

    @GetMapping("/api/workshop")
    public String contactWorkshop(@RequestParam("vin") String vin, @RequestParam("problem") String problem) {
        return String.format(
                "<IMG SRC=\"none\" onerror=\"alert('Request to workshop successfully processed for VIN [%s] and Problem [%s]')\"></img>",
                vin, problem);
    }
}
