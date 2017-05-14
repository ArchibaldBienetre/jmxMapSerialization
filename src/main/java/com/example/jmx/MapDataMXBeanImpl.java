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

    /**
     * Creating TabularData directly like this will not work - there will be no TabularData when deserializing.
     * <pre>
 public TabularData[] getTabularData() throws Exception {
     CompositeType rowType = new CompositeType("mapDataRowType", "row type",
     new String[]{"key", "value"}, new String[]{"map key", "map value"}, new OpenType<?>[]{SimpleType.STRING ,SimpleType.LONG});
     TabularType tabularType = new TabularType("mapDataTabularType", "a Map for JMX", rowType, new String[]{"key"});
     TabularDataSupport table = new TabularDataSupport(tabularType);
     _wrapped.forEach((key, value) -> {
         try {
            CompositeData compositeData = new CompositeDataSupport(rowType, new String[]{"key", "value"}, new Object[]{key, value});
            table.put(compositeData);
         } catch (OpenDataException e) {
            throw new RuntimeException(e);
         }
     });
     return new TabularData[]{table};
 }
     * </pre>
     */
    @Override
    public Map<String, Long> getMap() throws Exception {
        return _wrapped;
    }
}
