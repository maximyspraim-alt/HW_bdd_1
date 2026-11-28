package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public void transfer(String fromCardNumber, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromInput.setValue(fromCardNumber);
        transferButton.click();
    }

    public void waitForError() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}