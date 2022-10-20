package com.dannyhromau.main.model.repository;

import com.dannyhromau.main.model.entities.GoodToPosting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostingRepository extends CrudRepository<GoodToPosting, Integer> {

    default List<GoodToPosting> getGoodsToPostings() {
        Iterable<GoodToPosting> postingIterable = findAll();
        List<GoodToPosting> postings = new ArrayList<>();
        for (GoodToPosting posting : postingIterable) {
            postings.add(posting);
        }
        return postings;
    }

    default LocalDate convertToDate(String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.from(formatter.parse(localDate));
    }


}
