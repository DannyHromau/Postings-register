package com.dannyhromau.main.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity

@Table(name = "postings")
@NoArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "mat_doc")
    private String postingCode;
    @Column(name = "doc_date")
    private LocalDate docDate;
    @Column(name = "posting_date")
    private LocalDate postingDate;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @Column(name = "is_active")
    private int isActive;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "goods2postings", joinColumns = {@JoinColumn(name = "posting_id")}, inverseJoinColumns = {@JoinColumn(name = "good_id")})
    private List<Good> goods;

    public Posting(String postingCode, LocalDate docDate, LocalDate postingDate) {
        this.postingCode = postingCode;
        this.docDate = docDate;
        this.postingDate = postingDate;
    }
}
