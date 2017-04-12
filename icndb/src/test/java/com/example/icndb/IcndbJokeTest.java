package com.example.icndb;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by zhangxiangdong on 2017/4/12.
 */
public class IcndbJokeTest {

    private static final String jsonTxt = "{\"type\": \"success\", \"value\": {\"id\": 451," +
            "\"joke\": \"Xav Ducrohet writes code that optimizes itself.\", " +
            "\"categories\": [\"nerdy\"]}}";

    @Test
    public void parseJson_isTheRightJoke() {
        Gson gson = new Gson();
        IcndbJoke icndbJoke = gson.fromJson(jsonTxt, IcndbJoke.class);

        assertNotNull(icndbJoke);

        String expectedJoke = "Xav Ducrohet writes code that optimizes itself.";
        assertEquals(expectedJoke, icndbJoke.getJoke());
    }

}