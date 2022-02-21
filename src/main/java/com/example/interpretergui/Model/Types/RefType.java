package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class RefType implements IType{
    IType inner;

    public IType getInner() {
        return inner;
    }

    public RefType(IType inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        return false;
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(inner.deepCopy());
    }
    @Override
    public String toString() {
        return "Ref " + inner.toString();
    }


}
