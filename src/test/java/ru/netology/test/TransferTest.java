package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.data.AuthData;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

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
        VerificationPage verificationPage = loginPage.login(AuthData.LOGIN, AuthData.PASSWORD);
        verificationPage.verify(AuthData.VERIFICATION_CODE);
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

        transferPage.waitForError();
    }
}