package com.sap.polls;

import java.util.List;

public class LoginDetails {
    public static List<String> credentials(String currentuser, String organization, String team) {
        return List.of(currentuser, organization, team);
    }
}