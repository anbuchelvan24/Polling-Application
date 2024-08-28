package com.sap.polls;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.Timestamp;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class NewPolls {

    public String createPoll(Map<String,Object> data) {

        String poll_name =(String) data.get("poll_name");
        String creator = (String)data.get("creator");
        String question = (String)data.get("question");
        List<String> options = (List<String>) data.get("options");
        boolean end = false;
        Long duration =  (Long) data.get("duration");
        String organization = (String)data.get("organization");
        String team = (String)data.get("team");

        List<String> answered = new ArrayList<>();

        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Polls");
        DocumentReference documentReference = collectionReference.document(poll_name);

        Map<String, Integer> option = new HashMap<>();
        for(String txt: options){
            option.put(txt,0);
        }

        Map<String, Object> pollData = new HashMap<>();
        pollData.put("question", question);
        pollData.put("options", option);
        pollData.put("creator", creator);
        pollData.put("timestamp", Timestamp.now());
        pollData.put("answered", answered);
        pollData.put("duration", duration);
        pollData.put("organization", organization);
        pollData.put("team", team);
        pollData.put("end", end);

        documentReference.set(pollData);

        return "Poll created! ";
    }
}
