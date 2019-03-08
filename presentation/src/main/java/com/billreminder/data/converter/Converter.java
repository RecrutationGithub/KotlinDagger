package com.billreminder.data.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Converter<U, V> {
    @NonNull V convert(@Nullable U input);
}