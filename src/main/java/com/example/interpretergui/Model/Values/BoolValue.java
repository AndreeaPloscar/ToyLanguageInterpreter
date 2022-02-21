package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;

public class BoolValue implements Value{

    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return value == boolValue.value;
    }

}
