package com.cjsheehan.jrace.racing.repository;

import org.springframework.data.repository.CrudRepository;

import com.cjsheehan.jrace.racing.Race;

// Spring generates implementation at runtime
public interface RaceRepository extends CrudRepository<Race, Long> {
}
