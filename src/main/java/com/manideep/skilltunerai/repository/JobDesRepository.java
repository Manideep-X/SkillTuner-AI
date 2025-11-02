package com.manideep.skilltunerai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manideep.skilltunerai.entity.JobDescription;

public interface JobDesRepository extends JpaRepository<JobDescription, Long> {

    // Fetch all job descriptions of a resume
    // Internal query: select * from job_description_table where resume_id = ?1;
    List<JobDescription> findByResume_Id(long resumeId);

    // Fetches all job titles of a resume
    @Query("SELECT jd.jobTitle FROM JobDescription WHERE jd.resume.id = :resumeId")
    List<String> findAllJobTitlesByResume_Id(@Param("resumeId") long resumeId);

}
