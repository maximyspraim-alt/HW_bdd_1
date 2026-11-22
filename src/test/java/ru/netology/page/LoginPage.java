package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement codeInput = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public void login(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public void enterVerificationCode(String code) {
        codeInput.shouldBe(Condition.visible, Duration.ofSeconds(10)).setValue(code);
        verifyButton.click();
    }
}