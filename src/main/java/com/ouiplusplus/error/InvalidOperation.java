package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidOperation extends Error {
    public InvalidOperation() {
        super("Invalid Operation");
    }
    public InvalidOperation(Position start, Position end, String details) {
        super(start, end, "Invalid Operation", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}