package net.dmfe.organizer.dto;

import java.util.List;

public record ErrorResponse(List<String> errors) {
}
