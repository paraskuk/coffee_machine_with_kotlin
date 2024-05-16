fun main() {
    val coffeeMachine = CoffeeMachine()

    while (true) {
        val input = readLine()!!
        if (input == "exit") break
        coffeeMachine.process(input)
    }
}

class CoffeeMachine {
    private var water = 400
    private var milk = 540
    private var coffeeBeans = 120
    private var cups = 9
    private var money = 550

    private var state: State = State.ChoosingAction

    fun process(input: String) {
        when (state) {
            State.ChoosingAction -> handleAction(input)
            State.ChoosingCoffeeType -> handleCoffeeType(input)
            State.FillingWater -> fillWater(input)
            State.FillingMilk -> fillMilk(input)
            State.FillingCoffeeBeans -> fillCoffeeBeans(input)
            State.FillingCups -> fillCups(input)
        }
    }

    private fun handleAction(input: String) {
        when (input) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                state = State.ChoosingCoffeeType
            }
            "fill" -> {
                println("Write how many ml of water you want to add: ")
                state = State.FillingWater
            }
            "take" -> take()
            "remaining" -> printState()
        }
    }

    private fun handleCoffeeType(input: String) {
        when (input) {
            "1" -> makeCoffee(250, 0, 16, 4)
            "2" -> makeCoffee(350, 75, 20, 7)
            "3" -> makeCoffee(200, 100, 12, 6)
            "back" -> state = State.ChoosingAction
        }
        if (state != State.ChoosingAction) {
            println("Write action (buy, fill, take, remaining, exit): ")
            state = State.ChoosingAction
        }
    }

    private fun fillWater(input: String) {
        water += input.toInt()
        println("Write how many ml of milk you want to add: ")
        state = State.FillingMilk
    }

    private fun fillMilk(input: String) {
        milk += input.toInt()
        println("Write how many grams of coffee beans you want to add: ")
        state = State.FillingCoffeeBeans
    }

    private fun fillCoffeeBeans(input: String) {
        coffeeBeans += input.toInt()
        println("Write how many disposable cups you want to add: ")
        state = State.FillingCups
    }

    private fun fillCups(input: String) {
        cups += input.toInt()
        println("Write action (buy, fill, take, remaining, exit): ")
        state = State.ChoosingAction
    }

    private fun take() {
        println("I gave you \$$money")
        money = 0
        println("Write action (buy, fill, take, remaining, exit): ")
    }

    private fun makeCoffee(waterNeeded: Int, milkNeeded: Int, coffeeNeeded: Int, price: Int) {
        when {
            water < waterNeeded -> println("Sorry, not enough water!")
            milk < milkNeeded -> println("Sorry, not enough milk!")
            coffeeBeans < coffeeNeeded -> println("Sorry, not enough coffee beans!")
            cups <= 0 -> println("Sorry, not enough disposable cups!")
            else -> {
                water -= waterNeeded
                milk -= milkNeeded
                coffeeBeans -= coffeeNeeded
                cups--
                money += price
                println("I have enough resources, making you a coffee!")
            }
        }
    }

    fun printState() {
        println("\nThe coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$coffeeBeans g of coffee beans")
        println("$cups disposable cups")
        println("\$$money of money\n")
    }

    enum class State {
        ChoosingAction,
        ChoosingCoffeeType,
        FillingWater,
        FillingMilk,
        FillingCoffeeBeans,
        FillingCups
    }
}
