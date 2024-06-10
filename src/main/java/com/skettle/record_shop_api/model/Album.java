package com.skettle.record_shop_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "albums")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    long id;

    String artist;

    Genre genre;

    String name;

    @Column(name = "release_year")
    int releaseYear;

    @Column(name = "stock_quantity")
    int stockQuantity;

}
