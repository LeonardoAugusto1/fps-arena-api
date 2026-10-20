package com.example.fpsarena.dto;

import com.example.fpsarena.model.TimeJogo;

public class EscolherTimeRequest {

    private TimeJogo timeJogo;

    public TimeJogo getTimeJogo() {
        return timeJogo;
    }

    public void setTimeJogo(TimeJogo timeJogo) {
        this.timeJogo = timeJogo;
    }
}