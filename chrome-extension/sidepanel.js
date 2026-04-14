const API_URL = "http://localhost:8080/darija-translator/api/translate";
const USERNAME = "admin";
const PASSWORD = "admin123";
const credentials = btoa(`${USERNAME}:${PASSWORD}`);

async function translate(text) {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Basic ${credentials}`
    },
    body: JSON.stringify({ text })
  });
  const data = await response.json();
  return data.translation || data.error;
}

document.getElementById("translateBtn").addEventListener("click", async () => {
  const text = document.getElementById("inputText").value.trim();
  if (!text) return;
  document.getElementById("outputText").innerText = "Translating...";
  const result = await translate(text);
  document.getElementById("outputText").innerText = result;
  document.getElementById("readAloudBtn").style.display = "block";
});

document.getElementById("readAloudBtn").addEventListener("click", () => {
  const text = document.getElementById("outputText").innerText;
  const utterance = new SpeechSynthesisUtterance(text);
  utterance.lang = "ar-MA";
  speechSynthesis.speak(utterance);
});

// Auto-paste selected text from context menu
chrome.storage.local.get("selectedText", (data) => {
    if (data.selectedText) {
      document.getElementById("inputText").value = data.selectedText;
      chrome.storage.local.remove("selectedText");
      // Auto translate
      document.getElementById("translateBtn").click();
    }
  });