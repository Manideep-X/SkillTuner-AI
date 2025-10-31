package com.manideep.skilltunerai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.skilltunerai.entity.JobDescription;

public interface JobDesRepository extends JpaRepository<JobDescription, Long> {

    // Fetch all job descriptions of a resume
    // Internal query: select * from job_description_table where resume_id = ?1;
    Optional<List<JobDescription>> findByResume_id(long resumeId);

}
