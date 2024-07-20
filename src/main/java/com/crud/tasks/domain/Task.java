package com.crud.tasks.domain;

import lombok.*;

@Getter
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String content;

}
