# Домашнее задание «2.4. BDD» — Задача №1: Page Object's

Автотесты перевода с карты на карту для SUT `app-ibank-build-for-testers.jar`.

## Структура
- `page/LoginPage` — логин + код верификации.
- `page/DashboardPage` — список карт, получение баланса, выбор карты для пополнения.
- `page/TransferPage` — форма перевода.
- `test/TransferTest` — тесты перевода.

## Найденные баги
- [Issue #1](https://github.com/maximyspraim-alt/HW_bdd_1/issues/1) — перевод суммы больше баланса уводит баланс карты в минус.