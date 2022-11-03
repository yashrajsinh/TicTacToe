package com.yashrajsinh.tictactoe

import android.net.ipsec.ike.TunnelModeChildSessionParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.yashrajsinh.tictactoe.databinding.ActivityMainBinding
import java.text.DateFormatSymbols

class MainActivity : AppCompatActivity() {

    //Enum for player' turn
    enum class Turn {
        NOUGHT,
        CROSS
    }

    //Var for storing first and current turn
    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    //Variables to keep track of each user's winning
    private var crossScore = 0
    private var noughtScore = 0

    //List for adding buttons
    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    //TODO: Method for intialization
    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    //When one button is tapped
    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)
        //Check for NOUGHT
        if(checkForWinner(NOUGHT))
        {
            noughtScore++
            result("Noughts Win! ")
        }

        //Check for CROSS
        if(checkForWinner(CROSS))
        {
            crossScore++
            result("Crosses Win! ")
        }

        //For Tie or Draw
        if (fullBoard()) {
            result("Draw")
        }
    }

    //TODO: Method to check for a winner
    private fun checkForWinner(s: String): Boolean {

        //Horizontal Winning
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true

        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true

        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //Vertical Winning
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true

        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true

        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //Diagonal Winning
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true

        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true


            return false
    }

    private fun match(button:Button,symbol: String): Boolean = button.text == symbol

    //TODO: Method for result display
    private fun result(title: String) {
        val msg = "\nNoughts $noughtScore\n\nCrosses $crossScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Reset")
            { _, _ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    //TODO: Method to reset the game
    private fun resetBoard() {
        for (button in boardList) {
            button.text = ""
        }
        if (firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()
    }

    //TODO: To check if board is full
    private fun fullBoard(): Boolean {
        for (button in boardList) {
            if (button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return
        //Keeping track of current player
        if (currentTurn == Turn.NOUGHT) {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        } else if (currentTurn == Turn.CROSS) {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    //TODO: Method to show player's turn on text view
    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if (currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.txtPlayerName.text = turnText

    }
    //TODO:companion object because using those values often
    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}