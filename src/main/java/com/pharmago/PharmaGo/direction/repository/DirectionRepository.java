package com.pharmago.PharmaGo.direction.repository;

import com.pharmago.PharmaGo.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
