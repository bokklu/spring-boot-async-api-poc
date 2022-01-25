package com.pluralsight.conferencedemo.application.services;

import com.pluralsight.conferencedemo.converters.EntityToDomain;
import com.pluralsight.conferencedemo.domain.Conference;
import com.pluralsight.conferencedemo.infrastructure.repositories.ConferenceRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Async
    @Override
    public CompletableFuture<Optional<Conference>> getConference(Integer conferenceId) {
        return CompletableFuture.supplyAsync(() -> this.conferenceRepository.findById(conferenceId))
                .thenApply(conferenceEntity -> conferenceEntity.map(conference -> EntityToDomain.ToDomain(conference)));
    }

    @Async
    @Override
    public CompletableFuture<Iterable<Conference>> getConferences() {
        return CompletableFuture.supplyAsync(() -> this.conferenceRepository.findAll())
                .thenApply(conferenceEntities -> StreamSupport
                        .stream(conferenceEntities.spliterator(), false)
                        .map(entity -> EntityToDomain.ToDomain(entity))
                        .collect(Collectors.toList()));
    }
}
