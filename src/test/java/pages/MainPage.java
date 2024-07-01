package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    public MainPage openPage() {
        open("");
        return this;
    }

    public void selectCarBrand(String model) {
        $(String.format("[title='%s']", model)).click();
    }
}
