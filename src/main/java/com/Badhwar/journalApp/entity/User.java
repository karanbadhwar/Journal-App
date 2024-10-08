package com.Badhwar.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull // Lombok's annotation, as while setting the Field, Lombok's processor will check if it is empty or not
    private String userName;

    private String email;
    private boolean sentimentAnalysis;

    @NonNull
    private String password;

    @DBRef // Ref Created to the Connected Collection/Table
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
}
