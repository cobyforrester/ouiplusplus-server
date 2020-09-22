package com.ouiplusplus.parser;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class ASTCombineTokens {

    public static Pair<Token, Error> combine(Token left, Token op, Token right, Position start, Position end) {

        if (left.getType() == TokenType.NULL || right.getType() == TokenType.NULL) {
            Error nullOp = new NullOperation(start, end, op.getValue());
            return new Pair<>(null, nullOp);
        }

        if (op.getType() == TokenType.CARROT) {
            if ((left.getType() != TokenType.DOUBLE
                    && left.getType() != TokenType.INT)
                    || (right.getType() != TokenType.INT
                    && right.getType() != TokenType.DOUBLE)) {
                Error invOper = new InvalidOperation(start, end, op.getValue());
                return new Pair<>(null, invOper);
            } try {
                double rightDouble = Double.parseDouble(right.getValue());
                double leftDouble = Double.parseDouble(left.getValue());
                double result = Math.pow(leftDouble, rightDouble);
                long ifInt = (long) result;
                if (left.getType() == TokenType.INT && right.getType() == TokenType.INT) {
                    Token token = new Token(TokenType.INT, Long.toString(ifInt), start, end);
                    return new Pair<>(token, null);
                }
                // For double
                Token token = new Token(TokenType.DOUBLE, Double.toString(result), start, end);
                return new Pair<>(token, null);
            } catch (Exception e) {
                Error overflow = new OverFlow(start, end, "");
                return new Pair<>(null, overflow);
            }
        }

        if (left.getType() == TokenType.LIST || right.getType() == TokenType.LIST) {
            // IMPLEMENT == IN THE FUTURE
            Token rtnTok = new Token(TokenType.LIST, "[]", start, end);
            if (left.isNeg() || right.isNeg()) {
                Error invOper = new InvalidOperation(start, end, "-");
                return new Pair<>(null, invOper);
            }
            if (op.getType() != TokenType.PLUS && op.getType() != TokenType.MULT) {
                Error invOper = new InvalidOperation(start, end, op.getValue());
                return new Pair<>(null, invOper);
            }
            if (op.getType() == TokenType.PLUS) {
                if (left.getType() != TokenType.LIST || right.getType() != TokenType.LIST) {
                    Error invOper = new InvalidOperation(start, end, op.getValue());
                    return new Pair<>(null, invOper);
                }
                List<Token> lst = left.getElements();
                lst.addAll(right.getElements());
                rtnTok.setElements(lst);
                return new Pair<>(rtnTok, null);
            } else if (op.getType() == TokenType.MULT) {
                if (left.getType() == TokenType.INT) {
                    if(left.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "-int*String");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            long leftVal = Long.parseLong(left.getValue());
                            List<Token> newLst = new ArrayList<>();
                            for(int i = 0; i < leftVal; i++) {
                                for (Token t: right.getElements()) {
                                    newLst.add(t.copy());
                                }
                            }
                            rtnTok.setElements(newLst);
                            return new Pair<>(rtnTok, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else if (right.getType() == TokenType.INT) {
                    if(right.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "-int*String");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            long rightVal = Long.parseLong(right.getValue());
                            List<Token> newLst = new ArrayList<>();
                            for(int i = 0; i < rightVal; i++) {
                                for (Token t: left.getElements()) {
                                    newLst.add(t.copy());
                                }
                            }
                            rtnTok.setElements(newLst);
                            return new Pair<>(rtnTok, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else {
                    Error invOper = new InvalidOperation(start, end, "*");
                    return new Pair<>(null, invOper);
                }
            }


        } else if (left.getType() == TokenType.STRING || right.getType() == TokenType.STRING) {
            Token rtnTkn = new Token(TokenType.STRING, "", start, end);
            if (op.getType() == TokenType.PLUS) {
                rtnTkn.setValue(left.getValue() + right.getValue());
                return new Pair<>(rtnTkn, null);
            }
            else if (op.getType() == TokenType.MULT) {
                if (left.getType() == TokenType.INT) {
                    if(left.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "-int*String");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            long leftVal = Long.parseLong(left.getValue());
                            String str = "";
                            for(int i = 0; i < leftVal; i++) str += right.getValue();
                            rtnTkn.setValue(str);
                            return new Pair<>(rtnTkn, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else if (right.getType() == TokenType.INT) {
                    if(right.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "-int*String");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            long rightVal = Long.parseLong(right.getValue());
                            String str = "";
                            for(int i = 0; i < rightVal; i++) str += left.getValue();
                            rtnTkn.setValue(str);
                            return new Pair<>(rtnTkn, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else {
                    Error invOper = new InvalidOperation(start, end, "");
                    return new Pair<>(null, invOper);
                }
            }

        } else if (left.getType() == TokenType.DOUBLE || right.getType() == TokenType.DOUBLE) {
            Token rtnTok = new Token(TokenType.DOUBLE, "", start, end);
            try {
                double val;
                double leftVal = Double.parseDouble(left.getValue());
                double rightVal = Double.parseDouble(right.getValue());
                if (op.getType() == TokenType.PLUS) val = leftVal + rightVal;
                else if (op.getType() == TokenType.MINUS) val = leftVal - rightVal;
                else if (op.getType() == TokenType.MULT) val = leftVal * rightVal;
                else if (op.getType() == TokenType.DIV) {
                    if (rightVal == 0) {
                        Error divBy0 = new DivisionBy0(start, end, "");
                        return new Pair<>(null, divBy0);
                    }
                    val = leftVal / rightVal;
                } else {
                    Error invOper = new InvalidOperation(start, end, op.getValue());
                    return new Pair<>(null, invOper);
                }
                rtnTok.setValue(Double.toString(val));
                return new Pair<>(rtnTok, null);
            } catch(Exception e) {
                Error overflow = new OverFlow(start, end, "");
                return new Pair<>(null, overflow);
            }

        } else if (left.getType() == TokenType.INT && right.getType() == TokenType.INT) {
            Token rtnTok = new Token(TokenType.INT);
            try{
                long val;
                long leftVal = Long.parseLong(left.getValue());
                long rightVal = Long.parseLong(right.getValue());
                if (op.getType() == TokenType.PLUS) val = leftVal + rightVal;
                else if (op.getType() == TokenType.MINUS) val = leftVal - rightVal;
                else if (op.getType() == TokenType.MULT) val = leftVal * rightVal;
                else if (op.getType() == TokenType.MODULO) val = leftVal % rightVal;
                else if (op.getType() == TokenType.DIV) {
                    if (rightVal == 0) {
                        Error divBy0 = new DivisionBy0(start, end, "");
                        return new Pair<>(null, divBy0);
                    }
                    val = leftVal / rightVal;
                } else {
                    Error invOper = new InvalidOperation(start, end, op.getValue());
                    return new Pair<>(null, invOper);
                }

                rtnTok.setValue(Long.toString(val));
                return new Pair<>(rtnTok, null);
            } catch(Exception e) {
                Error overflow = new OverFlow(start, end, "");
                return new Pair<>(null, overflow);
            }
        }
        Error invType = new InvalidType(start, end, "");
        return new Pair<>(null, invType);
    }
}