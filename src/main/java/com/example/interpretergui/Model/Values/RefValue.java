package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.RefType;

public class RefValue implements Value{

    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(address) + " "+ locationType.toString() + ")";
    }
}
