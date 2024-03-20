package com.example.demo;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor // 생성자가 자동으로 만들어짐
public class DemoModel {
    @NonNull // 밑에 id가 nonnull이도록
    private String id;
}
