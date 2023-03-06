package com.school.myschool.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Table(name = "holidays")
@NoArgsConstructor
public class Holiday {
    @Id
    private String day;
    private String reason;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type{
        FESTIVAL,FEDERAL
    }
}
