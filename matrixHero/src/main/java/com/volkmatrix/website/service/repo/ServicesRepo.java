package com.volkmatrix.website.service.repo;

import com.volkmatrix.website.service.model.VolkServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepo extends JpaRepository<VolkServices, Long> {
}
