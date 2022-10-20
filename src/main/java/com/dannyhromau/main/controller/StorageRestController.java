package com.dannyhromau.main.controller;

import com.dannyhromau.main.model.entities.GoodToPosting;
import com.dannyhromau.main.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StorageRestController {
    @Autowired
    private PostingService dbWorker;
    @Value("${loginsPath}")
    private String loginsPath;
    @Value("${postingsPath}")
    private String postingsPath;

    @GetMapping("/save")
    public boolean saveDatum() {
        boolean isSaved = dbWorker.getDbWork(loginsPath, postingsPath);
        return isSaved;
    }

    @GetMapping("/postings")
    public List<GoodToPosting> getPostings(@RequestParam boolean isActive) {
        return dbWorker.getPostingsFromDB(isActive);
    }


    @GetMapping("/postings/day")
    public List<GoodToPosting> getDayPostings(@RequestParam String localDate, boolean isActive) {
            return dbWorker.getDayPostings(localDate, isActive);
    }

    @GetMapping("/postings/month")
    public List<GoodToPosting> getMonthPostings(@RequestParam String localDateFinish, boolean isActive) {
            return dbWorker.getMonthPostings(localDateFinish, isActive);
    }

    @GetMapping("/postings/quart")
    public List<GoodToPosting> getQuartPostings(@RequestParam String localDateFinish, boolean isActive) {
            return dbWorker.getQuartPostings(localDateFinish, isActive);
    }
    @GetMapping("/postings/year")
    public List<GoodToPosting> getYearPostings(@RequestParam String localDateFinish, boolean isActive) {
            return dbWorker.getMonthPostings(localDateFinish, isActive);
    }

    @GetMapping("/clear")
    public boolean clearALLDatum() {
        dbWorker.clearDB();
        return true;
    }

}
