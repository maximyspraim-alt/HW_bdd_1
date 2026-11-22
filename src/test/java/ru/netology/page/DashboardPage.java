package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    public SelenideElement getCardByNumber(String cardNumber) {
        // Ищем карточку, в тексте которой есть последние 4 цифры
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        return $$("li.list__item div")
                .findBy(Condition.text("**** **** **** " + last4));
    }

    public int getBalance(String cardNumber) {
        String text = getCardByNumber(cardNumber).getText()
                .replace('\u00A0', ' '); // неразрывные пробелы → обычные
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("баланс:\\s*(-?\\d+)")
                .matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalStateException("Не удалось извлечь баланс из текста: [" + text + "]");
    }

    public void clickDeposit(String cardNumber) {
        getCardByNumber(cardNumber).$("button[data-test-id='action-deposit']").click();
    }

    public void waitForDashboard() {
        $("[data-test-id='dashboard']").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}