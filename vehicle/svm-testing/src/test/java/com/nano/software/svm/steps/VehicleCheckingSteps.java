package com.nano.software.svm.steps;

import com.nano.software.DirectoryScanner;
import com.nano.software.configuration.ConfigurationException;
import com.nano.software.dto.FileDetails;
import com.nano.software.dto.Vehicle;
import com.nano.software.service.DirectoryScannerService;
import cucumber.api.java8.En;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;


/**
 * This class implements all the steps definition required for vehicle checking
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class VehicleCheckingSteps
        implements En {

    final WebDriver driver;

    public VehicleCheckingSteps()
            throws ConfigurationException,
            IOException {

        //FIXME - this should be read from a property file or supplied as a jvm option
        System.setProperty("webdriver.gecko.driver", "C:\\santosh\\dev\\2018\\projects\\vehicle\\drivers\\geckodriver.exe");

        final DirectoryScanner directoryScanner = new DirectoryScannerService();
        final List<Optional<FileDetails>> vehicleFiles = directoryScanner.getFilesDetails();
        driver = new FirefoxDriver();


        Given("^I have vehicles details in (\\d+) csv files and (\\d+) excel files at a pre-configured input directory$",
                (final Integer noOfCsvFile, final Integer noOfExcelFile) -> {

                    assertAll("Verify number of files",
                            () -> {
                                assertThat("Vehicle vehicleFiles not found", vehicleFiles, is(notNullValue()));
                                assertThat("Unexpected no of vehicle vehicleFiles found", vehicleFiles.size(),
                                            is(equalTo(noOfCsvFile + noOfExcelFile)));
                            });
                });


        When("^I launch get vehicle information from DVLA site (.*)$", (String baseUrl) -> {
            launchDVLASite(baseUrl);
        });


        Then("^I check colour and make of each vehicle on the Vehicle Details Result Page$", () -> {

            vehicleFiles.forEach(vehicleFile -> {

                try {
                    final Optional<List<Vehicle>> vehicleList = directoryScanner.getAllVehiclesFromFile(vehicleFile.get());

                    vehicleList.ifPresent(vList -> {

                        /*
                         * Only check the vehicle details if there are more than one row in the file, assuming the first row is
                         * always the heading in the file
                         */
                        if (vList.size() > 1) {

                            assertAll("Verify file header information",
                                    () -> {
                                        assertThat("Incorrect format of vehicle entry found in the file",
                                                vList.get(0).getRegistrationNo(), is(equalTo("Registration No")));
                                        assertThat("Incorrect format of vehicle entry found in the file",
                                                vList.get(0).getMake(), is(equalTo("Make")));
                                        assertThat("Incorrect format of vehicle entry found in the file",
                                                vList.get(0).getColour(), is(equalTo("Colour")));
                                    });

                            //Start checking the vehicle details from the second row of the file
                            vList.stream().skip(1).forEach(veh -> {

                                enterRegistrationNo(veh.getRegistrationNo());

                                verifyVehicle(veh);

                                goBackToVehicleCheckPage();

                            });
                        }
                    });

                } catch (IOException ioex) {
                    throw new RuntimeException(ioex);
                }

            });

        });

    }

    private void launchDVLASite(final String baseUrl) throws URISyntaxException {

        this.driver.manage().window().maximize();
        URIBuilder ub = new URIBuilder(baseUrl);
        driver.get(ub.toString());
        this.driver.findElement(By.className("gem-c-button--start")).click();

    }

    private void enterRegistrationNo(final String registrationNo) {

        waitForPageLoad(this.driver);
        this.driver.findElement(By.id("Vrm")).sendKeys(registrationNo);
        this.driver.findElement(By.className("button")).click();

    }

    private void verifyVehicle(final Vehicle vehicle) {

        final List<WebElement> actualVehicleDetails = this.driver.findElements(By.xpath("//*[@class='list-summary-item']"));

        final String actualRegistration = actualVehicleDetails.get(0).getText().split("\n")[1];
        final String actualMake = actualVehicleDetails.get(1).getText().split("\n")[1];
        final String actualColour = actualVehicleDetails.get(2).getText().split("\n")[1];

        assertAll("Verify vehicle information",
                () -> {
                    assertThat("Vehicle registration does not match", actualRegistration,
                            is(equalTo(vehicle.getRegistrationNo().toUpperCase())));
                    assertThat("Vehicle make does not match", actualMake, is(equalTo(vehicle.getMake().toUpperCase())));
                    assertThat("Vehicle colour does not match", actualColour, is(equalTo(vehicle.getColour().toUpperCase())));
                });

    }

    private void waitForPageLoad(final WebDriver wdriver) {

        final WebDriverWait wait = new WebDriverWait(wdriver, 60);
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        final WebElement ContinueButton =
                wait.until(webDriver -> webDriver.findElement(By.className("button")));

    }

    private void goBackToVehicleCheckPage() {

        this.driver.findElement(By.className("back-to-previous")).click();

    }

}
