package com.develhope.CustomQuery02.Repository;

import com.develhope.CustomQuery02.Entities.Flight;
import com.develhope.CustomQuery02.Enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {


    List<Flight> findByStatus(StatusEnum status);
    List<Flight> findByStatus(StatusEnum p1, StatusEnum p2);
}
