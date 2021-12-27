package com.itservice.repository;

import com.itservice.db.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    List<Calculation> findAllByDateOrType(String date,String type);
    Optional<Calculation> findById(Long id);
}
