package com.sap.polls;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentSnapshot;

public class DeleteDrafts {
    public String delete(String documentname, String currentuser) {
        Firestore db = FirebaseConfig.get_ref_fs();

        CollectionReference draftsCollectionReference = db.collection("Drafts");
        DocumentReference userDocumentReference  = draftsCollectionReference.document(currentuser);
        CollectionReference pollreference = userDocumentReference.collection("polls");
        DocumentReference documentreference = pollreference.document(documentname);

        try {
            DocumentSnapshot documentsnapshot = documentreference.get().get();

            String owner = documentsnapshot.getString("creator");
            if (currentuser.equals(owner)) {
                documentreference.delete();
                return "Deleted Successfully! ";
            } else {
                return "Not Authorized! ";
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();  // This is just a basic example, consider using a logging framework
            return "Error: " + e.getMessage();
        }
    }
}