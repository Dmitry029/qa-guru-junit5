package tests;

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

    static class DataProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("X-Trail", List.of("Минск", "Гродно"), 70000),
                    Arguments.of("Qashqai", List.of("Гомель", "Брест"), 40000),
                    Arguments.of("Leaf", List.of("Гродно", "Могилев"), 25000)
            );
        }
    }

    @ParameterizedTest(name = "Проверка того, что в заданном месте {1} есть авто модели {0} и есть цена не более {2}")
    @ArgumentsSource(DataProvider.class)
    public void checkingLocationAndPrise(String model, List<String> location, int prise) {
        nissanPage.openPage()
                .selectModel(model)
                .checkThatCarsAreSoldInTheCityFromTheList(location);
        assertTrue(nissanPage.ifThereIsLowerPrice(prise));
    }
}