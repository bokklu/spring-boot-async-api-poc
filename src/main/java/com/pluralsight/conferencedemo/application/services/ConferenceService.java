package com.pluralsight.conferencedemo.application.services;

import com.pluralsight.conferencedemo.domain.Conference;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ConferenceService {
    CompletableFuture<Optional<Conference>> getConference(Integer conferenceId);
    CompletableFuture<Iterable<Conference>> getConferences();
}
