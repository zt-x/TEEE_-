package com.teee.domain.returnClass;

import lombok.Data;

@Data
public class UploadErr {
    private String message;

    public UploadErr(String message) {
        this.message = message;
    }

    public UploadErr() {
    }
}
