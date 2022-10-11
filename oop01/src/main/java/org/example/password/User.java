package org.example.password;

public class User {
    private String password;

    public void initPassword(PasswordGeneratePolicy passwordGeneratePolicy) {
        // 겹합도를 낮추기 위해 인터페이스 활용해 주입받는다.
        // RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String randomPassword = passwordGeneratePolicy.generatePassword();

        /**
         * 비밀번호 최소 8자 이상 12자 이하
         */
        if (randomPassword.length() >= 8 && randomPassword.length() <= 12) {
            this.password = randomPassword;
        }
    }

    public String getPassword() {
        return this.password;
    }
}
