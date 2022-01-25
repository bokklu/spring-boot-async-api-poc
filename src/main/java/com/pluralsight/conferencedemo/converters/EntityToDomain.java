package com.pluralsight.conferencedemo.converters;

import com.pluralsight.conferencedemo.infrastructure.entities.Conference;

public class EntityToDomain {
    public static com.pluralsight.conferencedemo.domain.Conference ToDomain(Conference conference) {
        return com.pluralsight.conferencedemo.domain.Conference.create(conference.getName());
    }
}