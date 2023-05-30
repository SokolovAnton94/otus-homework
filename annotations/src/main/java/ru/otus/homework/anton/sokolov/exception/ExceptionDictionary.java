package ru.otus.homework.anton.sokolov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionDictionary {
    CALL_METHOD_ERROR("Error: %s" + System.lineSeparator() + "Occurred when calling the method: %s"),
    CREATE_TEST_CLASS_ERROR("Error when creating a test class object."),
    TEST_CLASS_NOT_FOUND("Testing aborted. Test class not found."),
    TESTING_ABORTED("Testing aborted. %s" + System.lineSeparator());

    private final String message;
}
