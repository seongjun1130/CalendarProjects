package com.sparta.calendarprojects.repository;

import com.sparta.calendarprojects.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

//Entity 를 Id 기준 내림차순으로 데이터를 받기위한 CrudRepository 요청 메소드
public interface EventPageRepository extends CrudRepository<Event, Long> {
    Page<Event> findAllByOrderByIdDesc(Pageable pageable);
}
