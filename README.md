# Personal Spending Tracker

[![Java CI with Maven](https://github.com/moein373/personal-spending-tracker/actions/workflows/maven.yml/badge.svg)](https://github.com/moein373/personal-spending-tracker/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=moein373_personal-spending-tracker2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=moein373_personal-spending-tracker2)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=moein373_personal-spending-tracker2&metric=coverage)](https://sonarcloud.io/summary/new_code?id=moein373_personal-spending-tracker2)

Personal Spending Tracker is a Java desktop application developed for the Advanced Techniques and Tools for Software Development course.

The application manages personal expenses through a Swing interface. An expense contains a description, amount, category and date, and can be added, updated, deleted and displayed. Data is stored in MongoDB.

## Technologies

- Java 17
- Maven
- Java Swing
- MongoDB
- JUnit 4
- Mockito
- AssertJ Swing
- Testcontainers
- Docker
- JaCoCo
- PIT
- GitHub Actions
- SonarCloud

## Project structure

The application is divided into a small set of layers:

```text
UI
 |
Controller
 |
Service
 |
Repository
 |
MongoDB