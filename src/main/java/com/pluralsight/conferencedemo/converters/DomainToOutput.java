package com.pluralsight.conferencedemo.converters;

import com.pluralsight.conferencedemo.domain.Conference;

public class DomainToOutput {
    public static com.pluralsight.conferencedemo.models.output.Conference ToOutput(Conference conference) {
        return com.pluralsight.conferencedemo.models.output.Conference.create(conference.getName());
    }
}
