package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo") //테이블 이름을 지정, 지정 안하면 entity이름이 그대로 감
public class TodoEntity { //TodoEntity가 테이블 하나라고 th
    @Id // PRIMARY KEY를 ID로 하려면 이렇게 붙임
    @GeneratedValue(generator = "system-uuid") // id는 자동 생성되게
    @GenericGenerator(name = "system-uuid", strategy = "uuid") // id는 자동 생성되게
    private String id;
    private String userId;
    private String title;
    private boolean done;

}
