package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

public class StringType implements IType{
    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }
}
