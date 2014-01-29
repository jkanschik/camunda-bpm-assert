package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsWaitingAtExactlyTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Only_Activity_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    assertThat(processInstance).isWaitingAtExactly("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Only_Activity_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    try {
      assertThat(processInstance).isWaitingAtExactly("UserTask_2");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Non_Existing_Activity_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    try {
      assertThat(processInstance).isWaitingAtExactly("NonExistingUserTask");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_One_Of_Two_Activities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isWaitingAtExactly("UserTask_2", "UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_One_Of_Two_Activities_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // And
    complete(taskQuery().singleResult());
    // When
    try {
      assertThat(processInstance).isWaitingAt("UserTask_1", "UserTask_2");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Null_Error() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    try {
      // When
      //noinspection NullArgumentToVariableArgMethod
      assertThat(processInstance).isWaitingAt(null);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      assertThat(processInstance).isWaitingAt("ok", null);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      assertThat(processInstance).isWaitingAt(null, "ok");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      String [] args = new String[] {};
      assertThat(processInstance).isWaitingAt(args);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
