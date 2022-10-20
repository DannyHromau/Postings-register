package com.dannyhromau.main.dao;

import com.dannyhromau.main.dao.parser.ParserCsv;
import com.dannyhromau.main.model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DataBaseManager implements DBManager {
    private String loginsPath;
    private String postingsPath;

    public DataBaseManager(String loginsPath, String postingsPath) {
        this.loginsPath = loginsPath;
        this.postingsPath = postingsPath;
    }


    public static Session getSession() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory.openSession();
    }

    public boolean insertIntoDB() {
        boolean isSaved = true;
        ParserCsv parserCsv = new ParserCsv();
        List<String> loginsList = parserCsv.getParsedLines(loginsPath);
        List<String> postingsList = parserCsv.getParsedLines(postingsPath);
        List<Login> logins = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Good> goods = new ArrayList<>();
        List<Posting> postings = new ArrayList<>();
        List<GoodToPosting> goodToPostingList = new ArrayList<>();
        for (String loginLine : loginsList) {
            String[] tokens = loginLine.split("\\.");
            Login login = new Login(
                    tokens[0].replaceAll("\\.", ""),
                    tokens[1].replaceAll("\\.", ""),
                    tokens[2].replaceAll("\\.", ""),
                    tokens[3].replaceAll("\\.", ""),
                    tokens[4].replaceAll("\\.", ""));
            logins.add(login);
        }
        List<String> uniqueGoodNames = new ArrayList<>();
        List<String> uniqueUserNames = new ArrayList<>();
        List<String> uniquePostings = new ArrayList<>();
        List<String> userNameList = new ArrayList<>();
        List<List<String>> keys = new ArrayList<>();
        List<List<String>> postUsersKeys = new ArrayList<>();
        for (String postingLine : postingsList) {
            String[] elements = postingLine.split("\t");
            List<String> embeddedKey = new ArrayList<>();
            embeddedKey.add(elements[0]);
            embeddedKey.add(elements[4]);
            List<String> postAndUser = new ArrayList<>();
            postAndUser.add(elements[0]);
            postAndUser.add(elements[9]);
            keys.add(embeddedKey);
            postUsersKeys.add(postAndUser);
            String uniquePosting = elements[0] + "\t" + elements[2] + "\t" + elements[3];
            if (!uniquePostings.contains(uniquePosting)) {
                uniquePostings.add(uniquePosting);
            }
            userNameList.add(elements[9]);
            if (!uniqueUserNames.contains(elements[9])) {
                uniqueUserNames.add(elements[9]);
            }
            if (!uniqueGoodNames.contains(elements[4])) {
                uniqueGoodNames.add(elements[4]);
            }
            GoodToPosting goodToPosting =
                    new GoodToPosting(0, 0,
                            new GoodToPostingKey(0, 0, Integer.parseInt(elements[1])));
            goodToPosting.setCount(Integer.parseInt(elements[5]));
            goodToPosting.setItem(Integer.parseInt(elements[1]));
            goodToPosting.setAmount(Double.parseDouble(elements[7]));
            goodToPosting.setBun(elements[6]);
            goodToPosting.setCurrency(elements[8]);
            goodToPostingList.add(goodToPosting);
        }
        for (Login login : logins) {
            loginsList.add(login.getAccountName());
        }
        for (String uniqueUser : uniqueUserNames) {
            User user = new User(uniqueUser);
            users.add(user);
        }
        for (String uniqueName : uniqueGoodNames) {
            Good good = new Good(uniqueName);
            goods.add(good);
        }

        for (String postingLines : uniquePostings) {
            String[] parts = postingLines.split("\t");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDateDoc = LocalDate.from(formatter.parse(parts[1]));
            LocalDate localDatePost = LocalDate.from(formatter.parse(parts[2]));
            Posting posting = new Posting(parts[0], localDateDoc, localDatePost);
            postings.add(posting);
        }


        for (int i = 0; i < goodToPostingList.size(); i++) {
            for (Good good : goods) {
                if (good.getDescription().equals(keys.get(i).get(1))) {
                    goodToPostingList.get(i).setGood(good);

                }
            }
            for (Posting posting : postings) {
                if (Objects.equals(posting.getPostingCode(), keys.get(i).get(0))) {
                    goodToPostingList.get(i).setPosting(posting);

                }
            }
        }

        for (int i = 0; i < postUsersKeys.size(); i++) {
            for (User user : users) {
                if (user.getName().equals(postUsersKeys.get(i).get(1))) {
                    goodToPostingList.get(i).getPosting().setUser(user);
                }
            }
        }

        for (Posting posting : postings) {
            if (userNameList.contains(posting.getUser().getName())) {
                posting.setIsActive(1);
            }
            posting.setIsActive(0);
        }
        try {
            Session session = getSession();
            Transaction transaction = session.beginTransaction();
            logins.forEach(session::save);
            users.forEach(session::save);
            goods.forEach(session::save);
            postings.forEach(session::save);

            for (int i = 0; i < goodToPostingList.size(); i++) {
                goodToPostingList.get(i).setPostingId(goodToPostingList.get(i).getPosting().getId());
                goodToPostingList.get(i).setGoodId(goodToPostingList.get(i).getGood().getId());
                goodToPostingList.get(i).getGoodToPostingKey().setPostingId(goodToPostingList.get(i).getPosting().getId());
                goodToPostingList.get(i).getGoodToPostingKey().setGoodId(goodToPostingList.get(i).getGood().getId());
            }
            for (GoodToPosting goodToPosting : goodToPostingList) {
                session.save(goodToPosting);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            isSaved = false;
        }
        finally {
            return isSaved;
        }
    }

}
