package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        //if(entity==null){
        //    log.warn("Entity cannot be null");
        //    throw new RuntimeException("Entity cannot be null");
        //}
        //if(entity.getUserId()==null){
        //    log.warn("Unknown user");
        //    throw new RuntimeException("Unknown user");
        //}
        validate(entity);

        repository.save(entity); // 엔터티 저장

        log.info("Entity Id : {} is saved", entity.getId()); // entity.getId()가 {}에 들어감

        return repository.findByUserId(entity.getUserId()); // 이 사용자가 저장한 투두아이템 다 가져와라
    }

    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        // 저장할 엔터티 유효한지 확인
        validate(entity);

        // 넘겨 받은 엔터티 id를 이용해 Todo엔터티 가져옴, 존재하지 않는 엔터티는 업데이트 불가하니까
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        // 객체가 있으면~
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        // 유저의 모든 Todo리스트를 리턴
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);

        try{
            repository.delete(entity);
        }catch(Exception e){
            log.error("error deleting entity ", entity.getId(), e);

            throw  new RuntimeException("error deleting entity "+ entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    public void validate(TodoEntity entity){
        if(entity==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId()==null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
