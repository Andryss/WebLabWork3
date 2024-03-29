package ru.andryss.weblab3.model.util;

import java.util.Objects;

public interface MBeanManager {
    void registerMBean(String beanName, Object bean);
    void unregisterMBean(Object bean);
}
