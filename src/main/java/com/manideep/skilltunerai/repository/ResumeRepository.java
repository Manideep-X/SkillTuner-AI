package com.manideep.skilltunerai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    // Find resume by it's unique title for a user
    // Internal query: select * from resume_table where resume_title = ?1 and user_id = ?2;
    Optional<Resume> findByResumeTitleAndUser(String resumeTitle, Users user);

    // Find all resumes belongs to a user
    // Internal query: select * from resume_table where user_id = ?1;
    List<Resume> findAllByUser(Users user);

    // Find resume with the help of resume ID and user ID
    // Internal query: select * from resume_table where id = ?1 user_id = ?2;
    Optional<Resume> findByIdAndUserId(long id, long userId);

    // Find all resumes with atleast one job description for a user
    // Internal query: select distinct r.* from resume_table r join job_description j on j.resume_id = r.id where r.user_id = :userId
    List<Resume> findDistinctByUserIdAndJobDescriptionsIsNotEmpty(long userId);

}
