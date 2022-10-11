package org.example;

/**
 * 항상 참인 패스워드
 */
public class CorrectFixedPasswordGenerator implements PasswordGeneratePolicy{
    @Override
    public String generatePassword() {
        return "abcdefgh";
    }
}
