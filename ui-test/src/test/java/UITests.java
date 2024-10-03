import Listeners.AllureTestWatcher;
import com.codeborne.selenide.*;
import core.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.codeborne.selenide.Selenide.*;
import static schema.ALERTS.CONTEXT_MENU_ALERT;
import static schema.NOTIFICATIONS.*;
import static schema.URLS.*;


@ExtendWith(AllureJunit5.class)
@ExtendWith(AllureTestWatcher.class)
public class UITests extends BaseUITest {

    @ParameterizedTest
    @MethodSource("checkboxOrderProvider")
    @Step("checkboxesTest")
    public void checkboxesTest(int[] checkboxOrder) {

        ElementsCollection checkboxes = $$x("//input");
        open(CHECKBOXES);

        for (int index : checkboxOrder) {
            SelenideElement checkbox = checkboxes.get(index);
            boolean isCheckedBeforeClick = checkbox.isSelected();
            checkbox.click();
            if (isCheckedBeforeClick) {
                checkbox.shouldNotBe(Condition.checked);
            } else {
                checkbox.shouldBe(Condition.checked);
            }
            System.out.println("Checkbox " + index + " checked attribute: " + checkbox.isSelected());
        }

    }
    private static int[][] checkboxOrderProvider() {
        return new int[][] {
                {0, 1},
                {1, 0}
        };
    }


    @Test
    @Step("dropdownTest")
    public void dropdownTest() {
        open(DROPDOWN);

        ElementsCollection dropdownOptions = $$x("//select/option[1]/following-sibling::*");
        SelenideElement dropdownList = $x("//select");

        dropdownList.click();

        for (SelenideElement dropdownOption : dropdownOptions) {
            dropdownOption.click();
            dropdownOption.shouldBe(Condition.selected);
            System.out.println("Option:" + dropdownOption.getText());
            dropdownList.click();

        }

    }

    @RepeatedTest(5)
    @Step("disappearingElementsTest")
    public void disappearingElementsTest(){
        int maxAttempts = 10;

        open(DISAPPEARING_ELEMENTS);

        ElementsCollection disappearingElements = $$x("//li");

        for (int i = 0; i < maxAttempts; i++) {
            if (disappearingElements.size() != 5) {
                refresh();
                disappearingElements = $$x("//li");
            }
            else break;
        }
        disappearingElements.shouldHave(CollectionCondition.size(5));
    }


    @TestFactory
    List<DynamicTest> inputFactoryTests(){
        List<DynamicTest> inputDynamicTests = new ArrayList<>();

        String[] inputValues = {"12345", "0000000", "-789", "999999", "5.6", "asdasdasd", "=///::", "абв", " 22", "33 "};
        for (String value : inputValues) {
            inputDynamicTests.add(DynamicTest.dynamicTest("Testing value:" + value, () -> inputsTest(value)));
        }
        return inputDynamicTests;
    }

    @Step("inputsTest")
    public void inputsTest(String inputValue) {
        open(INPUTS);
        SelenideElement input = $x("//input");

        input.click();
        input.sendKeys(inputValue);
        String actualInputValue = input.getValue();

        System.out.println("Value:"+actualInputValue);

        input.shouldHave(Condition.value(inputValue));
        Assertions.assertEquals(inputValue, actualInputValue); // без этой доп строчки тесты с пробелами проходили


    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    @Step("hoversTest")
    public void hoversTest(int hoverIndex) {

        String[] expectedTexts = {"name: user1", "name: user2", "name: user3"};
        open(HOVERS);

        ElementsCollection hoverElements = $$x("//div/img");
        ElementsCollection hiddenText = $$x("//h5");

            hoverElements.get(hoverIndex).hover();
            System.out.println(hiddenText.get(hoverIndex).getText());
            hiddenText.get(hoverIndex).shouldHave(Condition.text(expectedTexts[hoverIndex]));


    }

    @RepeatedTest(10)
    @Step("notificationTest")
    public void notificationTest() {

        open(NOTIFICATION_MESSAGE);

        SelenideElement notificationButton = $x("//p/a");
        SelenideElement notificationText = $x("//div[@class = 'flash notice']");
        notificationButton.click();
        notificationText.shouldHave(Condition.text((NOTIFICATION_SUCCESSFUL)));

    }


    @TestFactory
    List<DynamicTest> addRemoveFactoryTests(){
        List<DynamicTest> addRemoveDynamicTests = new ArrayList<>();
        int[] addCountValues = {2,5,1};
        int[] deleteCountValues = {1,2,3};

        for (int i = 0; i < addCountValues.length; i++) {
            int addCount = addCountValues[i];
            int deleteCount = deleteCountValues[i];
            addRemoveDynamicTests.add(DynamicTest.dynamicTest("Testing values: " + addCount + ", " + deleteCount, () -> addRemoveElementsTest(addCount, deleteCount)));}
        return addRemoveDynamicTests;
    }

    @Step("addRemoveElementsTest")
    public void addRemoveElementsTest(int addCount, int deleteCount) {

        Random random = new Random();
        int randomDeleteButton;
        int expectedDeleteButtons = 0;

        open(ADD_REMOVE_ELEMENTS);
        ElementsCollection deleteButton = $$x("//button[@onclick='deleteElement()']");
        SelenideElement addRemoveButton = $x("//button[@onclick='addElement()']");
        for (int i = 0; i < addCount; i++){
            addRemoveButton.click();
            deleteButton = $$x("//button[@onclick='deleteElement()']");
            expectedDeleteButtons++;
            Assertions.assertEquals(expectedDeleteButtons, deleteButton.size());
            System.out.println(deleteButton.last().getText());
        }
        for (int i = 0; i < deleteCount; i++){
            randomDeleteButton = random.nextInt(deleteButton.size());
            deleteButton.get(randomDeleteButton).click();
            deleteButton = $$x("//button[@onclick='deleteElement()']");
            expectedDeleteButtons--;
            Assertions.assertEquals(expectedDeleteButtons, deleteButton.size());
            System.out.println("Remaining delete buttons after deletion: " + deleteButton.size());
            System.out.println("Delete buttons texts: " + deleteButton.texts());

        }
    }

    @Test
    @Step("statusCodeTest")
    public void statusCodeTest() {

        open(STATUS_CODES);
        ElementsCollection statusCodes = $$x("//li/a");
        SelenideElement statusCodeText1;
        SelenideElement statusCodeText2;
        int expectedStatusCode = 0;
        String[] expectedStatusCodes = {"This page returned a 200 status code.", "This page returned a 301 status code.", "This page returned a 404 status code.", "This page returned a 500 status code."};

        for (SelenideElement statusCode : statusCodes){
            statusCode.click();
            statusCodeText1 = $x("//h3");
            statusCodeText2 = $x("//p");
            System.out.println(statusCodeText1.getText());
            System.out.println(statusCodeText2.getText());
            statusCodeText2.shouldHave(Condition.text(expectedStatusCodes[expectedStatusCode]));
            expectedStatusCode++;
            back();
        }

    }

    @Test
    @Step("dragAndDrop")
    public void dragAndDropTest() {
        open(DRAG_AND_DROP);
        Actions actions = Selenide.actions();
        SelenideElement elementA = $x("//div[@id='column-a']");
        SelenideElement elementB = $x("//div[@id='column-b']");
        actions.clickAndHold(elementA) //реализация без метода dragAndDrop() на оценку 10
            .moveToElement(elementB)
            .release().perform();

        elementA.shouldHave(Condition.text("B"));
        elementB.shouldHave(Condition.text("A"));

    }

    @Test
    @Step("contextMenuTest")
    public void contextMenuTest() {
        open(CONTEXT_MENU);
        SelenideElement contextMenuButton = $x("//div[@id='hot-spot']");
        contextMenuButton.contextClick();
        Alert alert = Selenide.switchTo().alert();
        String alertText = alert.getText();

        Assertions.assertEquals(alertText, CONTEXT_MENU_ALERT);
    }

    @Test
    @Step("infiniteScrollTest")
    public void infiniteScrollTest() {
        open(INFINITE_SCROLL);
        Actions actions = Selenide.actions();
        SelenideElement text = $x("//div[@class='jscroll-inner']");
        while(!text.innerText().contains("Eius")) {
            actions.scrollByAmount(0, 300);
            actions.perform();
            text = $x("//div[@class='jscroll-inner']");
        }
        SelenideElement paragraph = $x("//div[@class='jscroll-inner']/div[@class='jscroll-added'][last()]");
        paragraph.shouldBe(Condition.text("Eius"));
        Assertions.assertTrue(paragraph.isDisplayed());

    }

    @Test
    @Step("keyPressesTest")
    public void keyPressesTest() {
        open(KEY_PRESSES);
        SelenideElement input = $x("//input");
        SelenideElement result = $x("//p[@id='result']");
        Keys[] keys = {Keys.ENTER,Keys.CONTROL,Keys.ALT,Keys.TAB };
        String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        Actions actions = Selenide.actions();
        for (String c : chars){
            input.click();
            input.sendKeys(c);
            Assertions.assertTrue(result.getText().contains(c.toUpperCase()));
        }
        result.click();
        for (Keys key : keys){
            actions.sendKeys(key).perform();
            Assertions.assertTrue(result.getText().contains(key.name()));
        }




    }

}
