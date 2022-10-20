package com.dannyhromau.main.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "goods")
@NoArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "material_description")
    private String description;
    @ManyToMany
    @JoinTable(name = "goods2postings", joinColumns = {@JoinColumn(name = "good_id")}, inverseJoinColumns = {@JoinColumn(name = "posting_id")})
    private List<Posting> postings;

    public Good(String description) {
        this.description = description;
    }


}
