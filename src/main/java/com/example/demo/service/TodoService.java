package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j //로깅 라이브러리
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService(){
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEntity 저장
        repository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity){
        if(entity==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId()==null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }

        repository.save(entity); // 엔터티 저장

        log.info("Entity Id : {} is saved", entity.getId()); // entity.getId()가 {}에 들어감

        return repository.findByUserId(entity.getUserId()); // 이 사용자가 저장한 투두아이템 다 가져와라
    }
}
