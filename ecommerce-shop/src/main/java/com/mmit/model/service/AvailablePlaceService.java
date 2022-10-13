package com.mmit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.AvailablePlace;
import com.mmit.model.repo.AvailablePlaceRepo;

@Service
public class AvailablePlaceService {

	@Autowired
	private AvailablePlaceRepo repo;

	public List<String> findCityList() {
		return repo.findCityList();
	}

	public List<AvailablePlace> findPlaceList(String obj) {
		return repo.findPlaceList(obj);
	}

	public List<AvailablePlace> findAll() {
		return repo.findAll();
	}

	public AvailablePlace findById(int townshipId) {
		return repo.findById(townshipId).get();
	}

	public void save(AvailablePlace place) {
		repo.save(place);
	}
}
