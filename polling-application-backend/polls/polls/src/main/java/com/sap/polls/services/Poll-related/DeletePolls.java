package com.sap.polls;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.concurrent.ExecutionException;

public class DeletePolls {
    public String delete(String documentname, String currentuser) {
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionreference = db.collection("Polls");
        DocumentReference documentreference = collectionreference.document(documentname);

        try {
            ApiFuture<DocumentSnapshot> documentfuture = documentreference.get();
            DocumentSnapshot documentsnapshot = documentfuture.get();

            String owner = documentsnapshot.getString("creator");
            if (currentuser.equals(owner)) {
                documentreference.delete();
                return "Deleted Successfully! ";
            } else {
                return "Not Authorized! ";
            }
        } catch (InterruptedException | ExecutionException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();  // This is just a basic example, consider using a logging framework
            return "Error: " + e.getMessage();
        }
    }
}
