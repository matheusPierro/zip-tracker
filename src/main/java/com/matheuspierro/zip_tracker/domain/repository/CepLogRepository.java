package com.matheuspierro.zip_tracker.domain.repository;

import com.matheuspierro.zip_tracker.domain.model.CepLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CepLogRepository extends MongoRepository<CepLog, String> {}

