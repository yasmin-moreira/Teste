import os
from flask import Flask, request, jsonify
from flask_cors import CORS
from PIL import Image

import google.generativeai as genai


app = Flask(__name__)
CORS(app)

genai.configure(api_key="AIzaSyAjpukjqkJZVjFUnCl81TxAQm-yr4_EcZo")


@app.route('/upload/resum', methods=['POST'])
def upload_fileResum():
    if 'image' not in request.files:
        return jsonify({"error": "No file uploaded"}), 400
    
    file = request.files['image']

    if file.filename == '':
        return jsonify({"error": "Empty filename"}), 400
    

    file_path = os.path.join("uploads", file.filename)
    os.makedirs("uploads", exist_ok=True)
    file.save(file_path)

    image = Image.open(file_path)

    # Enviar a imagem ao GenAI (exemplo: como upload_file())
    with open(file_path, "rb") as img_file:
        

    # Geração de resposta (adapte o prompt para seu caso)
        model = genai.GenerativeModel(model_name="gemini-1.5-flash")
        chat_session = model.start_chat(history=[{"role": "user", "parts": [genai.upload_file(img_file, mime_type="image/jpeg")]}])
        response = chat_session.send_message("Resuma o texto da imagem a cima")

    print(response.text)

    return jsonify({"text": response.text}), 200


@app.route('/upload', methods=['POST'])
def upload_file():
    if 'image' not in request.files:
        return jsonify({"error": "No file uploaded"}), 400
    
    file = request.files['image']

    if file.filename == '':
        return jsonify({"error": "Empty filename"}), 400
    

    file_path = os.path.join("uploads", file.filename)
    os.makedirs("uploads", exist_ok=True)
    file.save(file_path)

    image = Image.open(file_path)

    # Enviar a imagem ao GenAI (exemplo: como upload_file())
    with open(file_path, "rb") as img_file:
        

    # Geração de resposta (adapte o prompt para seu caso)
        model = genai.GenerativeModel(model_name="gemini-1.5-flash")
        chat_session = model.start_chat(history=[{"role": "user", "parts": [genai.upload_file(img_file, mime_type="image/jpeg")]}])
        response = chat_session.send_message("Transcreva o que foi escrito acima")

    print(response.text)

    return jsonify({"text": response.text}), 200


if __name__ == '__main__':
    app.run(debug=True, port=5000)
