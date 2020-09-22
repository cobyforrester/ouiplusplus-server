package com.ouiplusplus.lexer;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.parser.TGParser;
import com.ouiplusplus.prebuiltfunctions.PreBuiltFunctions;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Run {
    public Run() {
    }

    public Pair<String, Error> generateOutput(String fn, String text) {
        /*
        GENERATES A STRING BY TOKENIZING, GROUPING TOKENS, PROCESSING TOKENS
         */

        // TOKENIZING
        Lexer lexer = new Lexer(fn, text);
        Pair<List<Token>, Error> lexerPair = lexer.make_tokens(); //returns
        Error error = lexerPair.getP2();
        if(error != null) return new Pair<>(null, error);
        List<Token> tokenLst = lexerPair.getP1();

        List<String> vars = new ArrayList<>();
        Map<String, List<String>> functions = new HashMap<>();
        for (String s : PreBuiltFunctions.getFunctions()) {
            functions.put(s, null);
        }
        Pair<List<TokenGroup>, Error> tgPair = GenerateTGLst.generateTokenGroupLst
                (tokenLst, vars, functions);
        error = tgPair.getP2();
        if (error != null) return new Pair<>(null, error);
        //PROCESS TOKENS/GENERATE OUTPUT STRING AND RETURN
        TGParser tgparser = new TGParser();
        Pair<Token, Error> parsPair =  tgparser.process(tgPair.getP1());
        if (parsPair.getP2() != null) return new Pair<>(null, parsPair.getP2());
        return new Pair<>(tgparser.getOutput(), null);
    }
}
