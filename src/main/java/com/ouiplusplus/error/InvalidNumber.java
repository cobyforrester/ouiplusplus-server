package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidNumber extends Error {

    public InvalidNumber(Position start, Position end, String details) {
        super(start, end, "Invalid Number", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Numéro Invalide");
    }
}