package com.techwizblog.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techwizblog.service.EarthQuakeDataService;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SIB on 8/12/2016.
 */
@RestController
public class GPSController {

    @Autowired
    private EarthQuakeDataService earthQuakeDataService;

    @Value("${api.url}")
    private String apiUrl;

    @RequestMapping(method = RequestMethod.GET, value = "/earthquake")
    public FeatureCollection getEarthQuakeData(@RequestParam(name = "source", required = true, defaultValue = "api") String source ) throws IOException {
        return source.equalsIgnoreCase("api")? getEarthQuakeDataAPI() : getEarthQuakeDataFile();
    }


    public FeatureCollection getEarthQuakeDataFile() throws IOException {

        String data = earthQuakeDataService.readData();

        return new ObjectMapper().readValue(data, FeatureCollection.class);
    }

    public FeatureCollection getEarthQuakeDataAPI() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setMessageConverters(getMessageConverters());

        ResponseEntity<FeatureCollection> response = restTemplate.getForEntity(apiUrl, FeatureCollection.class);
//        ResponseEntity<String> response = restTemplate.getForEntity("https://raw.githubusercontent.com/johan/world.geo.json/master/countries/USA/NC/Ashe.geo.json", String.class);

        return response.getBody();
    }


    private List<HttpMessageConverter<?>> getMessageConverters() {


        List<MediaType> jsonSupportedMediaType = new ArrayList<MediaType>();
        jsonSupportedMediaType.add(new MediaType("text", "javascript"));
        jsonSupportedMediaType.add(new MediaType("application", "json"));

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(jsonSupportedMediaType);

        List<HttpMessageConverter<?>> converters =
                new ArrayList<HttpMessageConverter<?>>();
        converters.add(jsonConverter);

        return converters;
    }
}
