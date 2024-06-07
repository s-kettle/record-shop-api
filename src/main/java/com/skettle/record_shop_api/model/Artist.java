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
public class Artist {

    @Id
    @GeneratedValue
    long id;

    String name;

    @OneToMany(mappedBy = "artist")
    Set<Album> albums;

}
