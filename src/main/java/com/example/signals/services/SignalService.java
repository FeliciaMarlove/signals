package com.example.signals.services;

import com.example.signals.dtos.SignalDto;
import com.example.signals.models.Keyword;
import com.example.signals.models.Signal;
import com.example.signals.repositories.KeywordRepository;
import com.example.signals.repositories.SignalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public SignalDto getSignal(String id) {
        Optional<Signal> optionalSignal = signalRepository.findById(id);
        return optionalSignal.map(this::transformToDto).orElse(null);
    }

    public List<String> getKeywordNamesForSignal(String signalId) {
        SignalDto signalDto = getSignal(signalId);
        int[] keywordIds = signalDto.getKeywordsIds();
        List<String> keywordsNames = new ArrayList<>();
        for(int kwi: keywordIds) {
            Optional<Keyword> optionalKeyword = keywordRepository.findById(kwi);
            Keyword keyword = optionalKeyword.orElseThrow(() -> new RuntimeException("No keyword found with id " + kwi));
            keywordsNames.add(keyword.getName());
        }
        return keywordsNames;
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
                getKeywordIds(signal.getKeywordIds())
        );
    }

    private int[] getKeywordIds(String keywordIds) {
        String[] ids = keywordIds.split(";");
        int[] intIds = new int[ids.length];
        for(int i = 0; i < ids.length; i++) {
            String stringId = ids[i];
            try {
                int id = Integer.parseInt(stringId);
                intIds[i] = id;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Keyword ID " + stringId + " is not an integer");
            }
        }
        return intIds;
    }
}
