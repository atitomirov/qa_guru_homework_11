package com.atitomirov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static io.qameta.allure.Allure.step;

public class IssueNameTest extends TestBase {

    private static final String REPOSITORY = "eroshenkoam/allure-example",
            ISSUE_NUMBER = "81",
            ISSUE_NAME = "issue_to_test_allure_report";

    @Test
    @DisplayName("Проверка названия Issue - Чистый Selenide с Listener")
    public void testIssueSearch() {

        open("/");
        $(".header-search-input").setValue(REPOSITORY).submit();
        $(linkText("eroshenkoam/allure-example")).click();
        $("#issues-tab").click();
        $(String.format("#issue_%s_link", ISSUE_NUMBER)).shouldHave(text(ISSUE_NAME));
    }

    @Test
    @DisplayName("Проверка названия Issue - Лямбда шаги через step")
    public void testLambdaStep() {

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем название Issue с номером " + ISSUE_NUMBER, () -> {
            $(String.format("#issue_%s_link", ISSUE_NUMBER)).shouldHave(text(ISSUE_NAME));
        });
    }

    @Test
    @DisplayName("Проверка названия Issue - Шаги с аннотацией @Step")
    public void testAnnotatedStep() {

        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldNameIssue(ISSUE_NUMBER, ISSUE_NAME);
        steps.takeScreenshot();

    }
}
