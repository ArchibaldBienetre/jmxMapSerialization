package com.example;

import com.example.jmx.MapDataEntry;
import org.junit.Test;

import javax.management.MBeanServer;
import javax.management.openmbean.CompositeData;
import java.util.List;

import static com.example.ExampleApp.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeserializeTest {

    @Test
    public void test_that_DataAsStringList_can_be_deserialized() throws Exception {
        ExampleApp exampleApp = new ExampleApp();
        Thread thread = new Thread(exampleApp, "ExampleApp");
        try {
            thread.start();
            MBeanServer server = registerAndWaitUntilInitialized(exampleApp);
            String[] dataAsStringList = (String[]) server.getAttribute(exampleObjectName(), "DataAsStringList");
            List<String> actualDataAsStringList = asList(dataAsStringList);
            assertEquals(exampleMapAsStringList(), actualDataAsStringList);
        } finally {
            exampleApp.terminate();
            thread.join(1000L);
        }
    }

    @Test
    public void test_that_Data_can_be_deserialized() throws Exception {
        ExampleApp exampleApp = new ExampleApp();
        Thread thread = new Thread(exampleApp, "ExampleApp");
        try {
            thread.start();
            MBeanServer server = registerAndWaitUntilInitialized(exampleApp);
            CompositeData[] data = (CompositeData[]) server.getAttribute(exampleObjectName(), "Data");
            assertEquals(3, data.length);
            List<MapDataEntry> actualData = asList(
                    new MapDataEntry((String) data[0].get("key"), (Long) data[0].get("value")),
                    new MapDataEntry((String) data[1].get("key"), (Long) data[1].get("value")),
                    new MapDataEntry((String) data[2].get("key"), (Long) data[2].get("value"))
            );
            assertEquals(exampleMapAsDataEntryList(), actualData);
        } finally {
            exampleApp.terminate();
            thread.join(1000L);
        }
    }

    private MBeanServer registerAndWaitUntilInitialized(ExampleApp exampleApp) throws InterruptedException {
        MBeanServer server = mBeanServer();
        int count = 0;
        while (!exampleApp.isInitialized() && count < 10) {
            Thread.sleep(100L);
            count++;
        }
        assertTrue("not initialized after 1 second", exampleApp.isInitialized());
        return server;
    }
}
