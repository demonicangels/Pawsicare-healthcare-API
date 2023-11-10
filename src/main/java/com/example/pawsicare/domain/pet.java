package com.example.pawsicare.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class pet {

    private long id;
    private long ownerId;
    private String name;
    private String birthday;
    private Integer age;
    private String information;
}