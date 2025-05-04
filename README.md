# Scoreboard-lib

This library provides a simple in-memory scoreboard service to manage football (soccer) matches and their scores. You can start new matches, update scores, finish matches, and get a summary of the currently active games.

---
## How to use
To use this library in your Spring Boot project, add the following dependency to your `pom.xml` file:

### maven
```xml
<dependency>
    <groupId>com.scoreboard</groupId>
    <artifactId>scoreboard-lib</artifactId>
    <version>YOUR_VERSION</version> 
</dependency>
```
### gradle
```declarative
implementation 'com.scoreboard:scoreboard-lib:YOUR_VERSION'
```
---
## Methods

| Method Name        | Parameters                                                      | Return Type | Description                                                                                                                                                                                               |
| ------------------ |-----------------------------------------------------------------| ----------- |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `startNewMatch`    | `@Mandatory` `String homeTeam`, <br>  `@Mandatory` `String awayTeam` | `Match`     | Starts a new match between the given home and away teams. Throws an exception if a match already exists between these teams.                                               |
| `finishMatch`      | `@Mandatory` `String homeTeam`,  `@Mandatory` `String awayTeam` | `void`      | Marks the match between the given home and away teams as finished, removing it from the active scoreboard.                                                                                                |
| `updateScore`      | `@Mandatory` `Team homeTeam`,  `@Mandatory` `Team awayTeam`     | `Match`     | Updates the score of the match. The `Team` objects should contain the team name and their current score.                                                                                                  |
| `getSummary`       | None                                                            | `List<Match>` | Returns a list of currently active matches, sorted first by the total score (higher score first) and then by the order in which the matches were started (most recently started matches appearing first). |
---
## Models

### `Match`

This model represents a football match between two teams.

| Field Name | Data Type | Description             |
| ---------- | --------- | ----------------------- |
| `homeTeam` | `Team`    | The home team of the match. |
| `awayTeam` | `Team`    | The away team of the match. |
| `matchId`  | `Long`    | The unique identifier for the match. |

### `Team`

This model represents a team participating in a match.

| Field Name | Data Type | Description                     |
| ---------- | --------- | ------------------------------- |
| `teamName` | `String`  | The name of the team.           |
| `score`    | `Integer` | The current score of the team in the match (defaults to 0). |

---
## Example

```Java
import com.scoreboard.exceptions.MatchDoesNotExistException;
import com.scoreboard.exceptions.MatchExistException;
import com.scoreboard.services.interfaces.ScoreBoardService;
import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {

    @Autowired
    private ScoreBoardService scoreBoardService;

    public void playSomeMatches() {
        try {
            Match match1 = scoreBoardService.startNewMatch("Home Team 1", "Away Team 1");
            System.out.println("Started: " + match1);

            match1.getHomeTeam().setScore(2);
            match1.getAwayTeam().setScore(1);
            Match updatedMatch1 = scoreBoardService.updateScore(match1.getHomeTeam(), match1.getAwayTeam());
            System.out.println("Updated: " + updatedMatch1);

            Match match2 = scoreBoardService.startNewMatch("Home Team 2", "Away Team 2");
            System.out.println("Started: " + match2);

            System.out.println("Summary:");
            scoreBoardService.getSummary().forEach(System.out::println);

            scoreBoardService.finishMatch(match1.getHomeTeam().getTeamName(), match1.getAwayTeam().getTeamName());
            System.out.println("Finished: Home Team 1 vs Away Team 1");

        } catch (MatchExistException | MatchDoesNotExistException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```
