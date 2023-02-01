const PLAYER_IDS = ["PLAYER_1", "PLAYER_2"]
const BASE_URL = "http://localhost:8080"
const REQUEST_COMPLETED = 4
const STATUS_CODE_OK = 200

function startGame() {
    
    let xhr = new XMLHttpRequest()
    let url = BASE_URL+'/startGame'
    xhr.open("POST", url, true)

    xhr.onreadystatechange = function () {
        if (this.readyState == REQUEST_COMPLETED && this.status == STATUS_CODE_OK) {
            const gameContext = JSON.parse(this.responseText)
            showBoard()
            enableNextState(gameContext.board, gameContext.next_state) 
        }
    }
    xhr.send();

}

function play(playerNumber, pit) {
    let pitNumber = pit.substring(4)

    let xhr = new XMLHttpRequest()
    let url = BASE_URL+'/play/'+PLAYER_IDS[playerNumber-1]+'/'+pitNumber
    xhr.open("POST", url, true)

    xhr.onreadystatechange = function () {
        if (this.readyState == REQUEST_COMPLETED && this.status == STATUS_CODE_OK) {
            const gameContext = JSON.parse(this.responseText)
            enableNextState(gameContext.board, gameContext.next_state) 
        }
    }
    xhr.send()
}

function endGame() {

    let xhr = new XMLHttpRequest()
    let url = BASE_URL+'/endGame'
    xhr.open("POST", url, true)

    xhr.onreadystatechange = function () {
        if (this.readyState == REQUEST_COMPLETED && this.status == STATUS_CODE_OK) {
            const gameContext = JSON.parse(this.responseText)
            showWinner(gameContext.board)
            enableNextState(gameContext.board, gameContext.next_state) 
        }
    }
    xhr.send()
   
}

function enableNextState(board, nextState) {

    populateBoard(board)

    switch(nextState) {
        case "GAME_START":
          showStartButton()
          break;
        case "PLAYER_1_TURN":
          enablePlayerBoard(board, "PLAYER_1")
          disablePlayerBoard(board, "PLAYER_2")
          break;
        case "PLAYER_2_TURN":
          enablePlayerBoard(board, "PLAYER_2")
          disablePlayerBoard(board, "PLAYER_1")
          break;
        case "GAME_END":
          endGame(board)
          disablePlayerBoard(board, "PLAYER_1")  
          disablePlayerBoard(board, "PLAYER_2")
          break;
      }
}

function populateBoard(board) {

    for(id of PLAYER_IDS) {
        let playerBoard = board.find((item) => item.player == id)
        for (let i = 0; i < playerBoard.houses.length; i++) {
            document.querySelector("#HOUSES_"+id).querySelector("#pit-"+(i+1)).innerHTML = playerBoard.houses[i].seeds
        }
        document.querySelector("#STORE_"+id).innerHTML = playerBoard.store.seeds
    }

}

function enablePlayerBoard(board, playerID) {
    let playerBoard = board.find((item) => item.player == playerID)
    for (let i = 0; i < playerBoard.houses.length; i++) {
        document.querySelector("#HOUSES_"+playerID).querySelector("#pit-"+(i+1)).classList.remove("disabled-element")
    }
    document.querySelector("#STORE_"+playerID).classList.remove("disabled-element")
}

function disablePlayerBoard(board, playerID) {
    let playerBoard = board.find((item) => item.player == playerID)
    for (let i = 0; i < playerBoard.houses.length; i++) {
        document.querySelector("#HOUSES_"+playerID).querySelector("#pit-"+(i+1)).classList.add("disabled-element")
    }
    document.querySelector("#STORE_"+playerID).classList.add('disabled-element')
}

function showBoard() {
    document.querySelector("#game").classList.add("visible")
    document.querySelector("#start-game-button").classList.add("hidden")
    document.querySelector("#winner").classList.remove("visible")
}

function showStartButton() {
    document.querySelector("#start-game-button").classList.remove("hidden")
    document.querySelector("#start-game-button").classList.remove("centered")
}

function showWinner(board) {

    let p1FinalScore = board.find((item) => item.player == PLAYER_IDS[0]).store.seeds
    let p2FinalScore = board.find((item) => item.player == PLAYER_IDS[1]).store.seeds

    let message

    if(p1FinalScore > p2FinalScore) {
        message = "PLAYER 1 WINS!"
    } else if (p2FinalScore > p1FinalScore) {
        message = "PLAYER 2 WINS!"
    } else {
        message = "IT'S A DRAW!"
    }

    document.querySelector("#winner").innerHTML = message
    document.querySelector("#winner").classList.add("visible")

}