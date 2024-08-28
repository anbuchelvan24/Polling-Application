package com.sap.polls;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.Map;

public class Register {

    public String register(Map<String, String> data) {
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Users");

        String email = data.get("email");
        String password = data.get("password");
        String organization = data.get("organization");
        String team = data.get("team");
        DocumentReference documentReference = collectionReference.document(email);
        ApiFuture<DocumentSnapshot> documentFuture = documentReference.get();

        try {
            DocumentSnapshot documentsnapshot = documentFuture.get();
            if(!documentsnapshot.exists())
            {
                SecurePwd securepwd = new SecurePwd();
                String password_hash = securepwd.encrypt(password);

                Map<String, Object> userData = Map.of(
                        "password", password,
                        "password_hash", password_hash,
                        "organization", organization,
                        "team", team
                );

                documentReference.set(userData);

                return "You have signed up successfully! ";
            }
            return "Email ID already exists!";
        }
        catch(Exception e){
            e.printStackTrace();
            return "Unexpected error found! ";
        }
    }
}
