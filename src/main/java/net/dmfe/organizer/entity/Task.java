package net.dmfe.organizer.entity;

import java.util.UUID;

public record Task(UUID id, String details, boolean completed, UUID userId) {

    public Task(String details, UUID userId) {
        this(UUID.randomUUID(), details, false, userId);
    }

}
