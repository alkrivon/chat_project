package ru.simbirsoft.chat_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthException extends Exception {
    private final String message;
}
