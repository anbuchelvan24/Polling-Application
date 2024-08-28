package com.sap.polls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.cloud.FirestoreClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class PollListing {
    public List<Map<String, Object>> listPolls() {

        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Polls");

        List<Map<String, Object>> pollsList = new ArrayList<>();

        collectionReference.listDocuments().forEach(documentReference -> {
            // Use ApiFuture to retrieve the DocumentSnapshot
            ApiFuture<DocumentSnapshot> documentFuture = documentReference.get();

            try {
                // Use get() to block until the result is available
                DocumentSnapshot documentSnapshot = documentFuture.get();

                Map<String, Object> pollData = documentSnapshot.getData();


                if (pollData != null) {
                    Map<String, Object> formattedPoll = new HashMap<>();
                    formattedPoll.put("poll_name", documentReference.getId());
                    formattedPoll.put("question", pollData.get("question"));
                    formattedPoll.put("options", pollData.get("options"));
                    formattedPoll.put("creator", pollData.get("creator"));
                    com.google.cloud.Timestamp timestamp = (com.google.cloud.Timestamp) pollData.get("timestamp");
                    if (timestamp != null) {
                        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());

                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM h:mm a", Locale.ENGLISH);
                        String formattedTimestamp = localDateTime.format(formatter);

                        formattedPoll.put("timestamp", formattedTimestamp);
                    }
                    pollsList.add(formattedPoll);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return pollsList;
    }
}