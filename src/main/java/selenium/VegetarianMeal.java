package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class VegetarianMeal {

    // Emre's  = "C:\\Users\\Emre\\Downloads\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe"
    private static String loc = "C:\\Users\\Emre\\Downloads\\"
        + "phantomjs-2.1.1-windows\\bin\\phantomjs.exe";

    /**
     * gives a proper score to add.
     * @param emission raw data from the web
     * @return returns a proper number to add
     */

    public static double points(String emission) {
        int begin = emission.indexOf("=");
        int end = emission.indexOf("metric");
        double footprint = Double.parseDouble(emission.substring(begin + 1, end));
        return footprint * 1000;

    }

    /**
     * Calculates the amount of points based on the CO2 saved for 1
     *                  solar panel and 4°C lower house temp.
     * @param choice 1 if user chooses a solar panel and to if user chooses to lower house temp.
     * @return returns a proper number to add
     */
    public static double houseEmission(int choice) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            loc);
        WebDriver house = new PhantomJSDriver(capabilities);
        house.get("https://calculator.carbonfootprint.com/calculator.aspx?c=Full&tab=2");
        double score = 0;
        if (choice == 1) { //installed solar panels
            WebElement electricity = house.findElement(By.name("ctl05$chsHouse$txtElecUsage"));
            electricity.sendKeys("365"); // 1 kWh/m^2 of solar panel

            house.findElement(By.name("ctl05$chsHouse$btnAddHouse")).click();
            String result = house.findElement(By.id("lblTabTotal")).getText();
            score = points(result);

        } else if (choice == 2) { // lowered Temp.

            WebElement gas = house.findElement(By.name("ctl05$chsHouse$txtGasUsage"));
            Select unit = new Select(house.findElement(By.name("ctl05$chsHouse$ddlGasUsageUnit")));
            unit.selectByVisibleText("kWh");
            gas.sendKeys("1800"); // 15000 kWh/year and you save 3% when lowering the temp.
            house.findElement(
                By.name("ctl05$chsHouse$btnAddHouse")).click(); //  Only noticeable above 4°C.
            String result = house.findElement(By.id("lblTabTotal")).getText();
            score = points(result);
        }
        return score / 365;
    }

    /** The method determines the CO2 output of a food related actions
     * and returns points based on that.
     * @return the amount of points depending on how much CO2 you save
     */
    public static double foodEmission() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            loc);
        WebDriver food = new PhantomJSDriver(capabilities);
        food.get("https://calculator.carbonfootprint.com/calculator.aspx?c=Full&tab=7");
        Select dropdown2 = new Select(food.findElement(By.name("ctl05$ddlCurrency")));
        dropdown2.selectByVisibleText("€ EUR");
        WebElement foodCost = food.findElement(
            By.name("ctl05$repConsumption$ctl01$ccsSpend$txtSpend"));
        Select dropdown1 = new Select(food.findElement(
            By.name("ctl05$repConsumption$ctl01$ccsSpend$ddlTimeUnit")));
        dropdown1.selectByVisibleText("per week");
        foodCost.sendKeys("1");
        food.findElement(By.name("ctl05$btnAddConsumption")).click();
        String result =  food.findElement(By.id("lblTabTotal")).getText();
        double score = points(result);
        return Math.round(score);
    }

    /**
     * This method calculates the amount of points you get by taking the bike instead of the car.
     *      A later method is going to recive user input on hom many daily km he takes
     * @return the method returns the amount of points for one km of taking the bike
     */
    public static double carEmission() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            loc);
        WebDriver car = new PhantomJSDriver(capabilities);
        car.get("http://impact.brighterplanet.com/models/automobile");
        Select dropdown = new Select(car.findElement(By.id("key_1480474671")));
        dropdown.selectByVisibleText("Annual distance");
        car.findElement(By.id("val_1480474671")).sendKeys("1");
        try {
            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = car.findElement(By.id("footprint")).getText();
        int end = result.indexOf(" ");

        return Double.parseDouble(result.substring(0, end));
    }


    /**
     * publicTransEmission returns points depending on how how CO2 you saved.
     * @param choice 1 if the user selects the train, 2 if user selects the bus
     * @param distance how much the user travels with the bus/train. 1 for short distance,
     *                 2 medium distance, 3 long distance.
     * @return an amount of points depending on how much CO2 you save
     */


    public static double publicTransEmission(int choice, int distance) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            loc);
        WebDriver publicTrans = new PhantomJSDriver(capabilities);
        publicTrans.get("https://calculator.carbonfootprint.com/calculator.aspx?c=Full&tab=6");
        double carMileage = 0;

        if (choice == 1) {
            WebElement trainMileage = publicTrans.findElement(
                By.name("ctl05$cdsIntRail$txtMileage"));

            Select mileageUnitT = new Select(publicTrans.findElement(
                By.name("ctl05$cdsIntRail$ddlMileageUnit")));
            mileageUnitT.selectByVisibleText("km");

            switch (distance) {
                case 1:
                    trainMileage.sendKeys("50");
                    carMileage = 0.01;
                    break;
                case 2:
                    carMileage = 0.02;
                    trainMileage.sendKeys("100");
                    break;
                case 3:
                    carMileage = 0.03;
                    trainMileage.sendKeys("150");
                    break;
                default:
                    return 0;
            }
        } else if (choice == 2) {

            WebElement busMileage = publicTrans.findElement(By.name("ctl05$cdsBus$txtMileage"));
            Select mileageUnitB = new Select(publicTrans.findElement(
                By.name("ctl05$cdsBus$ddlMileageUnit")));
            mileageUnitB.selectByVisibleText("km");

            switch (distance) {
                case 1:
                    busMileage.sendKeys("25");
                    carMileage = 0.01;
                    break;
                case 2:
                    carMileage = 0.02;
                    busMileage.sendKeys("50");
                    break;
                case 3:
                    carMileage = 0.03;
                    busMileage.sendKeys("75");
                    break;
                default:
                    return 0;
            }

        }
        publicTrans.findElement(By.name("ctl05$btnAddBus")).click();
        String result = publicTrans.findElement(By.id("lblTabTotal")).getText();
        return  (carMileage * 1000) - (points(result));

    }
}
