package com.techwizblog.service.controller.rest.com.techwizblog.service;

import com.techwizblog.Application;
import com.techwizblog.service.EarthQuakeDataService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Created by SIB on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EarthQuakeDataServiceTest {

    private EarthQuakeDataService earthQuakeDataService;

    @Before
    public void setup()
    {
        earthQuakeDataService = new EarthQuakeDataService();
    }

    @Test
    public void testReadData() throws IOException {
        String data = earthQuakeDataService.readData();
        Assert.hasText(data);
    }


}
