package com.sap.polls;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentSnapshot;
import java.util.HashMap;
import java.util.Map;

public class Endpolls {
    public void end_poll(String pollname){
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Polls");
        DocumentReference documentReference = collectionReference.document(pollname);
        Map<String, Object> updates = new HashMap<>();
        updates.put("end", true);
        documentReference.update(updates);
    }
    public boolean returnEndVal(String pollName){
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Polls");
        DocumentReference documentReference = collectionReference.document(pollName);
        ApiFuture<DocumentSnapshot> documentfuture = documentReference.get(); // Blocking call, handle exceptions

        try{
            DocumentSnapshot documentSnapshot = documentfuture.get();
            if (documentSnapshot.exists()) {
                Boolean end = documentSnapshot.getBoolean("end");
                return end != null && end; // Return true if end is true, false otherwise
            } else {
                // Document doesn't exist, handle accordingly
                return false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
