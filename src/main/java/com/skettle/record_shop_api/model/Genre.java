package com.skettle.record_shop_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue
    long id;

    @OneToMany(mappedBy = "genre")
    Set<Album> albums;

    String name;

}
