package com.techwizblog.service;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by SIB on 8/14/2016.
 */
@Component
public class EarthQuakeDataService {


    public String readData() throws IOException {
        ClassPathResource resource = new ClassPathResource("file/data.geojson");
        return IOUtils.toString(resource.getInputStream());
    }

}
