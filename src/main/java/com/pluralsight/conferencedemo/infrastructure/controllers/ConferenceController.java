package com.pluralsight.conferencedemo.infrastructure.controllers;

import com.pluralsight.conferencedemo.application.services.ConferenceService;
import com.pluralsight.conferencedemo.converters.DomainToOutput;
import com.pluralsight.conferencedemo.models.output.Conference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/v1/conference")
public class ConferenceController {
    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Async
    @GetMapping("/{conferenceId}")
    @Operation(summary = "Returns a conference by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully get a conference by Id", content = @Content(schema = @Schema(implementation = Conference.class))),
            @ApiResponse(responseCode = "404", description = "No conferences found"),
            @ApiResponse(responseCode = "500", description = "Technical error")
    })
    public CompletableFuture<ResponseEntity<Conference>> getConference(@PathVariable Integer conferenceId) {
        if (conferenceId <= 0) {
            ResponseEntity<Conference> badRequestResponse = ResponseEntity.badRequest().build();
            return CompletableFuture.completedFuture(badRequestResponse);
        }

        return this.conferenceService.getConference(conferenceId)
                .thenApply(maybeConference -> ResponseEntity.of(maybeConference.map(conference -> DomainToOutput.ToOutput(conference))))
                .exceptionally(x -> ResponseEntity.internalServerError().build());
    }

    @Async
    @GetMapping
    @Operation(summary = "Returns a list of conferences")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully get a list of conferences", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Conference.class)))),
            @ApiResponse(responseCode = "404", description = "No conferences found"),
            @ApiResponse(responseCode = "500", description = "Technical error")
    })
    public CompletableFuture<ResponseEntity<Iterable<Conference>>> getConferences() {
        return this.conferenceService
                .getConferences()
                .thenApply(conferences -> {
                    Iterable<Conference> outputConferences = StreamSupport
                            .stream(conferences.spliterator(), false)
                            .map(domain -> DomainToOutput.ToOutput(domain))
                            .collect(Collectors.toList());

                    if (!outputConferences.iterator().hasNext()) {
                        ResponseEntity<Iterable<Conference>> notFoundResponse = ResponseEntity.notFound().build();
                        return notFoundResponse;
                    }

                    return ResponseEntity.ok(outputConferences);
                })
                .exceptionally(x -> ResponseEntity.internalServerError().build());
    }
}
