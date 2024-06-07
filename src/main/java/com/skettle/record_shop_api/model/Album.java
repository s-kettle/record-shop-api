package com.skettle.record_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    Genre genre;

    String name;

    @Column(name = "release_year")
    int releaseYear;

    @Column(name = "stock_quantity")
    int stockQuantity;

}
