# Connect 4 written in Kotlin with JavaFX (why not?)

## Build and run the project

```shell
mvn clean javafx:run
```

## Changes from the original requirements

I chose to allow players to click on a column to play instead of using a button at the top of the column, because I
think it's more intuitive.

I made a basic AI using Alpha-Beta algorithm, but it's not very smart since there is currently no heuristic for
non-terminal states.
