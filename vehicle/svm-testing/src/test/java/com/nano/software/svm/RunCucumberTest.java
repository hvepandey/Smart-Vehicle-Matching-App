package com.nano.software.svm;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * Cucumber runner test class for running the cucumber test cases
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = {"features"}, tags = {"Complete"})
public class RunCucumberTest
{ }
