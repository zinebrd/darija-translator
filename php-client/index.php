<?php
$translation = "";
$error = "";

if ($_SERVER["REQUEST_METHOD"] === "POST" && !empty($_POST["text"])) {
    $text = $_POST["text"];
    $url = "http://localhost:8080/darija-translator/api/translate";
    $credentials = base64_encode("admin:admin123");

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode(["text" => $text]));
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        "Content-Type: application/json",
        "Authorization: Basic " . $credentials
    ]);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

    if ($httpCode === 200) {
        $data = json_decode($response, true);
        $translation = $data["translation"] ?? "No translation found";
    } else {
        $error = "Error: HTTP $httpCode - $response";
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Darija Translator - PHP Client</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }
        h1 { color: #2c3e50; text-align: center; }
        textarea { width: 100%; height: 120px; padding: 10px; font-size: 14px; border: 1px solid #ddd; border-radius: 8px; }
        button { width: 100%; padding: 12px; background: #3498db; color: white; border: none; border-radius: 8px; font-size: 16px; cursor: pointer; margin-top: 10px; }
        button:hover { background: #2980b9; }
        .result { margin-top: 20px; padding: 15px; background: #f9f9f9; border: 1px solid #ddd; border-radius: 8px; font-size: 18px; direction: rtl; text-align: right; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
    <h1>🌍🇲🇦 Darija Translator</h1>
    <form method="POST">
        <label>Enter English Text:</label><br><br>
        <textarea name="text" placeholder="Type something in English..."><?= htmlspecialchars($_POST["text"] ?? "") ?></textarea>
        <button type="submit">Translate to Darija</button>
    </form>
    <?php if ($translation): ?>
        <div class="result">
            <strong>Translation:</strong><br><?= htmlspecialchars($translation) ?>
        </div>
    <?php endif; ?>
    <?php if ($error): ?>
        <div class="error"><?= htmlspecialchars($error) ?></div>
    <?php endif; ?>
</body>
</html>