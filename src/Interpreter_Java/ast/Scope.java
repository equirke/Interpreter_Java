package Interpreter_Java.ast;

import Interpreter_Java.Token;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private final Scope scopeEnclosed;
    private Map<String, Object> variables = new HashMap<>();

    public Scope(){
        scopeEnclosed = null;
    }

    public Scope(Scope scopeEnclosed){
        this.scopeEnclosed = scopeEnclosed;
    }

    public Object get(Token var){
        if(!variables.containsKey(var.token)){
            if(scopeEnclosed == null) throw new RuntimeError(var,"Variable must be declared");
            return scopeEnclosed.get(var);
        }
        return variables.get(var.token);
    }

    public Object declare(Token var, Object value){
        variables.put(var.token, value);
        return value;
    }

    public Object assign(Token var, Object value){
        if(!variables.containsKey(var.token)){
            if(scopeEnclosed == null){
                if(scopeEnclosed == null) throw new RuntimeError(var,"Variable must be declared");
            }

            return scopeEnclosed.assign(var, value);
        }

        variables.put(var.token, value);
        return value;
    }

    public Scope getScopeEnclosed(){
        return scopeEnclosed;
    }
}
