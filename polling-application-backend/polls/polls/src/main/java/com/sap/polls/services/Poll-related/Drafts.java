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

public class Drafts {
    public String draftpolls(Map<String,Object> data){

        String poll_name =(String) data.get("poll_name");
        String creator = (String)data.get("creator");
        String question = (String)data.get("question");
        List<String> options = (List<String>) data.get("options");
        Long duration =  (Long) data.get("duration");
        String organization = (String)data.get("organization");
        boolean end = false;
        String team = (String)data.get("team");

        List<String> answered = new ArrayList<>();

        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference draftsCollectionReference = db.collection("Drafts");
        DocumentReference userDocumentReference  = draftsCollectionReference.document(creator);
        CollectionReference documentReference = userDocumentReference.collection("polls");

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

        userDocumentReference.collection("polls").document(poll_name).set(pollData);

        return "Draft created! ";
    }
}