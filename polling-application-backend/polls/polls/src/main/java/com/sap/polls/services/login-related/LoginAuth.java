package com.sap.polls;

import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;

public class LoginAuth {

    public boolean login(Map<String, String> data){
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Users");

        String email = data.get("email");
        String password = data.get("password");

        DocumentReference documentReference = collectionReference.document(email);
        ApiFuture<DocumentSnapshot> documentfuture = documentReference.get();

        try{
            DocumentSnapshot documentsnapshot = documentfuture.get();

            if(documentsnapshot.exists()){
                String passwordhashed = documentsnapshot.getString("password_hash");

                SecurePwd securepwd = new SecurePwd();
                return securepwd.checkpw(password, passwordhashed);
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
}