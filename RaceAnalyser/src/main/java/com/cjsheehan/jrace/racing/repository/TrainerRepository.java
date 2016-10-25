package com.cjsheehan.jrace.racing.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cjsheehan.jrace.racing.Trainer;

// Spring generates implementation at runtime
public interface TrainerRepository extends CrudRepository<Trainer, Long> {
    List<Trainer> findByName(String name);
}
