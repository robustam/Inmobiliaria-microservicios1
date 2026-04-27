package com.inmobiliaria.opinionesservice.repository;

import com.inmobiliaria.opinionesservice.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOpinionRepository extends JpaRepository<Opinion, Long> {
}