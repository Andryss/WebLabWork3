package ru.andryss.weblab3.model.util;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(name = "beanManager")
@ApplicationScoped
public class MBeanManagerImpl implements MBeanManager {

    private final Map<Object, ObjectName> objectNameMap = new HashMap<>();
    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    @Override
    public void registerMBean(String beanName, Object bean) {
        try {
            String domain = bean.getClass().getPackage().getName();
            String type = bean.getClass().getSimpleName();
            ObjectName objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, beanName));
            mBeanServer.registerMBean(bean, objectName);
            objectNameMap.put(bean, objectName);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException |
                 MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterMBean(Object bean) {
        try {
            ObjectName objectName = objectNameMap.get(bean);
            if (objectName == null)
                throw new IllegalArgumentException("Bean is not registered: " + bean);
            mBeanServer.unregisterMBean(objectName);
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
