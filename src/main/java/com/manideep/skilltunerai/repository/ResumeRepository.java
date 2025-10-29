package com.manideep.skilltunerai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.skilltunerai.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    // Find resume by it's unique title
    // Internal query: select * from resume_table where resume_title = ?1;
    Optional<Resume> findByResumeTitle(String resumeTitle);

}
