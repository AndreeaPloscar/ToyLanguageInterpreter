package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.StringType;

public class StringValue implements Value{
    String string;
    public StringValue(String string) {
        this.string = string;
    }

    public String getValue(){
        return string;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(string);
    }

    @Override
    public String toString() {
        return "'" + string + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return string.equals(that.string);
    }

}
