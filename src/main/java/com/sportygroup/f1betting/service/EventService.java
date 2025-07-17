package com.sportygroup.f1betting.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.f1betting.model.response.DriverMarket;
import com.sportygroup.f1betting.model.response.EventResponse;
import com.sportygroup.f1betting.model.response.SimpleDriver;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Fetches and returns a list of Formula 1 events from the external API,
     * applying optional filters by session type, year, and country. 
     * Each event includes a randomly generated driver market.
     * @param year
     * @param sessionType
     * @param country
     * @return results - List<EventResponse> 
     */
    public List<EventResponse> getFilteredEvents(String year, String sessionType, String country) {

    	//default sessions url
        String baseUrl = "https://api.openf1.org/v1/sessions";

        List<Map<String, Object>> resSessions = null;
        
        if (year == null && sessionType == null && country == null) {
            // No filters given, call unfiltered
        	resSessions = restTemplate.getForObject(baseUrl, List.class);
        } else {
        	//add filters year, session_type, country_name
        	StringBuilder url = new StringBuilder(baseUrl+"?");
        	if (year != null) url.append("year=").append(year).append("&");
        	if (sessionType != null) url.append("session_type=").append(sessionType).append("&");
        	if (country != null) url.append("country_name=").append(country).append("&");

        	String finalUrl = url.toString();
        	if (finalUrl.endsWith("&") || finalUrl.endsWith("?")) {
        	    finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        	}
        	resSessions = restTemplate.getForObject(finalUrl, List.class);
        }

        if (resSessions == null) return Collections.emptyList();

        // Get Driver Market        
        String driverUrl = "https://api.openf1.org/v1/drivers";
        String json = restTemplate.getForObject(driverUrl, String.class);

        List<Map<String, Object>> drivers;
        Map<String, List<SimpleDriver>> groupedDrivers;
        
        try {
        	drivers = objectMapper.readValue(json, new TypeReference<>() {});
        	
        	groupedDrivers = drivers.stream()
        	        .filter(d -> d.get("session_key") != null)
        	        .collect(Collectors.groupingBy(
        	            d -> String.valueOf(d.get("session_key")),
        	            Collectors.mapping(
        	                d -> new SimpleDriver(
        	                    String.valueOf(d.get("driver_number")),
        	                    d.getOrDefault("full_name", "Unknown").toString()
        	                ),
        	                Collectors.toList()
        	            )
        	        ));
        	
        } catch (Exception e) {
            return Collections.emptyList();
        }
        
        // Convert each session to EventResponse
        List<EventResponse> results = new ArrayList<>();
        for (Map<String, Object> session : resSessions) {
            String sessionId = String.valueOf(session.get("session_key"));
            String type = String.valueOf(session.get("session_type"));
            String sessionCountry = String.valueOf(session.get("country_name"));
            String date = String.valueOf(session.get("date_start"));
            String formattedDate = OffsetDateTime.parse(String.valueOf(session.get("date_start"))).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            List<DriverMarket> market = getRandomizedDriverMarket(sessionId, groupedDrivers);

            results.add(new EventResponse(sessionId, type, sessionCountry, formattedDate, market));
        }
        return results;
    }

    /**
     * Generates a list of drivers participating in the given session, 
     * assigning each a random betting odd (2, 3, or 4) to simulate a 
     * driver market for that event.
     * @param sessionId
     * @param groupedDrivers
     * @return List<DriverMarket>
     */
    private List<DriverMarket> getRandomizedDriverMarket(String sessionId, Map<String, List<SimpleDriver>> groupedDrivers) {
    	Random rand = new Random();
        List<Integer> oddsOptions = Arrays.asList(2, 3, 4);

        return groupedDrivers.getOrDefault(sessionId, Collections.emptyList())
                .stream()
                .map(driver -> new DriverMarket(
                        driver.getDriverId(),
                        driver.getName(),
                        oddsOptions.get(rand.nextInt(oddsOptions.size()))
                ))
                .collect(Collectors.toList());
    }
}
