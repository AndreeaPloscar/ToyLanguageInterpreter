package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.Value;

public interface IType {
    Value getDefaultValue();
    IType deepCopy();
    String toString();
    boolean equals(Object obj);
}
