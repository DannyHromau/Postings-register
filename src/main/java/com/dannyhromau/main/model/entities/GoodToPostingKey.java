package com.dannyhromau.main.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class GoodToPostingKey implements Serializable {
    @Column(name = "posting_id")
    private int postingId;
    @Column(name = "good_id")
    private int goodId;
    @Column(name = "item")
    private int item;

    public GoodToPostingKey(int postingId, int goodId, int item) {
        this.postingId = postingId;
        this.goodId = goodId;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodToPostingKey that = (GoodToPostingKey) o;
        return postingId == that.postingId && goodId == that.goodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postingId, goodId);
    }
}
