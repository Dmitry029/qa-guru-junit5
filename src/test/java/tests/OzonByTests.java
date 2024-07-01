package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

public class OzonByTests extends BaseTest {

    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("First test")
    void firstTest() {
        mainPage.openPage();
    }

}
