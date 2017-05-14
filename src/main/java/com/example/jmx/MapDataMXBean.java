package com.example.jmx;

import java.util.List;
import java.util.Map;

/**
 * MXBean interface s.t. content of a map can be shown in JConsole
 *
 * This shows two alternatives at how to do it in one class:
 * a) stringify your key-value pairs and return them as List
 * b) (automatically) create CompositeData
 * c) (automatically) create TabularData
 */
@SuppressWarnings("unused")
public interface MapDataMXBean {

    List<String> getDataAsStringList() throws Exception;

    List<MapDataEntry> getData() throws Exception;

    Map<String, Long> getMap() throws Exception;
}
