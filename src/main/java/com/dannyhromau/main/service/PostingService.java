package com.dannyhromau.main.service;

import com.dannyhromau.main.model.entities.GoodToPosting;
import com.dannyhromau.main.model.repository.PostingRepository;
import com.dannyhromau.main.dao.DataBaseManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostingService {
    @Autowired
    private PostingRepository postingRepository;

    @Transactional
    public boolean getDbWork(String loginsPath, String postingsPath) {
        DataBaseManager dataBaseService = new DataBaseManager(loginsPath, postingsPath);
        boolean isSaved = dataBaseService.insertIntoDB();
        return isSaved;
    }

    public List<GoodToPosting> getPostingsFromDB(boolean isActive) {
        List<GoodToPosting> goodToPostings = postingRepository.getGoodsToPostings();
        List<GoodToPosting> activeGoodToPostings = new ArrayList<>();
        for (GoodToPosting goodToPosting : goodToPostings) {
            if (goodToPosting.getPosting().getIsActive() == 1) {
                activeGoodToPostings.add(goodToPosting);
            }
        }
        if (isActive) {
            return activeGoodToPostings;
        }
        return goodToPostings;

    }

    public List<GoodToPosting> getDayPostings(String localDate, boolean isActive) {
        LocalDate localDatePost = postingRepository.convertToDate(localDate);
        List<GoodToPosting> dayPostings = new ArrayList<>();
        List<GoodToPosting> activeDayPostings = new ArrayList<>();
        for (GoodToPosting goodToPosting : postingRepository.getGoodsToPostings()) {
            if (goodToPosting.getPosting().getPostingDate().equals(localDatePost) && goodToPosting.getPosting().getIsActive() == 0) {
                dayPostings.add(goodToPosting);
            }
            if (goodToPosting.getPosting().getPostingDate().equals(localDatePost) && goodToPosting.getPosting().getIsActive() == 1) {
                activeDayPostings.add(goodToPosting);
            }
        }
        if (isActive) {
            return activeDayPostings;
        }
        return dayPostings;
    }

    public List<GoodToPosting> getMonthPostings(String localDateFinish, boolean isActive) {
        LocalDate localDatePost = postingRepository.convertToDate(localDateFinish);
        LocalDate localDateStart = localDatePost.minusMonths(1);
        List<GoodToPosting> periodPostings = new ArrayList<>();
        List<GoodToPosting> activePeriodPosting = new ArrayList<>();
        for (GoodToPosting goodToPosting : postingRepository.getGoodsToPostings()) {
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 0) {
                periodPostings.add(goodToPosting);
            }
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 1) {
                activePeriodPosting.add(goodToPosting);
            }
        }
        if (isActive) {
            return activePeriodPosting;
        }
        return periodPostings;
    }

    public List<GoodToPosting> getQuartPostings(String localDateFinish, boolean isActive) {
        LocalDate localDatePost = postingRepository.convertToDate(localDateFinish);
        LocalDate localDateStart = localDatePost.minusMonths(3);
        List<GoodToPosting> periodPostings = new ArrayList<>();
        List<GoodToPosting> activePeriodPostings = new ArrayList<>();
        for (GoodToPosting goodToPosting : postingRepository.getGoodsToPostings()) {
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 0) {
                periodPostings.add(goodToPosting);
            }
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 1) {
                activePeriodPostings.add(goodToPosting);
            }
        }

        if (isActive) {
            return activePeriodPostings;
        }
        return periodPostings;
    }

    public List<GoodToPosting> getYearPostings(String localDateFinish, int isActive) {
        LocalDate localDatePost = postingRepository.convertToDate(localDateFinish);
        LocalDate localDateStart = localDatePost.minusYears(1);
        List<GoodToPosting> periodPostings = new ArrayList<>();
        List<GoodToPosting> activePeriodPostings = new ArrayList<>();
        for (GoodToPosting goodToPosting : postingRepository.getGoodsToPostings()) {
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 0) {
                periodPostings.add(goodToPosting);
            }
            if (goodToPosting.getPosting().getPostingDate().isAfter(localDateStart)
                    && goodToPosting.getPosting().getPostingDate().isBefore(localDatePost)
                    && goodToPosting.getPosting().getIsActive() == 1) {
                activePeriodPostings.add(goodToPosting);
            }

        }
        if (isActive == 1) {
            return activePeriodPostings;
        }
        return periodPostings;
    }

    public void clearDB() {
        Session session = DataBaseManager.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from com.dannyhromau.main.model.entities.Posting").executeUpdate();
        session.createQuery("delete from com.dannyhromau.main.model.entities.Good").executeUpdate();
        session.createQuery("delete from com.dannyhromau.main.model.entities.GoodToPosting").executeUpdate();
        session.createQuery("delete from com.dannyhromau.main.model.entities.User").executeUpdate();
        session.createQuery("delete from com.dannyhromau.main.model.entities.Login").executeUpdate();
        transaction.commit();
    }


}
