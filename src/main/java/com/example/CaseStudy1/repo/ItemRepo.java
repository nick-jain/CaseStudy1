package com.example.CaseStudy1.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CaseStudy1.entity.Item;


public interface ItemRepo extends JpaRepository<Item,Long>{
	

}
