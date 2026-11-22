package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferTest {

    private static final String CARD_1 = "5559 0000 0000 0001";
    private static final String CARD_2 = "5559 0000 0000 0002";

    private DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        loginPage.login("vasya", "qwerty123");
        loginPage.enterVerificationCode("12345");
        dashboardPage = new DashboardPage();
        dashboardPage.waitForDashboard();
    }

    @Test
    void shouldTransferBetweenCards() {
        int balance1Before = dashboardPage.getBalance(CARD_1);
        int balance2Before = dashboardPage.getBalance(CARD_2);

        dashboardPage.clickDeposit(CARD_1);
        TransferPage transferPage = new TransferPage();
        transferPage.transfer(CARD_2, 5000);

        dashboardPage.waitForDashboard();

        int balance1After = dashboardPage.getBalance(CARD_1);
        int balance2After = dashboardPage.getBalance(CARD_2);

        assertEquals(balance1Before + 5000, balance1After,
                "Баланс карты 0001 должен увеличиться на 5000");
        assertEquals(balance2Before - 5000, balance2After,
                "Баланс карты 0002 должен уменьшиться на 5000");
    }

    @Test
    @Disabled("Баг: баланс уходит в минус при переводе больше баланса. См. issue #1")
    void shouldNotTransferMoreThanBalance() {
        int balance2Before = dashboardPage.getBalance(CARD_2);

        dashboardPage.clickDeposit(CARD_1);
        TransferPage transferPage = new TransferPage();
        transferPage.transfer(CARD_2, balance2Before + 5000);

        // Ожидаем ошибку, но SUT её не показывает — баг
        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
    }
}