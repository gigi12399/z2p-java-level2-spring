package com.mmit.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.AvailablePlace;

public interface AvailablePlaceRepo extends JpaRepository<AvailablePlace, Integer> {
	@Query("SELECT ap.city FROM AvailablePlace ap GROUP BY ap.city")
	List<String> findCityList();
	
	@Query("SELECT a FROM AvailablePlace a WHERE a.city = :input")
	List<AvailablePlace> findPlaceList(@Param("input") String city);
}
