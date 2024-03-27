package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    //testTodo 메서드 작성하기

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = service.testService(); //테스트 서비스 사용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user";
            // 1. TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            // 2.id를 null로 초기화
            entity.setId(null);
            // 3. 임시 유저 아이디 설정
            entity.setUserId(temporaryUserId);

            // 4. 서비스 이용해 TodoEntity 생성
            List<TodoEntity> entities = service.create(entity);
            // 5. 자바 스트림 이용해서 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 7. ResponseDTO 리턴
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            // 8. 예외가 나는 경우에는 dto 대신 error를 return
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temporary-user";
        // 1. TodoEntity 로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);
        // 2. 임시 유저 아이디 설정
        entity.setUserId(temporaryUserId);

        // 3. 서비스 이용해 TodoEntity 생성
        List<TodoEntity> entities = service.update(entity);
        // 4. 자바 스트림 이용해서 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 6. ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user";
            // 1. TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            // 2. 임시 유저 아이디 설정
            entity.setUserId(temporaryUserId);

            // 3. 서비스 이용해 TodoEntity 삭제
            List<TodoEntity> entities = service.delete(entity);
            // 4. 자바 스트림 이용해서 리턴된 엔티티 리스트를 ToDoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 6. ResponseDTO 리턴
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            // 7. 예외가 나는 경우에는 dto 대신 error를 return
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
