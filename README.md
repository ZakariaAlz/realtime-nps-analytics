![Project Banner](assets/header.png)


<!--
  📄 Licensed under the Apache License, Version 2.0.
  See LICENSE file for details.
-->

# Real-Time NPS Analysis Solution 🚀

Hey there! 👋 This was my very first data engineering project, It streams customer feedback, calculates Net Promoter Score (NPS) on the fly, and shows it in a live dashboard. It was my first data-engineering adventure, built with my friend Aymen Benniou—may he rest in peace. We used Apache Kafka to collect feedback events, Apache Flink to process them, and Grafana to display the results. Everything runs in containers so you can try it on your machine in minutes. Give it a try and let me know what you think! 😊


---

## 📖 Overview

Leveraging Apache Kafka for seamless event streaming, Apache Flink for meticulous data processing, and container technologies like Docker or Podman for effortless deployment, our solution calculates NPS in real-time and visualizes this data through an intuitive dashboard. This enables immediate actions to enhance customer satisfaction.

## 🛠 Technologies & Tools

Our solution is powered by a robust stack designed for efficiency and scalability:

| Technology      | Description                                                                                   |
|-----------------|-----------------------------------------------------------------------------------------------|
| Apache Kafka    | For building real-time data pipelines and streaming apps.                                     |
| Apache Flink    | For processing streaming data with ease.                                                      |
| Docker/Podman   | To containerize and manage application deployment.                                            |
| Python          | For scripting data generation and processing tasks.                                           |
| Java            | For developing high-performance backend services.                                             |
| Maven           | For managing project build, reporting, and documentation from a central piece of information. |
| Grafana         | For dynamic and interactive data visualization.                                               |
| Prometheus      | For monitoring our streaming architecture with precision.                                     |
| AKHQ            | A Kafka GUI for exploring topics, viewing data, and managing configurations easily.           |
| IntelliJ IDEA   | Recommended IDE for developing and managing the project with efficient Java support.         |

## 📖 What Is This?

A plug-and-play pipeline that:

1. **Streams** raw customer feedback via **Apache Kafka**  
2. **Processes** events in real-time with **Apache Flink**  
3. **Exposes** metrics to **Prometheus**  
4. **Visualizes** NPS and trends in **Grafana**  
5. **Manages** everything with **Docker**/**Podman**

This is Perfect if you want hands-on experience with modern streaming and monitoring tools as somebody who's just starting to learn Data Stream Processing.

## 🚀 Getting Started

So First, Start exploring by setting up the project locally:

### Prerequisites

You're gonna need to install these dependencies, you could work with Docker/Podman Desktop but you could also work under WSL2.

- **Docker/Podman:** Follow the official guides to install [Docker](https://docs.docker.com/get-docker/) or [Podman](https://podman.io/getting-started/installation) on your system.
- **Java:** Ensure you have Java installed for backend development. [Download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **Maven:** Needed for building and managing the project. [Download Maven](https://maven.apache.org/download.cgi).
- **IntelliJ IDEA:** Recommended IDE for an enhanced development experience. [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

### Installation & Running

1. **Clone this repository**

```bash
git clone https://github.com/ZakariaAlz/realtime-nps-analytics.git
cd realtime-nps-analytics
```

2. **Install Python dependencies**
   
```python
pip install -r requirements.txt
```

3. **Launch Docker Containers**
   
Now you’re ready to launch the full stack.

You don’t need to merge all of those files by hand — Docker Compose (and Podman Compose) will happily load as many -f files as you give it. From the folder where all your YAML lives (e.g. nps-calculator-stream/docker), just run : 

```bash
docker-compose \
  -f akhq.yaml \
  -f cp-kafka-brokers.yaml \
  -f cp-zookeeper.yaml \
  -f flink.yaml \
  -f grafana.yaml \
  -f prometheus.yaml \
  up -d
```

Or, if you prefer working with Podman:

```bash
podman-compose \
  -f akhq.yaml \
  -f cp-kafka-brokers.yaml \
  -f cp-zookeeper.yaml \
  -f flink.yaml \
  -f grafana.yaml \
  -f prometheus.yaml \
  up -d
```

What this does:

-f file1.yaml -f file2.yaml … lets Compose merge all those service definitions into one virtual stack

up -d will start every container in detached mode

After you run that, check with:

```bash
docker-compose ps   # or podman-compose ps
```

You’ll see Kafka, ZK, AKHQ, Flink, Prometheus and Grafana all up and running.

4. **Generate Data**
   
Next, generate some sample feedback data. Move into the data-gen directory, install the Python requirements, and run the generator script:

```bash
cd data-gen
pip install -r requirements.txt
python data-gen.py
cd ..
```

5.  **📊 Visualizing the Data**

## 📂 Project Structure
.
├── data-gen/                  # Python scripts to generate fake feedback
├── nps-calculator-stream/     # Flink job & Kafka connectors
│   ├── docker/                # Compose files for Kafka, ZK, Prometheus…
│   ├── src/                   # Java source code
│   └── pom.xml
├── docker-compose.yml         # Brings up Kafka, Flink, Prometheus, Grafana
└── README.md

Give the services a moment to start. When they’re ready, open your browser:

- Prometheus is available at http://localhost:9090 to monitor your Kafka and Flink metrics.

- Grafana lives at http://localhost:3000, access it to explore the real-time analytics dashboards. Default login is admin/admin; it's recommended to change these credentials. You’ll see your live NPS dashboard updating in real time.

Explore your Kafka clusters by navigating to http://localhost:8080. AKHQ provides a user-friendly GUI for your Kafka environment, allowing you to monitor topics, view data, and manage your setup efficiently.

## How It Works
Under the hood, Kafka gathers every piece of customer feedback as an event. Flink consumes those events, groups them into short time windows, and calculates the NPS score for each window. Flink then pushes metrics out to Prometheus, which stores time-series data. Grafana queries Prometheus to show you trends, scores, and charts at a glance. If you’re curious, all service definitions live in docker-compose.yml, and the Flink job code sits under nps-calculator-stream.

## Project Layout

- data-gen/: A small Python script that produces fake feedback messages.

- nps-calculator-stream/: Java code for the Flink job, along with Docker and Maven setup.

- docker-compose.yml: Brings up Kafka, Zookeeper, Flink, Prometheus, Grafana, and AKHQ.

- README.md: That’s this file—you’re reading it now!


## 🌐 Real-Time Dashboard
The heart of our solution is the real-time dashboard powered by Grafana, providing a live view of NPS scores and customer feedback trends. This dashboard is designed for immediate insight into customer satisfaction levels, enabling quick decision-making and proactive measures to improve service quality.

To access and customize the dashboard:

Navigate to http://localhost:3000.
Log in with the default or your customized credentials.
Explore the pre-configured NPS analytics panels or create your own for tailored insights.

Here's an overview of our Dashboard in Grafana : 
<p align="center">
<img src="https://github.com/Zakaria100000/Real-time-Data-Streaming-Application-for-Customer-Satisfaction/assets/93408719/0c30f75b-0c68-4bee-a3bb-e1cc4e625e3e">
</p>

## 📘 Further Documentation
Delve deeper into each technology with their official documentation, linked in the Technologies & Tools section.

## 🤝 Contributing

Feel free to fork the repo and add your ideas. If you fix a bug or add a feature, send a pull request and we’ll review it together. Every contribution helps make this project better—and helps others learn modern streaming techniques.

1 - Create a feature branch (git checkout -b feature/your-feature)

2 - Commit your changes (git commit -m "Add awesome feature")

3 - Push to your fork (git push origin feature/my-feature)

4 - Open a Pull Request and I’ll review it!

## 💬 Feedback
Raised an issue? Got ideas? I’m all ears—drop a comment in Issues or hop into Discussions.

Made with ❤️ by Zakaria Alz. If you find this useful, Please, feel free to ⭐ this repo if it helped you!

