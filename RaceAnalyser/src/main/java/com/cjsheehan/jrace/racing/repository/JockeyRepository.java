package com.cjsheehan.jrace.racing.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cjsheehan.jrace.racing.Jockey;

// Spring generates implementation at runtime
public interface JockeyRepository extends CrudRepository<Jockey, Long> {
    List<Jockey> findByName(String name);
}
