package com.sopt.sopkathon.domain.fail.repository;

import com.sopt.sopkathon.domain.fail.entity.FailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailRepository extends JpaRepository<FailEntity, Long> {
}
