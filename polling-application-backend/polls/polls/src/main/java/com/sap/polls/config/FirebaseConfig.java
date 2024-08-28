package com.sap.polls;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {


    private static Firestore firestore;
    public static synchronized void firebaseInit() {
        try {
            FileInputStream serviceAccount = new FileInputStream("firebaseconfig.json");

            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            FirebaseApp.initializeApp(options);

            firestore = FirestoreClient.getFirestore();

            System.out.println("=============================\nFirestore Initialized\n============================");

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., log it or throw a custom exception)
        }
    }

    public static Firestore get_ref_fs() {
        return firestore;
    }
}
