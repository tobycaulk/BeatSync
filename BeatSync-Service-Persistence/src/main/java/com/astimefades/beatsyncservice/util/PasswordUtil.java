package com.astimefades.beatsyncservice.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}