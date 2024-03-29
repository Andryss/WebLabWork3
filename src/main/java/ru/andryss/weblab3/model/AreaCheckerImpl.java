package ru.andryss.weblab3.model;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "areaChecker")
@ApplicationScoped
public class AreaCheckerImpl implements AreaChecker {
    @Override
    public boolean check(Request request) {
        double x = request.getX(),
                y = request.getY(),
                r = request.getR();
        return (x <= 0 && y >= 0 && (y <= x + r / 2)) ||
                (x <= 0 && y <= 0 && (Math.sqrt(x * x + y * y) <= r / 2)) ||
                (x >= 0 && y <= 0 && (x <= r / 2 && y >= - r));
    }
}
