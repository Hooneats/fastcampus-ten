package org.example;

/**
 * 항상 참인 패스워드
 */
public class WrongFixedPasswordGenerator implements PasswordGeneratePolicy{
    @Override
    public String generatePassword() {
        return "ab";
    }
}
