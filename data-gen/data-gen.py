import time
import json
from uuid import uuid4
from confluent_kafka import Producer
import random

jsonString1 = """ {"surveyId":"cbe7ff1f-ece5-413b-8402-94ff909e4d47","surveyRequestId":"a766e534-cf6f-4fa7-afef-9e255861ce03","surveyCode":"ebf36711-8734-459c-94d7-d7f1465dd111","surveySubject":"Sample Survey Subject","surveyTimestamp":"2024-02-08T05:41:35.797132100","question":{"id":"deb3c74a-b059-4e85-a754-d6caee92164b","name":"Sample Question Name","questionCode":"7bad89d0-10c2-43b8-98a5-c8265ab3a2ca","type":"scale","detractorScaleMinIncluded":0,"detractorScaleMaxIncluded":6,"detractorScaleStep":1,"passiveScaleMinIncluded":7,"passiveScaleMaxIncluded":8,"passiveScaleStep":1,"promoterScaleMinIncluded":9,"promoterScaleMaxIncluded":10,"promoterScaleStep":1},"participant":{"id":"36ed6eca-404e-43eb-9c96-6f1dd409f0fa","phoneNumber":"07123456789"},"answer":{"id":"fbc58a2c-62b4-4615-a24c-fde736f3701d","text":"9","timestamp":"2024-02-08T05:41:35.800131300"}} """
jsonv1 = jsonString1.encode()

def delivery_report(errmsg, msg):
	"""
	Reports the Failure or Success of a message delivery.
	Args:
		errmsg (KafkaError): The Error that occurred while message producing.
		msg (Actual message): The message that was produced.
	Note:
		In the delivery report callback the Message.key() and Message.value()
		will be the binary format as encoded by any configured Serializers and
		not the same object that was passed to produce().
		If you wish to pass the original object(s) for key and value to delivery
		report callback we recommend a bound callback or lambda where you pass
		the objects along.
	"""
	if errmsg is not None:
		print("Delivery failed for Message: {} : {}".format(msg.key(), errmsg))
		return
	print('Message: {} successfully produced to Topic: {} Partition: [{}] at offset {}'.format(
		msg.key(), msg.topic(), msg.partition(), msg.offset()))

kafka_topic_name = "survey-inputs"
#Change your Kafka Topic Name here. For this Example: lets assume our Kafka Topic has 3 Partitions==> 0,1,2
#And We are producing messages uniformly to all partitions.
#We are sending the message as ByteArray.
#If We want read the same message from a Java Consumer Program 
#We can configure KEY_DESERIALIZER_CLASS_CONFIG = ByteArrayDeserializer.class
# and VALUE_DESERIALIZER_CLASS_CONFIG = ByteArrayDeserializer.class

mysecret = "yourjksPassword"
#you can call remote API to get JKS password instead of hardcoding like above

print("Starting Kafka Producer") 
conf = {
	'bootstrap.servers' : 'localhost:9092, localhost:9094, localhost:9094',
}
		
print("connecting to Kafka topic...")
producer1 = Producer(conf)

# Trigger any available delivery report callbacks from previous produce() calls
producer1.poll(0)

while True:
  try:
    # Asynchronously produce a message, the delivery report callback
    # will be triggered from poll() above, or flush() below, when the message has
    # been successfully delivered or failed permanently.
    d = json.loads(jsonString1)
    d['answer']['text'] = str(random.randint(1, 10))
    producer1.produce(topic=kafka_topic_name, key=str(uuid4()), value=json.dumps(d), on_delivery=delivery_report)
    
    # Wait for any outstanding messages to be delivered and delivery report
    # callbacks to be triggered.
    producer1.flush()
    time.sleep(1)
  except Exception as ex:
    print("Exception happened :",ex)
	
print("\n Stopping Kafka Producer")
