package com.example.signals.repositories;

import com.example.signals.models.Signal;
import org.springframework.data.repository.CrudRepository;

public interface SignalRepository extends CrudRepository<Signal, String> {
}
