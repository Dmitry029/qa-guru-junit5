package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pages.MainPage;
import pages.NissanPage;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvByTests extends BaseTest {

    private final MainPage mainPage = new MainPage();
    private final NissanPage nissanPage = new NissanPage();

    @Tag("SMOKE") // в консоли:    ./gradlew clean myTags -x test -DcustomTags="SMOKE,REGRESS"
    @ParameterizedTest(name = "Проверка правильности перехода на страницу автобренда {1} после нажатия кнопки {0}")
    @CsvSource({
            "Ford, ford",
            "Mercedes-Benz, mercedes-benz",
            "Nissan, nissan"
    })
    void checkingCorrectTransitionToTheCarBrandPage(String carBrand, String partOfUrl) {
        mainPage.openPage()
                .selectCarBrand(carBrand);
        assertTrue(url().contains(partOfUrl));
    }

    @ParameterizedTest(name = "Проверка того, что поиск по модели авто {0} дает не нулевой результат")
    @ValueSource(strings = {"Patrol", "Qashqai", "X-Trail"})
    void checkSearchResults(String carModel) {
        nissanPage.openPage()
                .selectModel(carModel)
                .checkingSearchResultIsNotEmpty();
    }

    @ParameterizedTest(name = "Проверка того, что есть авто модели {0} c ценой не более {1}")
    @CsvSource({
            "X-Trail, 70000",
            "Qashqai, 40000",
            "Leaf, 25000"
    })
    public void checkingPrise(String model, int prise) {
        nissanPage.openPage()
                .selectModel(model);
        assertTrue(nissanPage.ifThereIsLowerPrice(prise));
    }

    static class DataProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("X-Trail", List.of("Минск", "Гродно")),
                    Arguments.of("Qashqai", List.of("Минск", "Брест")),
                    Arguments.of("Leaf", List.of("Минск", "Гомель"))
            );
        }
    }
@Tag("REGRESS")
    @ParameterizedTest(name = "Проверка того, что в заданном месте {1} есть авто модели {0}")
    @ArgumentsSource(DataProvider.class)
    public void checkingLocation(String model, List<String> location) {
        nissanPage.openPage()
                .selectModel(model)
                .checkThatCarsAreSoldInTheCityFromTheList(location);
    }
}