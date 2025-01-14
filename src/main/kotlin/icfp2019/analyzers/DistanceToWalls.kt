package icfp2019.analyzers

import icfp2019.core.Analyzer2
import icfp2019.model.GameState
import icfp2019.model.Node
import icfp2019.model.RobotId
import org.pcollections.PVector

data class Distance(val value: Int) : Comparable<Distance> {
    override fun compareTo(other: Distance): Int {
        return this.value.compareTo(other.value)
    }
}

class DistanceToWalls : Analyzer2<Distance> {

    // Given a map, return a map of weights where the weight indicates the distance
    // the square is from the walls. We will sum the distance to close horizontal and vertical walls.

    companion object {
        val obstacleIdentifier: Distance = Distance(-1)
    }

    private fun applyDistanceAlgorithm(map: PVector<PVector<Node>>): List<List<Distance>> {
        val maxX = map.size
        val maxY = map[0].size
        val ret = map.mapIndexed { x, subArray ->
            subArray.mapIndexed { y, node ->
                if (node.isObstacle) {
                    obstacleIdentifier
                } else {
                    val weightX = if (x < maxX / 2) x + 1 else maxX - x
                    val weightY = if (y < maxY / 2) y + 1 else maxY - y
                    Distance(weightX + weightY)
                }
            }
        }
        return ret
    }

    override fun analyze(initialState: GameState): (robotId: RobotId, state: GameState) -> Distance {
        val distanceBoard = applyDistanceAlgorithm(initialState.cells)
        return { robotId, state ->
            val robot = state.robotState[robotId]!!
            distanceBoard[robot.currentPosition.x][robot.currentPosition.y]
        }
    }
}
