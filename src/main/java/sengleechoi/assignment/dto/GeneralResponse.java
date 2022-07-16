package sengleechoi.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralResponse {
    private int statusCode;
    private String message;
}
