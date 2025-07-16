/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.internship.project;

//import com.internship.project.data.SurveyInput;
//import com.internship.project.survey.SurveyInputSource;
//import com.internship.project.survey.SurveyInputTimeAssigner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.internship.project.data.SurveyInput;
import com.internship.project.survey.SurveyInputSource;
import com.internship.project.survey.SurveyInputTimeAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
//import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

//import org.apache.flink.streaming.api.windowing.time.Time;
/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {



	public static void main(String[] args) throws Exception {
		// Sets up the execution environment, which is the main entry point
		// to building Flink applications.
//		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//		env.getConfig().setAutoWatermarkInterval(5000L);
//
//		DataStream<SurveyInput> surveyReadings = env
//			.addSource(new SurveyInputSource())
//			.assignTimestampsAndWatermarks(new SurveyInputTimeAssigner(Time.seconds(5000L)));
//
//		surveyReadings.print();
//		env.execute("Flink App Demo");

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
			.setBootstrapServers("localhost:9092, localhost:9093, localhost:9094")
			.setTopics("survey-inputs")
			.setGroupId("flink-survey-inputs-grp-id")
			.setStartingOffsets(OffsetsInitializer.earliest())
			.setValueOnlyDeserializer(new SimpleStringSchema())
			.build();
		DataStream<String> surveyReadings = env
			.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

		DataStream<String> detractorsReadings = surveyReadings.filter(
			(FilterFunction<String>) value -> {
				Gson gson = new GsonBuilder().create();
				SurveyInput surveyInput = gson.fromJson(value, SurveyInput.class);
				System.out.println(surveyInput);
				return Integer.parseInt(surveyInput.answer.text) >= 0 && Integer.parseInt(surveyInput.answer.text) <= 6;
			}
		);

		DataStream<String> passiveReadings = surveyReadings.filter(
			(FilterFunction<String>) value -> {
				Gson gson = new GsonBuilder().create();
				SurveyInput surveyInput = gson.fromJson(value, SurveyInput.class);
				System.out.println(surveyInput);
				return Integer.parseInt(surveyInput.answer.text) >= 7 && Integer.parseInt(surveyInput.answer.text) <= 8;
			}
		);

		DataStream<String> promoterReadings = surveyReadings.filter(
			(FilterFunction<String>) value -> {
				Gson gson = new GsonBuilder().create();
				SurveyInput surveyInput = gson.fromJson(value, SurveyInput.class);
				System.out.println(surveyInput);
				return Integer.parseInt(surveyInput.answer.text) >= 9 && Integer.parseInt(surveyInput.answer.text) <= 10;
			}
		);

		KafkaSink<String> detractorKafkaSink = KafkaSink.<String>builder()
			.setBootstrapServers("localhost:9092, localhost:9093, localhost:9094")
			.setRecordSerializer(
				KafkaRecordSerializationSchema.builder()
					.setTopic("detractor-records")
					.setValueSerializationSchema(new SimpleStringSchema())
					.build()
			)
			.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
			.build();

		KafkaSink<String> promoterKafkaSink = KafkaSink.<String>builder()
			.setBootstrapServers("localhost:9092, localhost:9093, localhost:9094")
			.setRecordSerializer(
				KafkaRecordSerializationSchema.builder()
					.setTopic("promoter-records")
					.setValueSerializationSchema(new SimpleStringSchema())
					.build()
			)
			.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
			.build();
		KafkaSink<String> passiveKafkaSink = KafkaSink.<String>builder()
			.setBootstrapServers("localhost:9092, localhost:9093, localhost:9094")
			.setRecordSerializer(
				KafkaRecordSerializationSchema.builder()
					.setTopic("passive-records")
					.setValueSerializationSchema(new SimpleStringSchema())
					.build()
			)
			.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
			.build();
		detractorsReadings.sinkTo(detractorKafkaSink);
		promoterReadings.sinkTo(promoterKafkaSink);
		passiveReadings.sinkTo(passiveKafkaSink);
		env.execute("Flink App Demo");
	}
}
