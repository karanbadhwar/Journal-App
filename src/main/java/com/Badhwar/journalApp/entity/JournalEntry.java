package com.Badhwar.journalApp.entity;

//POJO File
//Lombok -> creates getters, setters and all the BoilerPlate code at compilation based on annotations!!

import com.Badhwar.journalApp.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//MongoDB Annotation, to make the Table/Collection with this name
@Document(collection = "journal_entries")
//Lombok Annotations below
@Data //Note - It does not set @NoArgsConstructor!!!
//Use Data Annotation or all the below, as @Data is equivalent to all the Annotations below.
//@Getter
//@Setter
//
////Have a lot of Annotations
//@AllArgsConstructor
//@EqualsAndHashCode
//@Builder
@NoArgsConstructor //Required to deserialize the JSON to Java Object, as Spring calls the NoArgsConstructor!!
/*
The @NoArgsConstructor annotation was likely necessary because of the deserialization process happening in your createEntry method in the controller. When Spring MVC receives a POST request with a JournalEntry JSON payload, it uses a deserialization process (e.g., with Jackson) to convert that JSON into a JournalEntry object. During this process,
a no-argument constructor is needed to create an instance of JournalEntry before setting its fields.
Without @NoArgsConstructor, the deserialization process would fail, causing an error when trying to map the JSON data to the JournalEntry object.
 */
public class JournalEntry {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    private Sentiment sentiment;

//    //Date
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//    //-------------------------------
//
//    //ID
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//    //---------------------------------
//
//    //Title
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
////-------------------------------------------
//
//    //Content
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//    //------------------------------------------
}
