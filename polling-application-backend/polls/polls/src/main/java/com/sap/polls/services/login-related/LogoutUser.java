package com.sap.polls;

import java.util.List;
import java.util.ArrayList;

public class LogoutUser {
    public void logout() {
        if (GlobalVariables.credentials != null) {
            List<String> newcredentials = new ArrayList<>(List.of("","",""));
            GlobalVariables.credentials = newcredentials;
        }
    }
}
