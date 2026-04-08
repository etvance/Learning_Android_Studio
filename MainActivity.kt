package com.example.hangmanapp
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val word = "kotlin"
    private var remainingGuesses = 6
    private val guessedLetters = mutableSetOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wordText = findViewById<TextView>(R.id.wordText)
        val input = findViewById<EditText>(R.id.letterInput)
        val button = findViewById<Button>(R.id.guessButton)
        val image = findViewById<ImageView>(R.id.hangmanImage)

        updateWord(wordText)
        updateImage(image)

        button.setOnClickListener {
            val guess = input.text.toString().lowercase()

            if (guess.length != 1) return@setOnClickListener

            val letter = guess[0]
            input.text.clear()

            if (!guessedLetters.contains(letter)) {
                guessedLetters.add(letter)
                if (!word.contains(letter)) {
                    remainingGuesses--
                }
            }

            updateWord(wordText)
            updateImage(image)

            if (hasWon()) {
                wordText.text = "🎉 You Won! Word: $word"
                button.isEnabled = false
            } else if (remainingGuesses == 0) {
                wordText.text = "💀 You Lost! Word: $word"
                button.isEnabled = false
            }
        }
    }

    private fun updateWord(textView: TextView) {
        val display = word.map {
            if (it in guessedLetters) it else '_'
        }.joinToString(" ")
        textView.text = display
    }

    private fun updateImage(image: ImageView) {
        val wrongGuesses = 6 - remainingGuesses

        val imageRes = when (wrongGuesses) {
            0 -> R.drawable.hangman0
            1 -> R.drawable.hangman1
            2 -> R.drawable.hangman2
            3 -> R.drawable.hangman3
            4 -> R.drawable.hangman4
            5 -> R.drawable.hangman5
            6 -> R.drawable.hangman6
            else -> R.drawable.hangman0
        }

        image.setImageResource(imageRes)
    }

    private fun hasWon(): Boolean {
        return word.all { it in guessedLetters }
    }
}
