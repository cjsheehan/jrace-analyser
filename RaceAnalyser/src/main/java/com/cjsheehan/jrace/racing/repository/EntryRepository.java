package com.cjsheehan.jrace.racing.repository;

import org.springframework.data.repository.CrudRepository;

import com.cjsheehan.jrace.racing.Entry;

// Spring generates implementation at runtime
public interface EntryRepository extends CrudRepository<Entry, Long> {
}
