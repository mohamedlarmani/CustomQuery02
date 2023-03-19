package com.develhope.CustomQuery02.Controller;

import com.develhope.CustomQuery02.Entities.Flight;
import com.develhope.CustomQuery02.Enums.StatusEnum;
import com.develhope.CustomQuery02.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



@RestController
@RequestMapping("/flights")
public class FlightController {

    private static final int NUM_FLIGHTS = 50;

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/getById")
    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepository.findAll();
    }

    @PostMapping("/provision")
    public List<Flight> provisionFlights(@RequestParam(defaultValue = "100") int n) {
        Random random = new Random();
        List<Flight> flights = new ArrayList<>();
        for (Long i = Long.valueOf(0); i < n; i++) {
            Flight flight = new Flight();
            flight.setId(i);
            flight.setFromAirport("Airport " + random.nextInt(10));
            flight.setToAirport("Airport " + random.nextInt(10));
            flight.setStatusEnum(StatusEnum.values()[random.nextInt(StatusEnum.values().length)]);
            flights.add(flight);
        }
        return (List<Flight>) flightRepository.saveAll(flights);
    }

    @GetMapping("/getall")
    public List<Flight> getAllFlights(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                      @RequestParam(name = "sort", defaultValue = "fromAirport,asc") String[] sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
        return flightRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/ontime")
    public List<Flight> getOnTimeFlights() {
        return flightRepository.findByStatus(StatusEnum.ONTIME);
    }

    @GetMapping("/status")
    public List<Flight> getFlightsByStatus(@RequestParam StatusEnum p1, @RequestParam StatusEnum p2) {
        return flightRepository.findByStatus(p1, p2);
    }
}