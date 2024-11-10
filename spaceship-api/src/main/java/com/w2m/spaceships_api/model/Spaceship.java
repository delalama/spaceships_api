package com.w2m.spaceships_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.w2m.spaceships_api.utils.JsonUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

import static com.w2m.spaceships_api.utils.Constants.SPACESHIP_DATE_FORMAT;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Spaceship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String model;

    @JsonFormat(pattern = SPACESHIP_DATE_FORMAT)
    private LocalDate creationDate;

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}
