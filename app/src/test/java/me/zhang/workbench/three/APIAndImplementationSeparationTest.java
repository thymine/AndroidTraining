package me.zhang.workbench.three;

import com.jakewharton.fliptables.FlipTable;

import org.junit.Test;

/**
 * Created by Zhang on 1/21/2018 12:15 PM.
 */
public class APIAndImplementationSeparationTest {
    @Test
    public void testApiAndImplementation() throws Exception {
        String[] headers = {"Test", "Header"};
        String[][] data = {
                {"Foo", "Bar"},
                {"Kit", "Kat"},
        };
        System.out.println(FlipTable.of(headers, data));
    }

}