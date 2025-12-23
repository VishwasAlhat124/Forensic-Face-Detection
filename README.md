🧠 Forensic Face Detection System
📌 Overview

The Forensic Face Detection System is a Java-based application designed to assist law enforcement and forensic investigations by matching forensic sketches with real facial images. The system leverages AWS Rekognition for face analysis and Amazon S3 for secure image storage, enabling accurate and efficient facial recognition from sketches.

🎯 Objective

Convert and analyze forensic sketches for facial recognition

Match sketches against a database of real face images

Assist investigators in identifying potential suspects quickly and accurately

🧩 Project Scope

The application allows users to upload forensic sketches and compare them with stored facial images using cloud-based face recognition services. It focuses on accuracy, scalability, and real-world forensic applicability.

🏗️ System Architecture

The project follows a modular architecture:

UI Layer (Java Desktop Application)
Handles user interaction, sketch upload, and result display.

Service Layer
Processes images, communicates with AWS services, and handles recognition logic.

Cloud Layer (AWS Services)

AWS Rekognition – Facial analysis and matching

Amazon S3 – Secure storage for sketches and facial images

🛠️ Technology Stack

Language: Java

Application Type: Java Desktop Application

Cloud Services:

AWS Rekognition

Amazon S3

Build Tool: Maven

IDE: Eclipse / IntelliJ

AWS SDK: AWS SDK for Java

🚀 Key Features

Upload forensic sketches for analysis

Store and manage facial images securely in AWS S3

Perform face matching using AWS Rekognition

Display similarity scores for identified matches

Scalable cloud-based recognition system

📦 Functional Modules

Sketch Upload Module

Image Storage Module (S3)

Face Recognition Module (AWS Rekognition)

Match Result & Similarity Analysis Module

🔐 Security & Compliance

Secure cloud storage for sensitive forensic images

Access control via AWS IAM roles and permissions

No hard-coded credentials (uses environment variables)

📌 Use Cases

Law enforcement agencies identifying suspects from sketches

Forensic departments assisting investigations

Research and academic projects in face recognition

🔮 Future Enhancements

Support for real-time face recognition

Integration with police databases

AI-based sketch enhancement before recognition

Web-based interface for remote access

📄 License

This project is intended for educational and research purposes.

If you want, I can also:
✔ Add setup & AWS configuration steps
✔ Add screenshots / demo workflow
✔ Convert this into a resume-ready project description
✔ Create a system diagram section
