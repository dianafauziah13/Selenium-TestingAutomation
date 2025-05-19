**🧪 Selenium & RestAssured API Automation Testing**
This repository contains automated testing scripts for RESTful API using RestAssured and optionally Selenium for web UI interaction, as part of a course assignment in Automation Testing.
📁 Project Structure
SELENIUM-TESTING/
│
├── .vscode/
│
├── demo/
│
├── src/
│   ├── main/
│   │   └── java/
│   │
│   └── test/
│       └── java/
│           └── restassured/
│               └── RestAssuredTest.java
│
├── target/
│
├── pom.xml
│
├── testing/   ← (kemungkinan folder ini bisa dihapus jika tidak digunakan)
│
└── README.md

🚀 Technologies Used

- Java 17
- Maven
- RestAssured
- Selenium WebDriver (optional for UI flow)
- TestNG (testing framework)
- JSON (payload format

✅ Prerequisites
Before you begin, ensure you have the following installed:

- Java 17+
- Maven
- IDE like IntelliJ IDEA or VSCode

▶️ Running the Tests
Run All Tests Using Maven:
mvn test

Run Specific Test Class:
mvn -Dtest=YourTestClassName test




