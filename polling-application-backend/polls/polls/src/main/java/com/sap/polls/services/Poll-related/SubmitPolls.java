package com.sap.polls;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import java.util.Map;
import java.util.List;

import com.google.firebase.cloud.FirestoreClient;

public class SubmitPolls {

    private final Firestore db = FirebaseConfig.get_ref_fs();

    public void submit(String pollname, String participant, int answer){
        DocumentReference documentreference = db.collection("Polls").document(pollname);

        try{
            //For the "answered" field
            DocumentSnapshot documentsnapshot = documentreference.get().get();
            if (documentsnapshot != null && documentsnapshot.contains("answered")) {
                // Check if participant is already in the "answered" array
                if (!((List<String>) documentsnapshot.get("answered")).contains(participant)) {
                    documentreference.update("answered", FieldValue.arrayUnion(participant));
                }
            }

            //For the count
            Object optionsObject = documentsnapshot.get("options");
            Map<String, Integer> optionsMap = (Map<String, Integer>) optionsObject;
            if(answer>=0 && answer<optionsMap.size())
            {
                String keyToUpdate = optionsMap.keySet().toArray(new String[0])[answer];
                documentreference.update("options."+ keyToUpdate, FieldValue.increment(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}