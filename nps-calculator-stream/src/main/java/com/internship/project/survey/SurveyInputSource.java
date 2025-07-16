package com.internship.project.survey;

import com.internship.project.data.SurveyInput;
import com.internship.project.data.common.Answer;
import com.internship.project.data.common.Participant;
import com.internship.project.data.common.Question;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class SurveyInputSource extends RichParallelSourceFunction<SurveyInput> {

  Boolean running = true;
  String[] QUESTION_TYPES = {"yes_no", "qcm", "scale", "comment"};

  /**
   * Starts the source. Implementations use the {@link SourceContext} to emit elements. Sources
   * that checkpoint their state for fault tolerance should use the {@link
   * SourceContext#getCheckpointLock() checkpoint lock} to ensure consistency between the
   * bookkeeping and emitting the elements.
   *
   * <p>Sources that implement {@link CheckpointedFunction} must lock on the {@link
   * SourceContext#getCheckpointLock() checkpoint lock} checkpoint lock (using a synchronized
   * block) before updating internal state and emitting elements, to make both an atomic
   * operation.
   *
   * <p>Refer to the {@link SourceFunction top-level class docs} for an example.
   *
   * @param ctx The context to emit elements to and for accessing locks.
   */
  @Override
  public void run(SourceContext<SurveyInput> ctx) throws Exception {
    while(running) {
      ctx.collect(
        new SurveyInput(
          UUID.randomUUID().toString(),
          UUID.randomUUID().toString(),
          UUID.randomUUID().toString(),
          "Sample Survey Subject",
          LocalDateTime.now().toString(),
          new Question(
            UUID.randomUUID().toString(),
            "Sample Question Name",
            UUID.randomUUID().toString(),
            QUESTION_TYPES[(new Random()).nextInt(4)]
          ),
          new Participant(UUID.randomUUID().toString(), "07123456789"),
          new Answer(UUID.randomUUID().toString(), "Sample Answer", LocalDateTime.now().toString())
        )
      );
      Thread.sleep(5000);
    }
  }

  /**
   * Cancels the source. Most sources will have a while loop inside the {@link
   * #run(SourceContext)} method. The implementation needs to ensure that the source will break
   * out of that loop after this method is called.
   *
   * <p>A typical pattern is to have an {@code "volatile boolean isRunning"} flag that is set to
   * {@code false} in this method. That flag is checked in the loop condition.
   *
   * <p>In case of an ungraceful shutdown (cancellation of the source operator, possibly for
   * failover), the thread that calls {@link #run(SourceContext)} will also be {@link
   * Thread#interrupt() interrupted}) by the Flink runtime, in order to speed up the cancellation
   * (to ensure threads exit blocking methods fast, like I/O, blocking queues, etc.). The
   * interruption happens strictly after this method has been called, so any interruption handler
   * can rely on the fact that this method has completed (for example to ignore exceptions that
   * happen after cancellation).
   *
   * <p>During graceful shutdown (for example stopping a job with a savepoint), the program must
   * cleanly exit the {@link #run(SourceContext)} method soon after this method was called. The
   * Flink runtime will NOT interrupt the source thread during graceful shutdown. Source
   * implementors must ensure that no thread interruption happens on any thread that emits records
   * through the {@code SourceContext} from the {@link #run(SourceContext)} method; otherwise the
   * clean shutdown may fail when threads are interrupted while processing the final records.
   *
   * <p>Because the {@code SourceFunction} cannot easily differentiate whether the shutdown should
   * be graceful or ungraceful, we recommend that implementors refrain from interrupting any
   * threads that interact with the {@code SourceContext} at all. You can rely on the Flink
   * runtime to interrupt the source thread in case of ungraceful cancellation. Any additionally
   * spawned threads that directly emit records through the {@code SourceContext} should use a
   * shutdown method that does not rely on thread interruption.
   */
  @Override
  public void cancel() {
    running = false;
  }
}
