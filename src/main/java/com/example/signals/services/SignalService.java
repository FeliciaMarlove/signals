package com.example.signals.services;

import com.example.signals.dtos.SignalDto;
import com.example.signals.models.Keyword;
import com.example.signals.models.Signal;
import com.example.signals.repositories.KeywordRepository;
import com.example.signals.repositories.SignalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignalService {

    private final SignalRepository signalRepository;
    private final KeywordRepository keywordRepository;

    public SignalService(SignalRepository signalRepository, KeywordRepository keywordRepository) {
        this.signalRepository = signalRepository;
        this.keywordRepository = keywordRepository;
    }

    public List<SignalDto> getAllSignals() {
        return transformToDtoList(signalRepository.findAll());
    }

    private List<SignalDto> transformToDtoList(Iterable<Signal> signalIterable) {
        List<SignalDto> list = new ArrayList<>();
        for(Signal s: signalRepository.findAll()) {
            list.add(transformToDto(s));
        }
        return list;
    }

    private SignalDto transformToDto(Signal signal) {
        return new SignalDto(
                signal.getNodeId(),
                signal.getSamplingIntervalMs(),
                signal.getDeadbandValue(),
                signal.getDeadbandType(),
                signal.getActive(),
                signal.getKeywords().stream()
                        .map(Keyword::getId)
                        .collect(Collectors.toList())
        );
    }
}
