package com.midway.starwarsapi;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:cucumber-features/was_file_imported.feature"},
        glue = {"com.midway.starwars.cucumber"})
public class RunCucumberTests {
}
