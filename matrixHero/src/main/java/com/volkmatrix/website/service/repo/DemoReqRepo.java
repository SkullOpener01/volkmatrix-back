package com.volkmatrix.website.service.repo;

import com.volkmatrix.website.service.model.DemoRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoReqRepo extends JpaRepository<DemoRequests, Long> {
}
