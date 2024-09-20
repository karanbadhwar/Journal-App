package com.Badhwar.journalApp.repository;

import com.Badhwar.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRespositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserforSA()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("sentimentsAnalysis").is(true));

        //OR Statement
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(//First Query, //SecondQuery));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;

    }

}
