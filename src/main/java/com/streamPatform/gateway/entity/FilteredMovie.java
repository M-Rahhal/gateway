package com.streamPatform.gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "filtered_movies")
public class FilteredMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "movie_path", nullable = false)
    private String moviePath;

}
