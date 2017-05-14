package com.example.jmx;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @inheritDoc MapDataMXBean
 */
public class MapDataMXBeanImpl implements MapDataMXBean {

    private final Map<String, Long> _wrapped;

    public MapDataMXBeanImpl(Map<String, Long> wrapped) {
        _wrapped = wrapped;
    }

    @Override
    public List<String> getDataAsStringList() throws Exception {
        return _wrapped.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(toList());
    }

    @Override
    public List<MapDataEntry> getData() throws Exception {
        return _wrapped.entrySet().stream()
                .map(e -> new MapDataEntry(e.getKey(), e.getValue()))
                .collect(toList());
    }
}
