package ru.andryss.weblab3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private long responseTime = System.currentTimeMillis();
    private long executionTime;
    private double x;
    private double y;
    private double r;
    private boolean result;

    public Response(long executionTIme, Request request, boolean result) {
        this.executionTime = executionTIme;
        this.x = request.getX();
        this.y = request.getY();
        this.r = request.getR();
        this.result = result;
    }
}
