package icfp2019.analyzers

import icfp2019.model.GameBoard
import icfp2019.model.GameState
import icfp2019.model.Node
import icfp2019.model.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.pcollections.TreePVector

class ShortestPathUsingDijkstraTest {

    @Test
    fun testShortestDistanceToPoint() {
        var gameBoard = GameBoard(
            TreePVector.from(
                listOf(
                    TreePVector.from(
                        listOf(
                            Node(Point(0, 0), false),
                            Node(Point(0, 1), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(1, 0), false),
                            Node(Point(1, 1), false)
                        )
                    ),
                    TreePVector.from(
                        listOf(
                            Node(Point(2, 0), false),
                            Node(Point(2, 1), false)
                        )
                    )
                )
            ), 3, 2
        )

        val graph = ShortestPathUsingDijkstra
            .analyze(gameBoard)(GameState.gameStateOf(Point(0, 0)))
            .getPath(
                Node(Point(0, 0), false),
                Node(Point(2, 1), false)
            )

        Assertions.assertEquals(graph.vertexList, listOf(
            Node(Point(0, 0), false),
            Node(Point(0, 1), false),
            Node(Point(1, 1), false),
            Node(Point(2, 1), false)
        ))
    }
}