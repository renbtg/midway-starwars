package com.midway.starwarsapi.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

class WasFileImported {
    static String wasFileImported(String filename) {
        return null;
    }
}

public class StepDefinitions {
    private String filename;
    private String actualAnswer;

    @Given("^a filename$")
    public void aFile() {
        filename = "testFile.txt";
        throw new io.cucumber.java.PendingException();
    }

    @When("^I ask to import the file$")
    public void iAskToImport() {
        actualAnswer = WasFileImported.wasFileImported(filename);
        throw new io.cucumber.java.PendingException();
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void iShouldBeTold(String expectedAnswer) {
        // Write code here that turns the phrase above into concrete actions
        Assertions.assertEquals(expectedAnswer, actualAnswer);
        throw new io.cucumber.java.PendingException();
    }
}
