package com.sopt.sopkathon.domain.fail.repository;

import com.sopt.sopkathon.domain.fail.entity.FailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailRepository extends JpaRepository<FailEntity, Long> {
    List<FailEntity> findAllByUserId(final Long userId);
}
