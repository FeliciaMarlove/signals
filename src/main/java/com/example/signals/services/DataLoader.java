package com.example.signals.services;

import com.example.signals.models.DeadbandType;
import com.example.signals.models.Keyword;
import com.example.signals.models.Signal;
import com.example.signals.repositories.KeywordRepository;
import com.example.signals.repositories.SignalRepository;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
@Component
public class DataLoader implements ApplicationRunner {
    private final KeywordRepository keywordRepository;
    private final SignalRepository signalRepository;
    private static final String keywordsCsvPath = "./src/main/resources/static/keywords.csv";
    private static final String signalsCsvPath = "./src/main/resources/static/signals.csv";

    public DataLoader(KeywordRepository keywordRepository, SignalRepository signalRepository) {
        this.keywordRepository = keywordRepository;
        this.signalRepository = signalRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fillSignalTable();
        fillKeywordTable();
    }

    private void fillKeywordTable() throws IOException {
        if (keywordRepository.count() == 0) {
            log.info("Populating database with keywords");
            Path path = Paths.get(keywordsCsvPath);

            try (BufferedReader br = new BufferedReader(new FileReader(keywordsCsvPath))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    keywordRepository.save(
                            new Keyword(Integer.parseInt(values[0]),
                                    values[1],
                                    values[2]));
                }
            }
            log.info("Keywords were added to the database");
        }
    }

    private void fillSignalTable() throws IOException {
        if (signalRepository.count() == 0) {
            log.info("Populating database with signals");
            try (BufferedReader br = new BufferedReader(new FileReader(signalsCsvPath, StandardCharsets.UTF_8))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values[0].trim().isEmpty()) {
                        log.warning("Skipping empty line");
                        continue;
                    }
                    if (values.length == 1) {
                        log.warning("Skipping incomplete record " + values[0]);
                        continue;
                    }
                    signalRepository.save(
                            new Signal(values[0],
                                    Integer.valueOf(values[1]),
                                    !values[2].trim().isEmpty() ? Integer.valueOf(values[2]) : null,
                                    !values[3].trim().isEmpty() ? DeadbandType.valueOf(values[3]) : null,
                                    values[4].equals("1") ?
                                            Boolean.TRUE : Boolean.FALSE,
                                    values[5]
                                    ));
                }
                log.info("Signals were added to the database");
            }
        }
    }
}
