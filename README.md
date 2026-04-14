# 🌍🇲🇦 Darija Translator — LLM-Powered RESTful Web Service

A full-stack translation service that translates English text to Moroccan Darija Arabic using Google Gemini AI, built with Jakarta EE and deployed on Apache TomEE.

## Project Structure
darija-translator/          # Java REST API (Maven)
chrome-extension/           # Chrome Extension (Manifest V3)
php-client/                 # PHP Web Client
python-client/              # Python CLI Client
react-native-client/        # React Native Mobile App

## Features

- REST API built with Jakarta JAX-RS
- Google Gemini AI for English → Darija translation
- Basic Authentication security
- Chrome Extension with side panel and auto-paste
- Right-click context menu integration
- Text-to-speech (Read Aloud) feature
- PHP, Python, and React Native clients

## Setup & Run

### Prerequisites
- Java 22+
- Maven 3.9+
- Apache TomEE 10.1.4
- Google Gemini API Key

### 1. Configure API Key
In `src/main/java/com/translator/darijatranslator/GeminiService.java`:
```java
private static final String API_KEY = "YOUR_GEMINI_API_KEY";
```

### 2. Build & Deploy
```bash
mvn clean package
cp target/darija-translator-1.0-SNAPSHOT.war /path/to/tomee/webapps/darija-translator.war
```

### 3. Start TomEE
```bash
/path/to/tomee/bin/startup.sh
```

### 4. Test with cURL
```bash
curl -X POST http://localhost:8080/darija-translator/api/translate \
  -H "Content-Type: application/json" \
  -u admin:admin123 \
  -d '{"text": "Hello, how are you?"}'
```

## Authentication
Basic Authentication is required for all API calls.
- Username: `admin`
- Password: `admin123`

## Clients

### Chrome Extension
Load `chrome-extension/` folder in Chrome via `chrome://extensions` → Developer mode → Load unpacked.

### PHP Client
```bash
cd php-client
php -S localhost:8081
```
Open `http://localhost:8081`

### Python Client
```bash
cd python-client
pip install requests
python3 translator.py
```

### React Native
```bash
cd react-native-client
npm install
npm run web
```

## Tech Stack
- **Backend:** Java 22, Jakarta EE 11, JAX-RS, TomEE 10
- **AI:** Google Gemini 2.5 Flash API
- **Frontend:** Chrome Extension (Manifest V3), PHP, Python, React Native
- **Security:** Jakarta Basic Authentication

## Author
Zineb — Al Akhawayn University