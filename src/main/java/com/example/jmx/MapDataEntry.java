package com.example.jmx;

import java.beans.ConstructorProperties;

/**
 * Analog to https://docs.oracle.com/javase/tutorial/jmx/mbeans/mxbeans.html
 *
 * For JMX, it seems, this needs to be statically typed - my attempts at using generics (MapDataEntry<K, V>) failed.
 */
@SuppressWarnings("unused")
public class MapDataEntry {
    private final String _key;
    private final Long _value;

    @ConstructorProperties({"key", "value"})
    public MapDataEntry(String key, Long value) {
        _key = key;
        _value = value;
    }

    public String getKey() {
        return _key;
    }

    public Long getValue() {
        return _value;
    }

    /* generated (IDEA 2017.1) */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapDataEntry that = (MapDataEntry) o;

        if (_key != null ? !_key.equals(that._key) : that._key != null) return false;
        return _value != null ? _value.equals(that._value) : that._value == null;
    }

    @Override
    public int hashCode() {
        int result = _key != null ? _key.hashCode() : 0;
        result = 31 * result + (_value != null ? _value.hashCode() : 0);
        return result;
    }
}
