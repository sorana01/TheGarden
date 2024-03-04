# PlantID Mobile App

## Overview
The PlantID Mobile App is a powerful tool designed to help plant enthusiasts identify various plant species with ease. By leveraging the capabilities of the plant.id API, users can simply scan a plant using their mobile phone's camera, and the app will provide detailed information about the plant species. This project is built using a robust tech stack, including an Android Project for the mobile app, Java for core development, IntelliJ IDEA for efficient coding, and Spring Boot for the backend services.

## Features
- **Plant Identification:** Quickly identify plants from images using the plant.id API.
- **User Accounts:** Users can create and manage their accounts, allowing them to keep track of their plant scans.
- **Database Registration:** Every plant scanned can be registered into a database with its details, enabling users to build their own virtual herbarium.

## Getting Started

### Prerequisites
- Android Studio or IntelliJ IDEA
- JDK 8 or later
- Spring Boot
- MySQL (for the backend database)

### Installation
1. **Clone the repository**
   ```sh
   git clone https://github.com/sorana01/TheGarden
   
2. **Set up the backend**
- Navigate into the `backendApp` directory and open it as a project in IntellijIDEA.
 - Update src/main/resources/application.properties with your PostgreSQL settings.
 - Ensure Maven is installed and configured in your IDE to manage project dependencies.
 - Run BackendAppApplication class.

3. **Obtain a plant.id API Key**
  - Navigate to their official website, create an account to get api trial access and then request an api key.
  - Go to `mobileApp` directory and open it in AndroidStudio.
  - In PlantIDApi interface modify with your api key.

Now you're free to run MainActivity class, part of mobileApp, after you run the backend and see what happens!

This is a small tutorial to scan the plant correctly!
![tutorial_gif](https://github.com/sorana01/TheGarden/assets/74464853/9daa6f32-0f4d-471a-a5c6-ed5e320f979b)



