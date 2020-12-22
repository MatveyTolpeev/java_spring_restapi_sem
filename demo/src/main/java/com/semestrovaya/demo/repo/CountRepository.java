package com.semestrovaya.demo.repo;

import com.semestrovaya.demo.models.Count;
import org.springframework.data.repository.CrudRepository;

public interface CountRepository extends CrudRepository<Count, Long> {
}
