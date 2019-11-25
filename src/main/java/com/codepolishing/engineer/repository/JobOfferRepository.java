package com.codepolishing.engineer.repository;

import com.codepolishing.engineer.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer,Integer> {

    JobOffer findById(int id);

}
