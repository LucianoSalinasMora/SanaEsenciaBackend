package com.sanaesencia.backend.repository;

import com.sanaesencia.backend.entity.AboutInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutInfoRepository extends JpaRepository<AboutInfo, Integer> {
}
