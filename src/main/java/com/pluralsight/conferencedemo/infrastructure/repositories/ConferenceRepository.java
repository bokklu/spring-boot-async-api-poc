package com.pluralsight.conferencedemo.infrastructure.repositories;

import com.pluralsight.conferencedemo.infrastructure.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
}
