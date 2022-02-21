package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value getDefaultValue() {
        return new BoolValue(true);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }
}
