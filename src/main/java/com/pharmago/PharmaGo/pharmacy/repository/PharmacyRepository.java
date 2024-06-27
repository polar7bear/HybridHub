package com.pharmago.PharmaGo.pharmacy.repository;

import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
