package com.example.MvRepTile3.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AuthorisationRepository extends JpaRepository<Authorisation, Long>{

}