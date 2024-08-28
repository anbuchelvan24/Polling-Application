package com.sap.polls;

import org.mindrot.jbcrypt.BCrypt;

public class SecurePwd {
    public String encrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkpw(String password, String cipher){
        return BCrypt.checkpw(password,cipher);
    }
}