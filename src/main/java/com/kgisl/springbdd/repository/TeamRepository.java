package com.kgisl.springbdd.repository;

import com.kgisl.springbdd.entity.Team;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TeamRepository
 */
public interface TeamRepository extends JpaRepository<Team,Long>{

    
}