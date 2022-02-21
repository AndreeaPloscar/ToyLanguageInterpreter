package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;

public class IntValue implements Value{
    private int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

}
