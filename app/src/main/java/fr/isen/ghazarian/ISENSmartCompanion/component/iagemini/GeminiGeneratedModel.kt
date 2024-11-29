package fr.isen.ghazarian.isensmartcompanion.component.iagemini


import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import fr.isen.ghazarian.isensmartcompanion.BuildConfig


val model = GenerativeModel(
    modelName = "gemini-1.5-flash-001",
    apiKey = BuildConfig.GEMINI_API_KEY,
    generationConfig = generationConfig {
        temperature = 0.15f
        topK = 32
        topP = 1f
        maxOutputTokens = 4096
    },
    safetySettings = listOf(
        SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
    )
)
// Fonction pour générer une réponse à partir d'un prompt
suspend fun generateText(questionandreponse: String): String {
    return try {
        val result = model.generateContent(questionandreponse)  // Assurez-vous d'avoir une méthode valide pour la génération de texte
        result.text ?: "Aucune réponse générée"
    } catch (e: Exception) {
        "Erreur : ${e.message}"
    }
}
