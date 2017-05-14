package com.example;

import com.example.jmx.MapDataEntry;
import com.example.jmx.MapDataMXBeanImpl;
import com.google.common.annotations.VisibleForTesting;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Both Main class for tryouts and helper class for tests
 */
public class ExampleApp implements Runnable {

    public static void main(String[] args) throws Exception {
        ExampleApp exampleApp = new ExampleApp();
        System.out.println("Waiting until there is a keystroke... Now go observe it in jconsole!");
        Thread thread = new Thread(exampleApp, "exampleApp");
        thread.start();
        System.in.read();
        exampleApp.terminate();
        thread.join();
    }

    private final AtomicBoolean _keepRunning = new AtomicBoolean(true);
    private final AtomicBoolean _initialized = new AtomicBoolean(false);

    public void run() {
        MBeanServer server = mBeanServer();
        try {
            MapDataMXBeanImpl mapDataMBean = new MapDataMXBeanImpl(exampleMap());
            server.registerMBean(mapDataMBean, exampleObjectName());
            _initialized.compareAndSet(false, true);
            while (_keepRunning.get()) {
                Thread.sleep(100L);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                server.unregisterMBean(exampleObjectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    void terminate() {
        _keepRunning.compareAndSet(true, false);
    }

    boolean isInitialized() {
        return _initialized.get();
    }

    @VisibleForTesting
    static MBeanServer mBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    @VisibleForTesting
    static ObjectName exampleObjectName() {
        try {
            return new ObjectName("com.example.ExampleApp.mapDataMBean:type=MapDataMXBean");
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Long> exampleMap() {
        Map<String, Long> map = new HashMap<>();
        map.put("key1", 12L);
        map.put("key2", 23L);
        map.put("key3", 42L);
        return map;
    }

    @VisibleForTesting
    static List<MapDataEntry> exampleMapAsDataEntryList() {
        List<MapDataEntry> mapAsList = new ArrayList<>();
        mapAsList.add(new MapDataEntry("key1", 12L));
        mapAsList.add(new MapDataEntry("key2", 23L));
        mapAsList.add(new MapDataEntry("key3", 42L));
        return mapAsList;
    }

    @VisibleForTesting
    static List<String> exampleMapAsStringList() {
        List<String> mapAsList = new ArrayList<>();
        mapAsList.add("key1: 12");
        mapAsList.add("key2: 23");
        mapAsList.add("key3: 42");
        return mapAsList;
    }
}
