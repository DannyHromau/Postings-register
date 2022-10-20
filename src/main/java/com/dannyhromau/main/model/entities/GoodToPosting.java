package com.dannyhromau.main.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "goods2Postings")
@NoArgsConstructor
public class GoodToPosting {


    @EmbeddedId
    private GoodToPostingKey goodToPostingKey;
    @Column(name = "posting_id", insertable = false, updatable = false)
    private int postingId;
    @Column(name = "good_id", insertable = false, updatable = false)
    private int goodId;
    @Column(insertable = false, updatable = false)
    private int item;
    @ManyToOne
    @JoinColumn(name = "posting_id", insertable = false, updatable = false)
    private Posting posting;
    @ManyToOne
    @JoinColumn(name = "good_id", insertable = false, updatable = false)
    private Good good;
    private int count;
    private double amount;
    private String currency;
    private String bun;

    public GoodToPosting(int postingId, int goodId, GoodToPostingKey goodToPostingKey) {
        this.goodToPostingKey = goodToPostingKey;
        this.postingId = postingId;
        this.goodId = goodId;
    }


}
