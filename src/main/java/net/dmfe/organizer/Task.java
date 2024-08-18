package net.dmfe.organizer;

import java.util.UUID;

public record Task(UUID id, String description, boolean completed) {

    public Task(String description) {
        this(UUID.randomUUID(), description, false);
    }

}
