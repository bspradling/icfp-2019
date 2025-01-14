package icfp2019.analyzers

import icfp2019.model.*
import icfp2019.core.Analyzer

object MoveListAnalyzer : Analyzer<(RobotId) -> List<Action>> {
    override fun analyze(map: GameBoard): (state: GameState) -> (RobotId) -> List<Action> {
        return { gameState ->
            { robotId ->
                val moves = mutableListOf<Action>()
                fun checkCanDoAction(action: Action) {
                    if (MoveAnalyzer.analyze(map)(gameState)(robotId, action)) {
                        moves.add(action)
                    }
                }

                checkCanDoAction(Action.DoNothing)
                checkCanDoAction(Action.TurnClockwise)
                checkCanDoAction(Action.TurnCounterClockwise)
                checkCanDoAction(Action.MoveLeft)
                checkCanDoAction(Action.MoveRight)
                checkCanDoAction(Action.MoveDown)
                checkCanDoAction(Action.MoveUp)
                for (location: Point in gameState.teleportDestination) {
                    checkCanDoAction(Action.TeleportBack(location))
                }
                checkCanDoAction(Action.CloneRobot)
                checkCanDoAction(Action.AttachFastWheels)
                checkCanDoAction(Action.StartDrill)
                checkCanDoAction(Action.PlantTeleportResetPoint)

                // Need to check add manipulator at allowable points
                moves
            }
        }
    }
}
