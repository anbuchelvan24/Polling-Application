package com.sap.polls;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

public class AnsweredPolls {
    public void answered(){
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionreference = db.collection("polls");
        DocumentReference documentreference = collectionreference.document();
        ApiFuture<DocumentSnapshot> documentfuture = documentreference.get();
    }
}