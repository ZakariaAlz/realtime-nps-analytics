///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package org.apache.flink.playgrounds.ops.clickcount;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInput;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInput.Participant;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInput.Answer;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInput.Question;
import org.apache.flink.playgrounds.ops.clickcount.model.SurveyInputSerializer;;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import static org.apache.flink.playgrounds.ops.clickcount.ClickEventCount.WINDOW_SIZE;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;
//import static org.apache.flink.playgrounds.ops.clickcount.ClickEventCount.WINDOW_SIZE;
//
///**
// * A generator which pushes {@link ClickEvent}s into a Kafka Topic configured via `--topic` and
// * `--bootstrap.servers`.
// *
// * <p> The generator creates the same number of {@link ClickEvent}s for all pages. The delay between
// * events is chosen such that processing time and event time roughly align. The generator always
// * creates the same sequence of events. </p>
// *
// */
//public class ClickEventGenerator {
//
//	public static final int EVENTS_PER_WINDOW = 1000;
//
//	private static final List<String> pages = Arrays.asList("/help", "/index", "/shop", "/jobs", "/about", "/news");
//
//	//this calculation is only accurate as long as pages.size() * EVENTS_PER_WINDOW divides the
//	//window size
//	public static final long DELAY = WINDOW_SIZE.toMilliseconds() / pages.size() / EVENTS_PER_WINDOW;
//
//	public static void main(String[] args) throws Exception {
//
//		final ParameterTool params = ParameterTool.fromArgs(args);
//
//		String topic = params.get("topic", "input");
//
//		Properties kafkaProps = createKafkaProperties(params);
//
//		KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(kafkaProps);
//
//		ClickIterator clickIterator = new ClickIterator();
//
//		while (true) {
//
//			ProducerRecord<byte[], byte[]> record = new ClickEventSerializationSchema(topic).serialize(
//					clickIterator.next(),
//					null);
//
//			producer.send(record);
//
//			Thread.sleep(DELAY);
//		}
//	}
//
//	private static Properties createKafkaProperties(final ParameterTool params) {
//		String brokers = params.get("bootstrap.servers", "localhost:9092");
//		Properties kafkaProps = new Properties();
//		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
//		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
//		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
//		return kafkaProps;
//	}
//
//	static class ClickIterator  {
//
//		private Map<String, Long> nextTimestampPerKey;
//		private int nextPageIndex;
//
//		ClickIterator() {
//			nextTimestampPerKey = new HashMap<>();
//			nextPageIndex = 0;
//		}
//
//		ClickEvent next() {
//			String page = nextPage();
//			return new ClickEvent(nextTimestamp(page), page);
//		}
//
//		private Date nextTimestamp(String page) {
//			long nextTimestamp = nextTimestampPerKey.getOrDefault(page, 0L);
//			nextTimestampPerKey.put(page, nextTimestamp + WINDOW_SIZE.toMilliseconds() / EVENTS_PER_WINDOW);
//			return new Date(nextTimestamp);
//		}
//
//		private String nextPage() {
//			String nextPage = pages.get(nextPageIndex);
//			if (nextPageIndex == pages.size() - 1) {
//				nextPageIndex = 0;
//			} else {
//				nextPageIndex++;
//			}
//			return nextPage;
//		}
//	}
//}


//
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.Optional;
//import java.util.Properties;
//import java.util.Random;
//import java.util.UUID;
//import java.time.LocalDateTime;

public class ClickEventGenerator {

		private static final Logger LOG = LoggerFactory.getLogger(ClickEventGenerator.class);
		private static final String ANSI_RESET = "\u001B[0m";
		private static final String ANSI_RED = "\u001B[31m";
		private static final String ANSI_GREEN = "\u001B[32m";
		private static final String ANSI_YELLOW = "\u001B[33m";
		private static final String ANSI_BLUE = "\u001B[34m";

		private static final String KAFKA = Optional.ofNullable(System.getenv("DATAGEN_KAFKA")).orElse("kafka:9092");
		private static final String TOPIC = Optional.ofNullable(System.getenv("DATAGEN_TOPIC")).orElse("surveys");

		private static final String[] PHONE_PREFIXES = {"07"};
		private static final String[] QUESTION_TYPES = {"yes_no", "qcm", "scale", "comment"};
		private static final Random random = new Random();

		public static void main(String[] args) {
			Properties properties = new Properties();
			properties.put("bootstrap.servers", KAFKA);
			properties.put("key.serializer", StringSerializer.class.getName());
			properties.put("value.serializer", SurveyInputSerializer.class.getName());

			KafkaProducer<String, SurveyInput> producer = new KafkaProducer<>(properties);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				LOG.info("Shutting down");
				producer.close();
			}));

			// Start the data generation process
			generateAndSendData(producer);

			List<SurveyInput> surveys = new ArrayList<>();

			for (int i = 0; i < 100; i++) {
				SurveyInput survey = generateSurveyInput();
				surveys.add(survey);

				// Process the survey here if needed
				// For example, printing out each survey
				System.out.println(survey);
			}

		}

		private static void generateAndSendData(KafkaProducer<String, SurveyInput> producer) {
			try {
				while (true) {
					SurveyInput surveyInput = generateSurveyInput();
					ProducerRecord<String, SurveyInput> record = new ProducerRecord<>(TOPIC, UUID.randomUUID().toString(), surveyInput);
					producer.send(record);

					LOG.info(ANSI_GREEN + "Generated SurveyInput: " + ANSI_RESET + surveyInput);

					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				LOG.error(ANSI_RED + "Data generation was interrupted" + ANSI_RESET, e);
				Thread.currentThread().interrupt();
			}
		}

		private static SurveyInput generateSurveyInput() {
			SurveyInput surveyInput = new SurveyInput();
			surveyInput.setSurveyId(UUID.randomUUID().toString());
			surveyInput.setSurveyRequestId(UUID.randomUUID().toString());
			surveyInput.setSurveyCode(UUID.randomUUID().toString());
			surveyInput.setSurveySubject("Sample Survey Subject");
			surveyInput.setSurveyTimeStamp(LocalDateTime.now());

			Question question = new Question();
			question.setId(UUID.randomUUID().toString());
			question.setName("Sample Question Name");
			question.setQuestionCode(UUID.randomUUID().toString());
			String questionType = QUESTION_TYPES[random.nextInt(QUESTION_TYPES.length)];
			question.setType(questionType);

			boolean isNPS = "scale".equals(questionType);
			if (isNPS) {
				question.setDetractorScaleMinIncluded(0);
				question.setDetractorScaleMaxIncluded(6);
				question.setPassiveScaleMinIncluded(7);
				question.setPassiveScaleMaxIncluded(8);
				question.setPromoterScaleMinIncluded(9);
				question.setPromoterScaleMaxIncluded(10);
			}

			Participant participant = new Participant();
			participant.setId(UUID.randomUUID().toString());
			participant.setPhoneNumber(PHONE_PREFIXES[0] + (random.nextInt(9000000) + 1000000));

			Answer answer = new Answer();
			answer.setId(UUID.randomUUID().toString());
			answer.setText(isNPS ? String.valueOf(random.nextInt(11)) : "Sample Answer");
			answer.setTimestamp(LocalDateTime.now());

			surveyInput.setQuestion(question);
			surveyInput.setParticipant(participant);
			surveyInput.setAnswer(answer);

			return surveyInput;
		}
	}
