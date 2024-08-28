package com.sap.polls;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import java.util.Map;
import java.util.List;

import com.google.firebase.cloud.FirestoreClient;

public class SubmitDraftPolls {

    private final Firestore db = FirebaseConfig.get_ref_fs();

    public void submitdrafts(String pollname, String destcollection, String sourcecollection, String loggeduser){

        CollectionReference draftsCollectionReference = db.collection("Drafts");
        DocumentReference userDocumentReference  = draftsCollectionReference.document(loggeduser);
        CollectionReference pollreference = userDocumentReference.collection("polls");
        DocumentReference documentreference = pollreference.document(pollname);

        try{
            DocumentSnapshot document = documentreference.get().get();

            if (document.exists()) {
                // Get data from the source document
                Map<String, Object> data = document.getData();

                // Create a new document in the destination collection
                DocumentReference destDocRef = db.collection(destcollection).document(pollname);
                destDocRef.set(data);

                // Delete the document from the source collection
                documentreference.delete();
            } else {
                System.out.println("Document does not exist in the source collection.");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}