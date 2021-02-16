package com.poc.PocJPAyRedis.repositories;

import com.poc.PocJPAyRedis.models.RealState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealStateRepository extends JpaRepository<RealState, Long> {
}
