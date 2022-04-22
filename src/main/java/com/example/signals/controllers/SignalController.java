package com.example.signals.controllers;

import com.example.signals.dtos.SignalDto;
import com.example.signals.services.SignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/signal")
public class SignalController {
    private final SignalService signalService;

    public SignalController(SignalService signalService) {
        this.signalService = signalService;
    }

    @GetMapping("/{id:.+}")
    public SignalDto getSignal(@PathVariable("id") String id) {
        return signalService.getSignal(id);
    }

    @GetMapping()
    public List<SignalDto> getSignals() {
        return signalService.getAllSignals();
    }

    @GetMapping("/keywords")
    public List<String> getKeywordsNames(@PathVariable("id") String id) {
        return signalService.getKeywordNamesForSignal(id);
    }
}
