# Real-Time NPS Analysis Solution 🚀

Dive into the heart of customer satisfaction with our Real-Time Net Promoter Score (NPS) Analysis Solution. Developed at Telecom Djezzy, this project brings the cutting edge of Data/Event Stream Processing to your fingertips, enabling actionable insights from customer feedback, instantly.

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

## 🚀 Getting Started

Embark on your journey to real-time analytics by setting up the project locally:

### Prerequisites

- **Docker/Podman:** Follow the official guides to install [Docker](https://docs.docker.com/get-docker/) or [Podman](https://podman.io/getting-started/installation) on your system.
- **Java:** Ensure you have Java installed for backend development. [Download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **Maven:** Needed for building and managing the project. [Download Maven](https://maven.apache.org/download.cgi).
- **IntelliJ IDEA:** Recommended IDE for an enhanced development experience. [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

### Installation & Running

1. **Clone this repository**

git clone https://github.com/yourgithubusername/yourrepositoryname.git
cd yourrepositoryname

2. **Install Python dependencies** 

pip install -r requirements.txt

3. **Launch Docker Containers** 

Ensure Docker Desktop/ Podman is running and execute:

docker-compose up -d  # For Docker users
# or
podman-compose up -d  # For Podman users


This starts Kafka, Flink, Prometheus, Grafana, and AKHQ, etc.. 

4. **Generate Data**

Kickstart the data generation:

python data-gen.py

## 📊 Visualizing the Data

- Prometheus Monitoring:

Access Prometheus at http://localhost:9090 to monitor your Kafka and Flink metrics.

- Grafana Dashboards:

Visit http://localhost:3000 to explore the real-time analytics dashboards. Default login is admin/admin; it's recommended to change these credentials.

- AKHQ Interface:

Explore your Kafka clusters by navigating to http://localhost:8080. AKHQ provides a user-friendly GUI for your Kafka environment, allowing you to monitor topics, view data, and manage your setup efficiently.

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
Your contributions make our project grow and improve. Whether it's bug reports, feature suggestions, or code contributions, we welcome your input wholeheartedly.

## 💬 Feedback
Got feedback? I'd love to hear it! Please create an issue for any suggestions or feedback you have.
 

