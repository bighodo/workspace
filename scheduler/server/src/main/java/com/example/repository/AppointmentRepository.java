package com.example.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.AppointmentDto;

public interface AppointmentRepository extends JpaRepository<AppointmentDto, String>, JpaSpecificationExecutor<AppointmentDto>{
	@Query(value="SELECT a.* FROM APPOINTMENT AS a WHERE a.USER=:user", nativeQuery =true)
	List<AppointmentDto> findAllByUserId(@Param("user")String user);
	
	@Query(value="SELECT a.* FROM APPOINTMENT AS a WHERE a.USER=:user AND a.START_DATE>=:start AND a.END_DATE<=:end", nativeQuery =true)
	List<AppointmentDto> findAllByUserIdWithRange(@Param("user")String user, @Param("start")Date start, @Param("end")Date end);
}
