package com.billreminder.ui.base.binding;

import androidx.databinding.ObservableField;
import androidx.annotation.Nullable;

public class ObservableString extends ObservableField<String> {
    public ObservableString() {
        super();
    }

    public ObservableString(@Nullable String text) {
        super(text);
    }
}