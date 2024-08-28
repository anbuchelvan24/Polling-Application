package com.sap.polls;

import java.util.Map;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

public class AlreadyAnswered {

    private final Firestore db = FirebaseConfig.get_ref_fs();

    public boolean already(String participant, String pollname){

        DocumentReference documentreference = db.collection("Polls").document(pollname);

        try{
            //For the "answered" field
            DocumentSnapshot documentsnapshot = documentreference.get().get();
            if (documentsnapshot != null && documentsnapshot.contains("answered")) {
                // Check if participant is already in the "answered" array
                if (((List<String>) documentsnapshot.get("answered")).contains(participant)) {
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}