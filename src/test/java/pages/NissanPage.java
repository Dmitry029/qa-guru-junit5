package pages;

import com.codeborne.selenide.CollectionCondition;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class NissanPage {

    public NissanPage openPage() {
        open("nissan");
        return this;
    }

    public void checkingSearchResultIsNotEmpty() {
        $$(".listing-item")
                .filterBy(visible)
                .should(sizeGreaterThanOrEqual(1));
    }

    public NissanPage selectModel(String carModel) {
        $(String.format("[title='%s']", carModel)).click();
        return this;
    }

    public void checkThatCarsAreSoldInTheCityFromTheList(List<String> location) {
        $$(".listing-item .listing-item__location")
                .filterBy(visible)
                .should(CollectionCondition.containExactTextsCaseSensitive(location));
    }

    public boolean ifThereIsLowerPrice(int prise) {
        return $$(".listing-item__price")
                .should(sizeGreaterThanOrEqual(1))
                .texts()
                .stream().map(e -> e.replaceAll("[^\\d]", "")).toList()
                .stream().map(Integer::parseInt).toList()
                .stream().anyMatch(e -> e < prise);
    }
}
