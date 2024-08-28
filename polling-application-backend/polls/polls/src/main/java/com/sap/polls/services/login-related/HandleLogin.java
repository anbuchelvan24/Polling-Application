package com.sap.polls;

import java.util.Map;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;


public class HandleLogin {
    public boolean handleCredentials(Map<String, String> data) {
        Firestore db = FirebaseConfig.get_ref_fs();
        CollectionReference collectionReference = db.collection("Users");

        String currentuser = data.get("email");

        DocumentReference documentReference = collectionReference.document(currentuser);
        ApiFuture<DocumentSnapshot> documentfuture = documentReference.get();

        boolean auth;

        try{

            DocumentSnapshot documentSnapshot = documentfuture.get();

            LoginAuth loginAuth = new LoginAuth();
            auth = loginAuth.login(data);

            if (auth) {
                if(documentSnapshot.exists()) {

                    String organization = documentSnapshot.getString("organization");
                    String team = documentSnapshot.getString("team");

                    GlobalVariables.credentials = LoginDetails.credentials(currentuser, organization, team);
                }

            }

            return auth;
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception message: " + e.getMessage());
            return false;
        }
    }

    public List<String> getCredentials(){
        return GlobalVariables.credentials;
    }

}
