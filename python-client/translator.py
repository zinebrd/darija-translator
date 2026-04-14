import requests
import base64

API_URL = "http://localhost:8080/darija-translator/api/translate"
USERNAME = "admin"
PASSWORD = "admin123"

def translate_to_darija(text):
    credentials = base64.b64encode(f"{USERNAME}:{PASSWORD}".encode()).decode()
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Basic {credentials}"
    }
    response = requests.post(API_URL, json={"text": text}, headers=headers)
    
    if response.status_code == 200:
        return response.json().get("translation", "No translation found")
    else:
        return f"Error {response.status_code}: {response.text}"

def main():
    print("🌍 Darija Translator - Python Client")
    print("=====================================")
    while True:
        text = input("\nEnter English text (or 'quit' to exit): ").strip()
        if text.lower() == "quit":
            print("Goodbye! / بسلامة")
            break
        if text:
            print("Translating...")
            result = translate_to_darija(text)
            print(f"Darija: {result}")

if __name__ == "__main__":
    main()