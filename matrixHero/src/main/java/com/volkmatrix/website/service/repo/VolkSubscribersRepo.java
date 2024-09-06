package com.volkmatrix.website.service.repo;

import com.volkmatrix.website.service.model.VolkMailSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolkSubscribersRepo extends JpaRepository<VolkMailSubscribers, Long> {
  VolkMailSubscribers findByEmail(String lowerCase);

  VolkMailSubscribers findByEmailAndActiveFlgTrue(String lowerCase);
}
