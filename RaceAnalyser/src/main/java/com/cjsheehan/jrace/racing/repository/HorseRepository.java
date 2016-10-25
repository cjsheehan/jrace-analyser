package com.cjsheehan.jrace.racing.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cjsheehan.jrace.racing.Horse;

// Spring generates implementation at runtime
public interface HorseRepository extends CrudRepository<Horse, Long> {
    List<Horse> findByName(String name);
}
