package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class UnexpectedChar extends Error {
    public UnexpectedChar() {
        super("Unexpected Character");
    }
    public UnexpectedChar(Position start, Position end, String details) {
        super(start, end, "Unexpected Character", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}
