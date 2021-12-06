# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### [v1.1.0] - 2021-12-5
### Added
    - Added a player label above the hand tray to keep track visually of current player.
    - Added a button to show a link to the game rules.
    - Added a button to toggle if the game will end.
    - Added a button to save the current state of the game.
### Changed
    - Logic for merges now uses a depth first search in order to identify all effected tiles.
    - Made the end button text change when clicked to make it more apparent.
### Fixed
    - Players should no longer be able to play "Illegal" tiles.
    - Players are now forced to place a tile before their turn can end.
    - Players scores are now accurate to the values of their stocks.
    - Stock Market price is now properly displayed based on the size of the corporation.
    - Stock Market now accurately displays the exchanges of stocks.
    - Stock Market now accurately displays the size of the corporations.
    - Tiles that are connected but not associated with each other are now both consumed during merges. 
    - Tiles that are at the edge of the board are correctly checked for adjacent tiles.
    - Save game now actually saves the current state of the game.
    - Load game now actually loads the saved state of the game.
### Removed
    - Remove the sell button from the main game screen since players can only sell on merges and are automatically asked.

## [Unreleased]

## [1.0.0] - 2021-11-14
-Completed basic implementation of game logic and UI controls

## [v1.0.0] - 2021-11-14
-Completed basic implementation of game logic and UI controls
