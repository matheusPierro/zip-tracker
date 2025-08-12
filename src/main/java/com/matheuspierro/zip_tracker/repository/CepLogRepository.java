package com.matheuspierro.zip_tracker.repository;

import com.matheuspierro.zip_tracker.model.CepLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CepLogRepository extends MongoRepository<CepLog, String> {}

