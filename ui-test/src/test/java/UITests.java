import com.codeborne.selenide.*;
import core.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;
import static schema.NotificationTest.*;
import static schema.URLS.*;

public class UITests extends BaseUITest {

    @Test
    public void checkboxesTest() {
        ElementsCollection checkboxes = $$x("//input");
        open(CHECKBOXES);

        for (SelenideElement checkbox : checkboxes) {
            checkbox.click();
            System.out.println(checkbox.getAttribute("checked"));
        }


    }

    @Test
    public void dropdownTest() {
        open(DROPDOWN);

        ElementsCollection dropdownOptions = $$x("//select/option[1]/following-sibling::*");
        SelenideElement dropdownList = $x("//select");

        dropdownList.click();

        for (SelenideElement dropdownOption : dropdownOptions) {
            dropdownOption.click();
            System.out.println("Option:" + dropdownOption.getText());
        }

    }

    @Test
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

        assert disappearingElements.size() == 5;

    }

    @Test
    public void inputsTest() {
        open(INPUTS);
        SelenideElement input = $x("//input");

        input.click();
        input.sendKeys("12345");

        System.out.println("Value:"+input.getValue());
    }

    @Test
    public void hoversTest() {
        open(HOVERS);

        ElementsCollection hoverElements = $$x("//div/img");
        ElementsCollection hiddenText = $$x("//h5");

        for (int i = 0; i < hoverElements.size(); i++) {
            hoverElements.get(i).hover();
            System.out.println(hiddenText.get(i).getText());
        }
    }

    @Test
    public void notificationTest() {

        open(NOTIFICATION_MESSAGE);

        SelenideElement notificationButton = $x("//p/a");
        SelenideElement notificationText = $x("//div[@class = 'flash notice']");
        String actualUnsuccess;
        do {
            notificationButton.click();
            actualUnsuccess = notificationText.getText().replace("×", "").trim();
        } while (actualUnsuccess.equals(NOTIFICATION_UNSUCCESSFUL));

        String actualSuccess = notificationText.getText().replace("×", "").trim();
        assert actualSuccess.equals(NOTIFICATION_SUCCESSFUL);
    }

    @Test
    public void addRemoveElementsTest() {

        Random random = new Random();
        int randomDeleteButton;

        open(ADD_REMOVE_ELEMENTS);
        ElementsCollection deleteButton = $$x("//button[@onclick='deleteElement()']");
        SelenideElement addRemoveButton = $x("//button[@onclick='addElement()']");
        for (int i = 0; i < 5; i++){
            addRemoveButton.click();
            deleteButton = $$x("//button[@onclick='deleteElement()']");
            System.out.println(deleteButton.last().getText());
        }

        for (int i = 0; i < 3; i++){
            randomDeleteButton = random.nextInt(deleteButton.size());
            deleteButton.get(randomDeleteButton).click();
            deleteButton = $$x("//button[@onclick='deleteElement()']");
            System.out.println("Remaining delete buttons after deletion: " + deleteButton.size());
            System.out.println("Delete buttons texts: " + deleteButton.texts());

        }
    }

    @Test
    public void statusCodeTest() {

        open(STATUS_CODES);
        ElementsCollection statusCodes = $$x("//li/a");
        SelenideElement statusCodeText1;
        SelenideElement statusCodeText2;

        for (SelenideElement statusCode : statusCodes){
            statusCode.click();
            statusCodeText1 = $x("//h3");
            statusCodeText2 = $x("//p");
            System.out.println(statusCodeText1.getText());
            System.out.println(statusCodeText2.getText());
            back();
        }

    }

}
