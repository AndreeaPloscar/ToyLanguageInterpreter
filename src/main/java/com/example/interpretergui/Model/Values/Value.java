package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.IType;

public interface Value {
    IType getType();
    Value deepCopy();
    public String toString();
}
